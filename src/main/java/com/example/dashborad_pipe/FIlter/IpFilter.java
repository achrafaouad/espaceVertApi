package com.example.dashborad_pipe.FIlter;

import com.example.dashborad_pipe.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class IpFilter extends OncePerRequestFilter {

    private Service service;

    @Autowired
    public IpFilter(Service service) {
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String clientIp = request.getRemoteAddr();
        System.out.println("Client IP: " + clientIp);
        this.service.setIp(clientIp);
        filterChain.doFilter(request, response);
    }
}