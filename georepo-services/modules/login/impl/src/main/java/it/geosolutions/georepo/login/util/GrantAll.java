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

package it.geosolutions.georepo.login.util;

import it.geosolutions.georepo.api.AuthProvider;
import it.geosolutions.georepo.api.dto.Authority;
import it.geosolutions.georepo.api.dto.GrantedAuths;
import it.geosolutions.georepo.api.exception.AuthException;

import java.util.Arrays;

import org.apache.log4j.Logger;

/**
 * A dummy AuthProvider which grants all auths to every request.
 * 
 * @author ETj (etj at geo-solutions.it)
 */
public class GrantAll implements AuthProvider {
    private final static Logger LOGGER = Logger.getLogger(GrantAll.class);

    @Override
    public GrantedAuths login(String username, String password) throws AuthException {
        // allow auth to anybody
        LOGGER.warn("Granting login to " + username);

        GrantedAuths ga = new GrantedAuths();
        ga.setAuthorities(Arrays.asList(Authority.values()));
        return ga;
    }

    @Override
    public void logout(String token) {
        // nothing to do
    }

}
