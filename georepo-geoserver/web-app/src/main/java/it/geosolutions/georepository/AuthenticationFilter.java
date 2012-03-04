package it.geosolutions.georepository;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.geosolutions.georepo.services.RuleReaderService;

import org.apache.commons.codec.binary.Base64;
import org.geoserver.platform.GeoServerExtensions;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;


public class AuthenticationFilter implements Filter
{

    static final String ROOT_ROLE = "ROLE_ADMINISTRATOR";
    static final String ANONYMOUS_ROLE = "ROLE_ANONYMOUS";
    static final String USER_ROLE = "ROLE_USER";
    private RuleReaderService rules;

    @Override
    public void destroy()
    {
        // nothing to do

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
        ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String header = ((httpRequest.getHeader("Authorization") != null) ? httpRequest.getHeader("Authorization")
                                                                          : httpRequest.getHeader("X-CUSTOM-USERID"));

        Authentication authentication = null;
        String username = null;
        String password = null;
        if (header != null)
        {
            String base64Token = header.startsWith("Basic ") ? header.substring(6) : header;
            String token = new String(Base64.decodeBase64(base64Token.getBytes()));

            int delim = token.indexOf(":");

            if (delim != -1)
            {
                username = token.substring(0, delim);
                password = token.substring(delim + 1);
            }
            else
            {
                username = header;
                password = "";
            }
        }

        if (username != null)
        {
            GrantedAuthority[] authorities;
            // check against georepo that the user is the admin
            if ((rules != null) && rules.isAdmin(username))
            {
                authorities = new GrantedAuthority[] { new GrantedAuthorityImpl(ROOT_ROLE) };
            }
            else
            {
                authorities = new GrantedAuthority[] { new GrantedAuthorityImpl(USER_ROLE) };
            }

            UsernamePasswordAuthenticationToken upa = new UsernamePasswordAuthenticationToken(username, password,
                    authorities);
            authentication = upa;
        }
        else
        {
            /*authentication = new AnonymousAuthenticationToken("geoserver", "null",
                    new GrantedAuthority[] { new GrantedAuthorityImpl(ANONYMOUS_ROLE) });*/

            GrantedAuthority[] authorities = new GrantedAuthority[] { new GrantedAuthorityImpl(USER_ROLE) };

            UsernamePasswordAuthenticationToken upa = new UsernamePasswordAuthenticationToken("ANONYM", "ANONYM",
                    authorities);
            authentication = upa;
        }

        /* if(authentication instanceof AnonymousAuthenticationToken && (httpRequest.getPathInfo().startsWith("/web"))) {
            httpResponse.addHeader("WWW-Authenticate", "Basic realm=\"geoserver\"");
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please authenticate as administrator");
        } else if(httpRequest.getPathInfo().startsWith("/j_spring_security_logout")) {
            // send them back to the home page
            httpResponse.addHeader("WWW-Authenticate", "Basic realm=\"geoserver\"");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/web");
        } else {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(httpRequest, response);
        } */

        if (httpRequest.getPathInfo().startsWith("/j_spring_security_logout"))
        {
            // send them back to the home page
            httpResponse.addHeader("WWW-Authenticate", "Basic realm=\"geoserver\"");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/web");
        }

        /*else if (authentication instanceof AnonymousAuthenticationToken)
        {
            httpResponse.addHeader("WWW-Authenticate", "Basic realm=\"geoserver\"");
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please authenticate as administrator");
        }*/
        else
        {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(httpRequest, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        rules = (RuleReaderService) GeoServerExtensions.bean("ruleReaderService");
    }

}
