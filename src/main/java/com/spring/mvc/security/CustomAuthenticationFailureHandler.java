package com.spring.mvc.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, SecurityException {
        String error = null;

        if (exception instanceof UsernameNotFoundException) {
            error = "E";
        } else {
            error = "M";
        }
        // Đưa lỗi vào session
        request.getSession().setAttribute("Error", error);

        // Chuyển hướng về trang login
        getRedirectStrategy().sendRedirect(request, response, "/login");
    }
}
