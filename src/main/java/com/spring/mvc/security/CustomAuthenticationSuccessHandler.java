package com.spring.mvc.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler  implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setAttribute("username", authentication.getName());

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_NEWS_WRITER")) {
            response.sendRedirect("/news_writer/dashboard");
        } else if (roles.contains("ROLE_HOUSE_LISTING_AGENT")) {
            response.sendRedirect("/agent/dashboard");
        } else if (roles.contains("ROLE_CUSTOMER_CARE")) {
            response.sendRedirect("/customer-care/home");
        } else if (roles.contains("ROLE_CUSTOMER")) {
            response.sendRedirect("/customer/homepage");
        } else {
            response.sendRedirect("/customer/homepage");
        }
    }
}
