/*
 *  Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 * 
 *  GPLv3 + Classpath exception
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.geosolutions.georepo.services.webgis.impl;

import com.trg.search.Search;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.simplify.DouglasPeuckerLineSimplifier;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;
import it.geosolutions.georepo.core.dao.GSUserDAO;
import it.geosolutions.georepo.core.dao.ProfileDAO;
import it.geosolutions.georepo.core.model.GSUser;
import it.geosolutions.georepo.core.model.Profile;
import it.geosolutions.georepo.services.webgis.SGUService;
import it.geosolutions.georepo.services.webgis.model.SGUProfile;
import it.geosolutions.georepo.services.webgis.model.SGUProfileList;
import it.geosolutions.georepo.services.webgis.model.SGUUser;
import it.geosolutions.georepo.services.webgis.model.SGUUserList;
import jaitools.jts.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class WGSGUServiceImpl implements SGUService {
    private final static Logger LOGGER = Logger.getLogger(WGSGUServiceImpl.class);

    private GSUserDAO gsUserDAO;
    private ProfileDAO profileDAO;

    @Override
    public SGUProfileList getProfiles() {
        List<Profile> profiles = profileDAO.findAll();
        
        SGUProfileList ret = new SGUProfileList(profiles.size());
        for (Profile profile : profiles) {
            ret.add(transformProfile(profile));
        }
        return ret;
    }


    @Override
    public SGUUserList getUsers() {
        List<GSUser> users = gsUserDAO.findAll();

        SGUUserList ret = new SGUUserList(users.size());
        for (GSUser user : users) {
            ret.add(transformUser(user));
        }
        return ret;
    }

    //==========================================================================

    /**
     * TODO: we should create the profiles in a standalone thread.
     * Call performed when one thread is running should return an error message
     * to the caller into the return String
     *
     * TODO: disable in georepo all the profiles not included in the list.
     *
     * @param list
     * @return "OK" if the importing procedure started correctly or "ALREADY RUNNING" if another procedure is still running.
     */
    @Override
    public String setProfiles(SGUProfileList list) {
        LOGGER.info("Got " + list);
        int cntNew = 0;
        int cntUp = 0;
        int cntOld = 0;

        for (SGUProfile sguProfile : list.getProfileList()) {
            Search search = new Search(Profile.class).addFilterEqual("extId", sguProfile.getSguId());
            List<Profile> profileList = profileDAO.search(search);
//            List<Profile> profileList = profileDAO.searchByCustomProp(EXTERNAL_ID_KEY, sguProfile.getSguId());
            switch (profileList.size()) {
                case 0:
                    cntNew++;
                    LOGGER.info("Importing new " + sguProfile);
                    Profile profile = createProfile(sguProfile);
                    profileDAO.persist(profile);
                    break;
                case 1:
                    Profile oldProfile = profileList.get(0);
                    if( oldProfile.getName().equals(sguProfile.getName())) {
                        cntOld++;
                    } else {
                        cntUp++;
                        LOGGER.info("Updating " + sguProfile + " -- old name : " + oldProfile.getName());
                        oldProfile.setName(sguProfile.getName());
                        profileDAO.merge(oldProfile);
                    }
                    break;
                default:
                    LOGGER.error("Found " + profileList.size() + " profiles related to " + sguProfile + " Please check and fix the DB.");
            }

        }
        LOGGER.info("setProfiles: new:" + cntNew + " updated:" + cntUp + " existing:" + cntOld);
        return "OK";
    }

    private static Profile createProfile(SGUProfile sp) {
        Profile profile = new Profile();
        profile.setName(sp.getName());
        profile.setExtId(sp.getSguId());
//        profile.getCustomProps().put(EXTERNAL_ID_KEY, sp.getSguId());
        return profile;
    }

    //==========================================================================
    
    @Override
    public String setUsers(SGUUserList sguList) {
        LOGGER.info("Got " + sguList);
        int cntNew = 0;
        int cntErr = 0;
        int cntEx = 0;
        int cntUp = 0;
        int cntOld = 0;

        ProfileCache profileCache = new ProfileCache(profileDAO);

        for (SGUUser sguUser : sguList.getUserList()) {
            try {
                Search search = new Search(GSUser.class).addFilterEqual("extId", sguUser.getSguId());
                List<GSUser> geoRepoList = gsUserDAO.search(search);

                switch (geoRepoList.size()) {
                    case 0:
                        LOGGER.info("Importing new " + sguUser);
                        GSUser user = createUser(sguUser, profileCache);
                        if (user == null) {
                            cntErr++;
                            LOGGER.error("Error creating new user from " + sguUser);
                        } else {
                            cntNew++;
                            gsUserDAO.persist(user);
                        }
                        break;
                    case 1:
                        GSUser oldUser = geoRepoList.get(0);
                        LOGGER.info("Updating " + sguUser + " -- userId : " + oldUser.getId());
                        boolean updated = update(sguUser, oldUser); // TODO: define policy for updating user (geom may be changed)

                        if ( updated ) {
                            cntOld++;
                        } else {
                            cntUp++;
                            gsUserDAO.merge(oldUser);
                        }
                        break;
                    default:
                        LOGGER.error("Found " + geoRepoList.size() + " users related to " + sguUser + " Please check and fix the DB.");
                }
            } catch (Exception e) {
                LOGGER.error("Error importing " + sguList, e);
                cntEx++;
            }
        }
        LOGGER.info("setUsers: new:" + cntNew + " updated:" + cntUp + " existing:" + cntOld + " err:"+cntErr + " ex:"+cntEx);
        return "OK";
    }

    // TODO: add the fields you want to update
    private boolean update(SGUUser sgu, GSUser gs) {
        boolean updated = false;
        if(! gs.getName().equals(sgu.getUserName())) {
            updated = true;
            gs.setName(sgu.getUserName());
        }
        if( sgu.isEnabled() != gs.getEnabled()) {
            updated = true;
            gs.setEnabled(sgu.isEnabled());
        }
        return updated;
    }

    private GSUser createUser(SGUUser sguUser, ProfileCache profileCache) {
        GSUser user = new GSUser();
        user.setName(sguUser.getUserName());
        user.setExtId(sguUser.getSguId());

        Profile profile = profileCache.get(sguUser.getProfile());
        if(profile == null)
            return null;
        user.setProfile(profile);

        user.setEnabled(sguUser.isEnabled());

        String wkt = sguUser.getWkt();
        Integer srid = sguUser.getSrid();
        if(wkt != null && ! wkt.trim().isEmpty()) {
            final WKTReader wktReader = new WKTReader(); // should be made static? is it threadsafe?
            try {
                MultiPolygon the_geom = (MultiPolygon) wktReader.read(wkt);
                int isrid = srid!= null? srid.intValue() : 4326;
                the_geom.setSRID(isrid); // !!! read javadoc about setSRID()

                MultiPolygon simp = simplifyMultiPolygon(the_geom);
                simp.setSRID(isrid);
                user.setAllowedArea(simp);
            } catch (ParseException pe) {
                LOGGER.error("Error parsing WKT for " + sguUser, pe);
            }
        }

        return user;
    }

    /**
     * Simplifies a MultiPolygon.
     * <BR/><BR/>
     * Simplification is performed by first removing collinear points, then
     * by applying DouglasPeucker simplification.
     * <BR/>Order <B>is</B> important, since it's more likely to have collinear
     * points before applying any other simplification.
     */
    public static MultiPolygon simplifyMultiPolygon(final MultiPolygon mp) {

        final Polygon[] simpPolys = new Polygon[mp.getNumGeometries()];

        for (int i= 0; i < mp.getNumGeometries(); i++) {
            Polygon p = (Polygon)mp.getGeometryN(i);
            Polygon s1 = Utils.removeCollinearVertices(p);
            TopologyPreservingSimplifier tps = new TopologyPreservingSimplifier(s1);
            Polygon s2 = (Polygon)tps.getResultGeometry();
            simpPolys[i] = s2;

            if(LOGGER.isInfoEnabled()) {
                LOGGER.info("RCV: simplified poly " + getPoints(p) 
                        + " --> " + getPoints(s1)
                        + " --> " + getPoints(s2));
            }
        }

        // reuse existing factory
        final GeometryFactory gf = mp.getFactory();
        return gf.createMultiPolygon(simpPolys);
    }

    /**
     * Return the number of points of a polygon in the format
     * E+I0+I1+...+In
     * where E is the number of points of the exterior ring and I0..In are
     * the number of points of the Internal rings.
     */
    public static String getPoints(final Polygon p) {
        final StringBuilder sb = new StringBuilder();
        sb.append(p.getExteriorRing().getNumPoints());
        for (int i = 0; i < p.getNumInteriorRing(); i++) {
            LineString ir = p.getInteriorRingN(i);
            sb.append('+').append(ir.getNumPoints());
        }

        return sb.toString();
    }

    //==========================================================================

    public static SGUUser transformUser(GSUser user) {
        SGUUser sgu = new SGUUser();
        sgu.setEnabled(user.getEnabled());
        sgu.setProfile(user.getProfile().getName());
        sgu.setUserName(user.getName());
        sgu.setSguId(user.getExtId());
        sgu.setSrid(user.getAllowedArea().getSRID());
        sgu.setWkt(user.getAllowedArea().toText());
        return sgu;
    }

    public static SGUProfile transformProfile(Profile profile) {
        SGUProfile sgu = new SGUProfile();
        sgu.setName(profile.getName());
        sgu.setSguId(profile.getExtId());
        return sgu;
    }

    //==========================================================================

    public void setProfileDAO(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }

    public void setGsUserDAO(GSUserDAO gsUserDAO) {
        this.gsUserDAO = gsUserDAO;
    }

    //==========================================================================


    class ProfileCache {
        private final Map<String, Profile> cache = new HashMap<String, Profile>();
        private final ProfileDAO profileDAO;

        public ProfileCache(ProfileDAO profileDAO) {
            this.profileDAO = profileDAO;
        }

        public Profile get(String sguId) {
            Profile profile = cache.get(sguId);
            if(profile == null) {
                Search search = new Search(Profile.class).addFilterEqual("extId", sguId);
                List<Profile> profileList = profileDAO.search(search);
                if(profileList.isEmpty()) {
                    LOGGER.warn("Profile sgu:" + sguId + " not found in GeoRepository. Make sure the profiles are in synch.");
                    // should we put a null in the cache?
                } else {
                    profile = profileList.get(0);
                    cache.put(sguId, profile);
                }
            }
            return profile;
        }
    }
}
