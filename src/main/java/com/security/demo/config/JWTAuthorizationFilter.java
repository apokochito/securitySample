package com.security.demo.config;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    // Authorization process, which is capable of intercepting invocations to protected
    // resources to recover the token and determine if the client has permissions or not.

    // Checks the existence of the token (checkJWTToken(…))
    // If it exists, it decrypts and validates (validateToken(…))
    // If everything is ok, it adds the necessary configuration to the Spring context
    // to authorize the request (setUpStringAuthentication(…)).

    private final String PARAM = "Authorization";
    private final String PREFIX = "demo ";

    @Value("${jwt.token.secret}")
    private String SECRET;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            if (checkJWTToken(servletRequest, servletResponse)) {
                Claims claims = validateToken(servletRequest);
                if (claims.get("authorities") != null) {
                    setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            }else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            servletResponse.setContentType(String.valueOf(HttpServletResponse.SC_FORBIDDEN));
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }
    }

    private void setUpSpringAuthentication(Claims claims) {
        List<String> authorities = (List) claims.get("authorities");

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private Claims validateToken(ServletRequest servletRequest) {
        //parameter
        String jwtToken = servletRequest.getParameter(PARAM).replace(PREFIX, "");
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    private boolean checkJWTToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        //parameter
        String authenticationHeader = servletRequest.getParameter(PARAM);
        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
            return false;
        return true;
    }
}
