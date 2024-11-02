package com.spring.mvc.controller;

import com.spring.mvc.dto.ProfileDTO;
import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Customer;
import com.spring.mvc.entity.Image;
import com.spring.mvc.service.AccountService;
import com.spring.mvc.service.CustomerService;
import com.spring.mvc.service.ImageService;
import com.spring.mvc.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {

    AccountService accountService;

    CustomerService customerService;

    ImageService imageService;

    PasswordEncoder passwordEncoder;

    private static final String UPLOAD_DIRECTORY = "D:\\Semester 5\\HSF301\\HSF301_RentingHouse\\src\\main\\resources\\static\\image";

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        // Lấy thông tin người dùng hiện tại từ tên đăng nhập (email)
        String name = principal.getName();
        Account account  = accountService.findByUsername(name);
        Customer customer = customerService.findCustomerById(account.getId());

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setCustomer(customer);
        profileDTO.setAccount(account);
        model.addAttribute("profileDTO", profileDTO);
        // Trả về trang profile
        return "customer/profile";
    }

    @PostMapping("/update")
    public String updateProfile(
            @ModelAttribute("customerDTO") ProfileDTO profileDTO,
            @RequestParam("idCardFrontImage") MultipartFile idCardFrontImage,
            @RequestParam("idCardBackImage") MultipartFile idCardBackImage, Model model, Principal principal) throws IOException {
        // Extract customer and account from DTO
        Account account = accountService.findByUsername(principal.getName());
        Customer customer = profileDTO.getCustomer();

        // Check if account and customer are not null
        if (customer != null) {
            // Update account and customer details
            // Handle file uploads

                // Đường dẫn lưu file
                String filePath = idCardFrontImage.getOriginalFilename();
                idCardFrontImage.transferTo(new File(filePath));

                // Lưu ảnh vào bảng Image
                Image image = new Image();
                image.setPath(UPLOAD_DIRECTORY + idCardFrontImage.getOriginalFilename());
                image.setUploadDate(LocalDateTime.now().toString());
                imageService.saveImage(image);

                // Gán đối tượng Image cho News

                // Đường dẫn lưu file
                String filePath2 = idCardBackImage.getOriginalFilename();
                idCardBackImage.transferTo(new File(filePath2));

                // Lưu ảnh vào bảng Image
                Image image2 = new Image();
                image.setPath(UPLOAD_DIRECTORY + idCardBackImage.getOriginalFilename());
                image.setUploadDate(LocalDateTime.now().toString());
                imageService.saveImage(image2);

                // Gán đối tượng Image cho News
                customerService.save(customer, account.getId());
        }
        else
            {
            // Handle the case where account or customer is null
            model.addAttribute("errorMessage", "Account or Customer data is missing.");
            return "/customer/profile";
        }
        return "redirect:/customer/profile";
    }

    @PostMapping("/uploadAvatar")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile avatar, Model model, Principal principal) throws IOException {
        Account account = accountService.findByUsername(principal.getName());
            if (avatar != null && !avatar.isEmpty()) {
                // Đường dẫn lưu file
                String filePath = avatar.getOriginalFilename();
                avatar.transferTo(new File(filePath));

                // Lưu ảnh vào bảng Image
                Image image = new Image();
                image.setPath(UPLOAD_DIRECTORY + avatar.getOriginalFilename());
                image.setUploadDate(LocalDateTime.now().toString());
                imageService.saveImage(image);

                // Gán đối tượng Image cho News
                accountService.save(account);
            }

        return "redirect:/customer/profile";
    }

    @GetMapping("/changePassword")
    public String showChangePasswordForm(Model model, Principal principal) {
        String name = principal.getName();
        Account user = accountService.findByUsername(name);

        // Đưa thông tin người dùng vào model (nếu cần)
        model.addAttribute("user", user);

        return "customer/profile";
    }


    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Principal principal, Model model) {
        String name = principal.getName();
        Account user = accountService.findByUsername(name);

        // Kiểm tra mật khẩu hiện tại có đúng không
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            model.addAttribute("passwordError", "Mật khẩu hiện tại không đúng.");
            model.addAttribute("user", user);
            return "/customer/profile";
        }

        // Kiểm tra nếu mật khẩu mới giống với mật khẩu hiện tại
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            model.addAttribute("passwordError", "Mật khẩu mới không được giống mật khẩu hiện tại.");
            model.addAttribute("user", user);
            return "/customer/profile";
        }

        // Kiểm tra xem mật khẩu mới có khớp với xác nhận mật khẩu không
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("passwordError", "Mật khẩu mới và xác nhận không khớp.");
            model.addAttribute("user", user);
            return "/customer/profile";
        }

        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(newPassword));

        accountService.save(user);

        model.addAttribute("passwordSuccess", "Mật khẩu đã được cập nhập.");
        model.addAttribute("user", user);
        return "/customer/profile";
    }



}
