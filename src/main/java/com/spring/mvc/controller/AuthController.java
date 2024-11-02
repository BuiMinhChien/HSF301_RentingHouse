package com.spring.mvc.controller;


import com.spring.mvc.dto.UserRegisterDTO;
import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Role;
import com.spring.mvc.service.AccountService;
import com.spring.mvc.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {


    AccountService accountService;

    PasswordEncoder passwordEncoder;

    RoleService roleService;

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
        return "403";
    }

    @GetMapping("/")
    public String redirectToHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        model.addAttribute("isLoggedIn", isLoggedIn); // Truyền biến vào view
        return "redirect:/home"; // Chuyển hướng đến trang home
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        model.addAttribute("isLoggedIn", isLoggedIn); // Truyền biến vào view
        return "home";
    }

    @GetMapping("/customer/homepage")
    public String customerHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        model.addAttribute("isLoggedIn", isLoggedIn); // Truyền biến vào view

        return "customer/homepage";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";  // Return the registration form (register.html)
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute("registerDTO") UserRegisterDTO userRegisterDTO, BindingResult bindingResult, Model model) {
        // Check if the username already exists
        if (accountService.existsByUsername(userRegisterDTO.getUsername())) {
            model.addAttribute("errorUsernameMessage", "Username already exists. Please choose another one.");
            return "register";
        }

        // Check if the email already exists
        if (accountService.existsByEmail(userRegisterDTO.getEmail())) {
            model.addAttribute("errorEmailMessage", "Email already exists. Please use another one.");
            return "register";
        }

        // Register the user if no issues
        createAccount(userRegisterDTO);
        return "redirect:/login";  // After registration, redirect to OTP verification
    }
    public void createAccount(UserRegisterDTO userRegisterDTO) {
        Account user = new Account();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setEmail(userRegisterDTO.getEmail());
        user.setVerify("1");
        // Gán vai trò (role) cho người dùng
        Role role = roleService.findRoleById(1);
        user.setRole(role);

        accountService.save(user);

    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403"; // Tên file HTML là 403.html trong thư mục templates
    }
}
