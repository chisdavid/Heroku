package com.ds.Assignement1.Assignement1.Security;

import com.ds.Assignement1.Assignement1.Repository.RoleRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final RoleRepository roleRepository;

    @Autowired
    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil,RoleRepository userRepo) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.roleRepository = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println(header);
        log.info("Entered with JWT: "+ header);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        final String token = header.split(" ")[1].trim();

        UserDetails userDetails = null;
        try {
            userDetails =roleRepository.findFirstByUsername(jwtTokenUtil.getUsernameFromToken(token)).orElse(null);
        } catch (ExpiredJwtException ignore) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        if (userDetails == null) {
            chain.doFilter(request, response);
            return;
        }
        // Get jwt token and validate
        if (!jwtTokenUtil.validateToken(token, userDetails)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Success auth");
        chain.doFilter(request, response);
    }

}
