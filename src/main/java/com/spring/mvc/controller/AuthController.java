package com.spring.mvc.controller;


import com.spring.mvc.dto.UserRegisterDTO;
import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Role;
import com.spring.mvc.entity.Token;
import com.spring.mvc.entity.Topic;
import com.spring.mvc.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    @Autowired
    EmailService emailService;

    @Autowired
    TopicService topicService;

    AccountService accountService;

    PasswordEncoder passwordEncoder;

    RoleService roleService;

    @Autowired
    TokenService tokenService;

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
    public String customerHome(Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        model.addAttribute("isLoggedIn", isLoggedIn); // Truyền biến vào view

        Account account = accountService.findByUsername(principal.getName());
        model.addAttribute("accountId", account.getId());
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
        user.setRegistrationDate(LocalDateTime.now().toString());

        accountService.save(user);

    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403"; // Tên file HTML là 403.html trong thư mục templates
    }

    @GetMapping("customer-care/home")
    public String customercareHome() {
        return "customercare/chatbot-settings";
    }

    @GetMapping("customer-care/insertContent")
    public String insertContent(Model model) {
        List<Topic> mainTopics = topicService.findTopicsWithoutQuestions();
        List<Topic> subTopics = topicService.getALlSubTopics();

        model.addAttribute("mainTopics", mainTopics);
        model.addAttribute("subTopics", subTopics);
        return "customercare/insert-content";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        Account user = accountService.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "Email not valid!");
            return "forgot-password";
        }

        // Tạo token đặt lại mật khẩu
        String token = tokenService.createToken(user);
        String resetUrl = "http://localhost:8080/reset-password?token=" + token;

        // Gửi email với link đặt lại mật khẩu
        emailService.sendEmail(email, "Reset Password", "Click the link to reset password: " + resetUrl);
        model.addAttribute("message", "Link đặt lại mật khẩu đã được gửi đến email của bạn.");
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam(value = "token", required = false) String token, Model model) {
        if (token == null || token.isEmpty()) {
            model.addAttribute("error", "Missing or invalid token!");
            return "reset-password";
        }
        Token resetToken = tokenService.findToken(token).orElse(null);
        if (resetToken.equals("") || resetToken.getExpiryDate().before(new Date())) {
            model.addAttribute("error", "Token invalid!");
            return "reset-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }


    @PostMapping("/reset-password")
    public String processResetPassword(
            @RequestParam(name = "token", required = true) String token,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        if (token == null) {
            model.addAttribute("error", "Missing required token.");
            return "reset-password";
        }

        // Find the reset token
        Token resetToken = tokenService.findToken(token).orElse(null);
        if (resetToken == null) {
            model.addAttribute("error", "Token không hợp lệ!");
            return "reset-password";
        }

        // Check if token is expired
        if (resetToken.getExpiryDate().before(new Date())) {
            model.addAttribute("error", "Token đã hết hạn!");
            return "reset-password";
        }

        // Check password match
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu xác nhận không khớp!");
            model.addAttribute("token", token); // Keep token for user
            return "reset-password";
        }

        // Update password
        Account account = resetToken.getAccount();
        account.setPassword(newPassword);
        accountService.changePass(account);

        // Add success message and delete the token
        model.addAttribute("message", "Mật khẩu đã được thay đổi thành công!");
        tokenService.deleteToken(resetToken);

        return "login";
    }



}
