package it.geosolutions.georepo.gui;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.geosolutions.georepo.gui.server.GeoRepoKeySessionValues;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;


public class AuthenticationFilter implements Filter
{

    static final String ROOT_ROLE = "LOGIN";
    static final String ANONYMOUS_ROLE = "ANONYMOUS";
    static final String USER_ROLE = "REMOTE";

    public void destroy()
    {
        // nothing to do

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
        ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String header = httpRequest.getHeader("X-CUSTOM-USERID");

        Authentication authentication = null;
        String username = null;
        String password = null;
        if (header != null)
        {
            String base64Token = header;
            String token = new String(Base64.decodeBase64(base64Token.getBytes()));

//            int delim = token.indexOf(":");
//
//            if (delim != -1)
//            {
//                username = token.substring(0, delim);
//                password = token.substring(delim + 1);
//            }

            httpRequest.getSession().setAttribute(GeoRepoKeySessionValues.USER_LOGGED_TOKEN.getValue(), token);

            GrantedAuthority[] authorities;
            authorities = new GrantedAuthority[] { new GrantedAuthorityImpl(ROOT_ROLE) };

            UsernamePasswordAuthenticationToken upa = new UsernamePasswordAuthenticationToken(
                    "1nt3rnAL-G30r3p0-admin",
                    "1geosolutions2",
                    authorities);
            authentication = upa;

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(httpRequest, response);
        }
        else
        {
            httpRequest.getSession().setAttribute(GeoRepoKeySessionValues.USER_LOGGED_TOKEN.getValue(), "");
//            httpResponse.addHeader("WWW-Authenticate", "Basic realm=\"georepository\"");
//            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please authenticate as administrator");

            authentication = new AnonymousAuthenticationToken("georepository", "null", new GrantedAuthority[]
                    {
                        new GrantedAuthorityImpl(ANONYMOUS_ROLE)
                    });

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(httpRequest, response);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

}
