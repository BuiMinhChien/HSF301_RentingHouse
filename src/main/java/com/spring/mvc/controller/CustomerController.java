package com.spring.mvc.controller;

import com.spring.mvc.common.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.mvc.common.QrCode;
import com.spring.mvc.entity.News;
import com.spring.mvc.entity.TagForNews;
import com.spring.mvc.service.NewsService;
import com.spring.mvc.service.TagForNewsService;
import com.spring.mvc.service.TagService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private NewsService newsService;
    private TagForNewsService tagForNewsService;
    private TagService tagService;
    private QrCode qrCode;

    @Autowired
    AccountService accountService;

    @Autowired
    CustomerService customerService;


    PasswordEncoder passwordEncoder;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    public CustomerController(NewsService newsService, TagForNewsService tagForNewsService,
                              TagService tagService, QrCode qrCode) {
        this.newsService = newsService;
        this.tagForNewsService = tagForNewsService;
        this.tagService = tagService;
        this.qrCode = qrCode;
    }

    @GetMapping("/get_all_news")
    public String getAllNews(Model model) {
        List<News> newsList = newsService.getAllNews();
        model.addAttribute("listNews", newsList);
//        lay ra 3 bai viet moi nhat
        List<News> top3News = newsService.getTop3LatestNews();
        model.addAttribute("top3LatestNews", top3News);
//        lay danh sach cac tag
        List<TagForNews> tagList = tagForNewsService.getAllTag();
        model.addAttribute("listTag", tagList);
        return "customer/newsList";
    }

//    @GetMapping("/filter_news")
//    public String filterNews(
//            @RequestParam(required = false) List<Integer> tagIds,
//            @RequestParam(required = false) String keyword,
//            Model model) {
//        List<News> filteredNews = newsService.filterNews(tagIds, keyword);
//        model.addAttribute("listNews", filteredNews);
//        return "customer/newsList :: newsListFragment";
//    }

    @GetMapping("/viewNewsDetail")
    public String getNewsById(@RequestParam("newsId") int newsId, Model model) {
        if (newsId <= 0) {
            return "redirect:/customer/get_all_news";
        }
        News news = newsService.getNewsById(newsId);
        if (news == null) {
            return "redirect:/customer/get_all_news";
        }
        model.addAttribute("news", news);
//        lay ra 3 bai viet moi nhat
        List<News> top3News = newsService.getTop3LatestNews();
        model.addAttribute("top3LatestNews", top3News);
//        lay danh sach cac tag
        List<TagForNews> tagList = tagForNewsService.getAllTag();
        model.addAttribute("listTag", tagList);
        return "customer/newsDetail";
    }

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
            @RequestParam("idCardFrontImage") List<MultipartFile> idCardFrontImage,
            @RequestParam("idCardBackImage") List<MultipartFile> idCardBackImage, Model model, Principal principal) throws IOException {
        // Extract customer and account from DTO
        Account account = accountService.findByUsername(principal.getName());
        Customer customer = customerService.findCustomerById(account.getId());

        // Check if account and customer are not null
        if (customer != null) {
            // Gán đối tượng Image cho News
            customer.setFullName(profileDTO.getCustomer().getFullName());
            customer.setGender(profileDTO.getCustomer().getGender());
            customer.setAddress(profileDTO.getCustomer().getAddress());
            customer.setPhoneNumber(profileDTO.getCustomer().getPhoneNumber());
            customer.setFullName(profileDTO.getCustomer().getFullName());
            customer.setDateOfBirth(profileDTO.getCustomer().getDateOfBirth());
            customer.setIdIssuanceDate(profileDTO.getCustomer().getIdIssuanceDate());
            customer.setIdIssuancePlace(profileDTO.getCustomer().getIdIssuancePlace());
            fileUploadUtil.UploadImagesForCard(idCardFrontImage, customer);
            fileUploadUtil.UploadImagesForCard(idCardBackImage, customer);
            customerService.update(customer);
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
    public String uploadAvatar(@RequestParam("avatar") List<MultipartFile> avatar, Model model, Principal principal) throws IOException {
        Account account = accountService.findByUsername(principal.getName());
         fileUploadUtil.UploadImagesForAvata(avatar,account);
         accountService.update(account);
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
