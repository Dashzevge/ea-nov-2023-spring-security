package com.example.demo.filter;


import com.example.demo.entity.Role;
import com.example.demo.util.Jwt;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter extends OncePerRequestFilter {

    private  Jwt jwtUtil;

    public AuthenticationFilter(Jwt jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse responce, FilterChain filterchain)
            throws ServletException, IOException {
        try {
            final String header = request.getHeader("Authorization");

            if (header == null || !header.startsWith("Bearer ")) {
                filterchain.doFilter(request, responce);
                return;
            }

            final String token = header.split(" ")[1].trim();

            if (!jwtUtil.validateToken(token)) {
                filterchain.doFilter(request, responce);
                return;
            }

            String email = jwtUtil.getEmailFromToken(token);
            Role role = Role.valueOf(jwtUtil.getRoleFromToken(token));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null,
                    java.util.Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Modify the request to include the email as a parameter
            HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request) {
                @Override
                public String getParameter(String name) {
                    if ("email".equals(name)) {
                        return email;
                    }
                    return super.getParameter(name);
                }
            };

            filterchain.doFilter(requestWrapper, responce);
        } catch (java.io.IOException | ServletException e) {
            e.printStackTrace();
        }
    }

}