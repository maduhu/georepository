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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import it.geosolutions.georepo.api.AuthProvider;
import it.geosolutions.georepo.api.dto.Authority;
import it.geosolutions.georepo.api.dto.GrantedAuths;
import it.geosolutions.georepo.api.exception.AuthException;

import org.apache.log4j.Logger;


/**
 * A dummy AuthProvider which grants all auths to every request.
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class GrantAll implements AuthProvider
{
    private static final Logger LOGGER = Logger.getLogger(GrantAll.class);

    @Override
    public GrantedAuths login(String username, String password) throws AuthException
    {
        // allow auth to anybody
        LOGGER.warn("Granting login to " + username);

        GrantedAuths ga = new GrantedAuths();

        byte[] bytesOfMessage;
        try
        {
            bytesOfMessage = password.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new AuthException(e.getLocalizedMessage(), e);
        }

        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new AuthException(e.getLocalizedMessage(), e);
        }

        byte[] thedigest = md.digest(bytesOfMessage);

        LOGGER.info("DIGEST: " + new String(thedigest));

        if (username.equals("1nt3rnAL-G30r3p0-admin") &&
                // new String(thedigest).equals("2c6fe6e20600312c5aa94ef0ca42b0af")
                password.equals("1geosolutions2"))
        {
            ga.setAuthorities(Arrays.asList(Authority.values()));
        }
        else
        {
            ga.setAuthorities(Arrays.asList(Authority.REMOTE));
        }

        return ga;
    }

    @Override
    public void logout(String token)
    {
        // nothing to do
    }

}
