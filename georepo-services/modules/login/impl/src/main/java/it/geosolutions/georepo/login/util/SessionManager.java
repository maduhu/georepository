/*
 *  Copyright (C) 2007 - 2010 GeoSolutions S.A.S.
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

import java.util.LinkedHashMap;
import java.util.Map;

import it.geosolutions.georepo.api.dto.GrantedAuths;
import it.geosolutions.georepo.api.exception.AuthException;
import org.apache.log4j.Logger;

/**
 * 
 * @author etj
 */
public class SessionManager {

	private final static Logger LOGGER = Logger.getLogger(SessionManager.class);

	private final static int MAXSESSIONS = 100;
	private final SessionMap activeSessions = new SessionMap(MAXSESSIONS);

    private static class LoggedInMember {
        public GrantedAuths grantedAuths;
        public String username;
    }

    public String createSession(String username, GrantedAuths grantedAuths) {

        String token = TokenEncoder.encode(username + grantedAuths.hashCode(),
                ("" + System.nanoTime() + "" + System.currentTimeMillis())
                        .substring(0, 16));

        LoggedInMember loggedInMember = new LoggedInMember();
        loggedInMember.grantedAuths = grantedAuths;
        loggedInMember.username = username;
        activeSessions.put(token, loggedInMember);

        return token;
    }

	public void closeSession(String token) {
		LoggedInMember member = activeSessions.remove(token);
		if (member == null) {
			LOGGER.warn("Tried to close non existent session. Token " + token);
		} else {
            LOGGER.info("Closing session for user ["+member.username+"] token " + token);
        }
	}

    public GrantedAuths getGrantedAuthorities(String token) throws AuthException {
        LoggedInMember loggedInMember = activeSessions.get(token);
        if (loggedInMember != null) {
            return loggedInMember.grantedAuths;
        }
        else {
            throw new AuthException("No active session for token " + token);
        }
    }

	/**
	 * If too many sessions, throw away the older ones. 
     * Todo: has to be improved and made customizable.
	 */
	class SessionMap extends LinkedHashMap<String, LoggedInMember> {

		private final int MAX_ENTRIES;

		public SessionMap(int maxEntries) {
			this.MAX_ENTRIES = maxEntries;
		}

		@Override
		protected boolean removeEldestEntry(Map.Entry eldest) {
			if (size() > MAX_ENTRIES) {
				LoggedInMember member = (LoggedInMember) eldest.getValue();
				LOGGER.info("Removing stale token " + eldest.getKey()
						+ " for member " + member.username);
				// todo: have to logout related member?
				return true;
			} else {
				return false;
			}
		}
	}

}
