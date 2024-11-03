package com.spring.mvc.controller;


import com.spring.mvc.common.FileUploadUtil;
import com.spring.mvc.dto.ProfileDTO;
import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Customer;
import com.spring.mvc.entity.Staff;
import com.spring.mvc.service.AccountService;
import com.spring.mvc.service.StaffService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/house-listing")
public class StaffController {
        private StaffService staffService;
        private AccountService accountService;
        private FileUploadUtil fileUploadUtil;

    public StaffController(StaffService staffService, AccountService accountService, FileUploadUtil fileUploadUtil) {
        this.staffService = staffService;
        this.accountService = accountService;
        this.fileUploadUtil = fileUploadUtil;
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        // Lấy thông tin người dùng hiện tại từ tên đăng nhập (email)
        String name = principal.getName();
        Account account  = accountService.findByUsername(name);
        Staff staff = staffService.findStaffById(account.getId());

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setStaff(staff);
        profileDTO.setAccount(account);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        model.addAttribute("isLoggedIn", isLoggedIn); // Truyền biến vào view
        model.addAttribute("staffDTO", profileDTO);
        // Trả về trang profile
        return "house_listing_agent/profile";
    }


    @PostMapping("/uploadAvatar")
    public String uploadAvatar(@RequestParam("avatar") List<MultipartFile> avatar, Model model, Principal principal) throws IOException {
        Account account = accountService.findByUsername(principal.getName());
        fileUploadUtil.UploadImagesForAvata(avatar,account);
        accountService.update(account);
        return "redirect:/house-listing/profile";
    }

    @GetMapping("/changePassword")
    public String showChangePasswordForm(Model model, Principal principal) {
        String name = principal.getName();
        Account user = accountService.findByUsername(name);

        // Đưa thông tin người dùng vào model (nếu cần)
        model.addAttribute("user", user);

        return "house_listing_agent/profile";
    }


    @PostMapping("/change-password")
    @ResponseBody
    public Map<String, String> changePassword(@RequestParam("oldPassword") String oldPassword,
                                              @RequestParam("newPassword") String newPassword,
                                              @RequestParam("confirmPassword") String confirmPassword,
                                              Principal principal) {

        Map<String, String> response = new HashMap<>();

        if (!newPassword.equals(confirmPassword)) {
            response.put("status", "error");
            response.put("message", "New password and confirm password do not match.");
            return response;
        }
        String username = principal.getName();
        Account account = accountService.findByUsername(username);
        try {
            accountService.changePassword(username,oldPassword,newPassword );
            response.put("status", "success");
            response.put("message", "Password changed successfully.");
        } catch (IllegalArgumentException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        } catch (NoSuchElementException e) {
            response.put("status", "error");
            response.put("message", "User not found.");
        }

        return response;
    }


}
