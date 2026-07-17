package com.ochai.medflow.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        for (GrantedAuthority authority : authentication.getAuthorities()) {

            String role = authority.getAuthority();

            switch (role) {

                case "ROLE_ADMIN":
                    response.sendRedirect("/admin/dashboard");
                    return;

                case "ROLE_DOCTOR":
                    response.sendRedirect("/doctor/dashboard");
                    return;

                case "ROLE_RECEPTIONIST":
                    response.sendRedirect("/reception/dashboard");
                    return;

                case "ROLE_LAB_SCIENTIST":
                    response.sendRedirect("/lab/dashboard");
                    return;

                case "ROLE_PHARMACIST":
                    response.sendRedirect("/pharmacy/dashboard");
                    return;
            }
        }

        response.sendRedirect("/login?error");
    }
}