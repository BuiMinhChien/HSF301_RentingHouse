package com.spring.mvc.cotroller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    @GetMapping("/login")
    public String login(Principal principal, HttpServletRequest session) {
        if (principal != null) {
            // Lấy URL từ session và chuyển hướng nếu có, ngược lại thì về home
            String redirectUrl = (String) session.getAttribute("redirectUrl");
            session.removeAttribute("redirectUrl"); // Xóa URL khỏi session sau khi lấy
            return "redirect:" + (redirectUrl != null ? redirectUrl : "/home");
        }
        return "login";
    }

    @GetMapping("/someProtectedPage")
    public String someProtectedPage(HttpServletRequest request) {
        // Kiểm tra nếu người dùng chưa đăng nhập
        if (request.getUserPrincipal() == null) {
            // Lưu URL hiện tại vào session
            request.getSession().setAttribute("redirectUrl", request.getRequestURI());
            return "redirect:/login";
        }
        return "access-denied";
    }

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home"; // Chuyển hướng đến trang home
    }


    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
}
