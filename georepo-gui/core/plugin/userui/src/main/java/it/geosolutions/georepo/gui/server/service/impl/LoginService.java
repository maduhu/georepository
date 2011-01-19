/*
 * $ Header: it.geosolutions.georepo.gui.server.service.impl.LoginService,v. 0.1 14-gen-2011 19.29.51 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 14-gen-2011 19.29.51 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. 
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by developers
 * of GeoSolutions.  For more information on GeoSolutions, please see
 * <http://www.geo-solutions.it/>.
 *
 */
package it.geosolutions.georepo.gui.server.service.impl;

import it.geosolutions.georepo.api.dto.Authority;
import it.geosolutions.georepo.api.dto.GrantedAuths;
import it.geosolutions.georepo.api.exception.AuthException;
import it.geosolutions.georepo.gui.client.ApplicationException;
import it.geosolutions.georepo.gui.client.model.Authorization;
import it.geosolutions.georepo.gui.client.model.User;
import it.geosolutions.georepo.gui.server.GeoRepoKeySessionValues;
import it.geosolutions.georepo.gui.server.service.ILoginService;
import it.geosolutions.georepo.gui.service.GeoRepoRemoteService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginService.
 */
@Component("loginService")
public class LoginService implements ILoginService {

    /** The logger. */
    private final Logger logger = Logger.getLogger(this.getClass());

    // @Autowired
    // private SecurityManager securityManagerService; // DIRECT ACCESS TO
    // MEMBER SERVICES (here for demo purposes)

    /** The georepo remote service. */
    @Autowired
    private GeoRepoRemoteService georepoRemoteService;

    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.server.service.ILoginService#authenticate (java.lang.String,
     * java.lang.String)
     */
    public User authenticate(String userName, String password, HttpSession session)
            throws ApplicationException {
        logger.info("authenticate " + userName);

        GrantedAuths grantedAuths = null;
        String token = null;

        try {
            URL url = Class.forName("it.geosolutions.georepo.gui.client.UserUI").getResource(
                    "client.keystore");
            String path = url.getPath();
            if (logger.isDebugEnabled()) {
                logger.debug(path);
            }
            System.setProperty("javax.net.ssl.trustStore", path);
            System.setProperty("javax.net.ssl.trustStorePassword", "geosolutions");

            // grantedAuthorities =
            token = georepoRemoteService.getLoginService().login(userName, password);
            grantedAuths = georepoRemoteService.getLoginService().getGrantedAuthorities(token);

        } catch (ClassNotFoundException e) {
            logger.error("Error :********** " + e.getMessage());
            throw new ApplicationException(e);
        } catch (AuthException e) {
            logger.error("Error : ********* " + e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }

        User user = new User();
        user.setName(userName);
        user.setPassword(password);

        // convert the server-side auths to client-side auths
        List<Authorization> guiAuths = new ArrayList<Authorization>();
        for (Authority auth : grantedAuths.getAuthorities()) {
            guiAuths.add(Authorization.valueOf(auth.name()));
        }
        user.setGrantedAuthorizations(guiAuths);

        if (grantedAuths != null && !grantedAuths.getAuthorities().isEmpty()) {
        }

        session.setMaxInactiveInterval(7200);

        session.setAttribute(GeoRepoKeySessionValues.USER_LOGGED_TOKEN.getValue(), token);
        // session.setAttribute(
        // DGWATCHKeySessionValues.USER_LOGGED_TOKEN.getValue(),
        // grantedAuthorities_NOTUSEDANYMORE.getToken());

        return user;
    }

}
