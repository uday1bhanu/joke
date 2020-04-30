package com.uday.joke.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseHeadersFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        response.addHeader("Strict-Transport-Security","max-age=31536000; includeSubDomains");
        response.addHeader("Content-Security-Policy", "default-src https:");
        response.addHeader("Referrer-Policy","origin-when-cross-origin");
        response.addHeader("Feature-Policy", "vibrate 'none'; geolocation 'none'");

        filterChain.doFilter(request, response);
    }
}
