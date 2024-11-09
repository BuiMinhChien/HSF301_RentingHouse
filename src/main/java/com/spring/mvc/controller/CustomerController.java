package com.spring.mvc.controller;

import com.spring.mvc.common.GetSrcInGoogleMapEmbededURLUtil;
import com.spring.mvc.dto.NotificationDTO;
import com.spring.mvc.entity.*;
import com.spring.mvc.service.*;
import com.spring.mvc.common.FileUploadUtil;
import com.spring.mvc.entity.*;
import com.spring.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private NewsService newsService;
    private TagForNewsService tagForNewsService;
    private TagService tagService;
    private QrCode qrCode;
    private HouseService houseService;
    private AccountService accountService;
    private HouseRegisterService houseRegisterService;
    private NotificationService notificationService;
    private ContractService contractService;
    @Autowired
    private CustomerService customerService;
    private PasswordEncoder passwordEncoder;

    private FileUploadUtil fileUploadUtil;

    public CustomerController(NewsService newsService, TagForNewsService tagForNewsService,
                              TagService tagService, QrCode qrCode, HouseService houseService,
                              AccountService accountService, HouseRegisterService houseRegisterService,
                              ContractService contractService, NotificationService notificationService) {
        this.newsService = newsService;
        this.tagForNewsService = tagForNewsService;
        this.tagService = tagService;
        this.qrCode = qrCode;
        this.houseService = houseService;
        this.accountService = accountService;
        this.houseRegisterService = houseRegisterService;
        this.contractService = contractService;
        this.notificationService = notificationService;
    }

    @GetMapping("/get_all_news")
    public String getAllNews(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        model.addAttribute("isLoggedIn", isLoggedIn);

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

    @GetMapping("/get_all_house")
    public String getAllHouse(Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        model.addAttribute("isLoggedIn", isLoggedIn);
        List<House> houseList = houseService.getAllHouses();

        Account account = accountService.findByUsername(principal.getName());
        model.addAttribute("accountId", account.getId());


        model.addAttribute("listHouse", houseList);
        return "customer/houseList";
    }

    @GetMapping("/viewNewsDetail")
    public String getNewsById(@RequestParam("newsId") int newsId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        model.addAttribute("isLoggedIn", isLoggedIn);

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

    @GetMapping("/viewHouseDetail")
    public String getHouseById(@RequestParam(value = "error", required = false) String error, @RequestParam("houseId") int houseId,
                               Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        model.addAttribute("isLoggedIn", isLoggedIn);

        if (houseId <= 0) {
            return "redirect:/customer/get_all_house";
        }
        House house = houseService.findById(houseId);
        if (house == null) {
            return "redirect:/customer/get_all_house";
        }
        //lay ra nguoi dang ky
        String username = principal.getName();
        Account account = accountService.findByUsername(username);
        String embedUrl = GetSrcInGoogleMapEmbededURLUtil.extractSrcFromIframe(house.getCoordinates_on_map());
        model.addAttribute("embedUrl", embedUrl);
        model.addAttribute("house", house);
        qrCode.setAmount(house.getContract().getPrice()+"");
        qrCode.setDescription("UserId " + account.getId() + " contract price " + house.getId());
        model.addAttribute("qrCode", qrCode);
        HouseRegister register = houseRegisterService.getByHouseIdAccountId(houseId, account.getId());
        if (register != null) {
            model.addAttribute("register", register);
        }
        if (error != null) {
            model.addAttribute("error", error);
        }
        model.addAttribute("accountId", account.getId());
        return "customer/houseDetail";
    }

    @PostMapping("/registerHouse")
    public String registerHouse(@RequestParam(value = "validate", required = false) String validate,
                                @RequestParam("houseId") int houseId, RedirectAttributes redirectAttributes,Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        redirectAttributes.addAttribute("isLoggedIn", isLoggedIn);
        if (houseId <= 0) {
            return "redirect:/customer/get_all_house";
        }
        House house = houseService.findById(houseId);
        if (house == null) {
            return "redirect:/customer/get_all_house";
        }
        //check xem nguoi dung da validate tai khoan chua
        String username = principal.getName();
        Account account = accountService.findByUsername(username);
        // Kiểm tra tài khoản đã xác thực chưa
        if (account.getVerify().equals("1")) {
            if (validate != null) {
                // Cập nhật trạng thái đăng ký vào database
                HouseRegister register = new HouseRegister();
                register.setHouse(house);
                register.setAccount(account);
                register.setResult(null);
                register.setDeposit_status("Not transferred");
                register.setRegistration_time(LocalDateTime.now().toString());
                houseRegisterService.save(register);

                // Tạo thông báo sau khi đăng ký thành công
                Notification notification = new Notification();
                notification.setContent("You have registered to rent house, please transfer money to complete the procedure.");
                notification.setCreated_date(LocalDateTime.now().toString());
                notification.setRead_status("unread"); // Trạng thái chưa đọc
                notification.setHouse(house);

                // Lưu thông báo vào cơ sở dữ liệu và gửi SSE cho client
                notification.addAccount(account);
                account.addNotification(notification);
                notificationService.saveNotification(notification);
                notificationService.sendNotification(notification); // Gửi SSE tới client

                redirectAttributes.addFlashAttribute("error", "Registration successful, please transfer the register fee");
            }
        }
        return "redirect:/customer/viewHouseDetail?houseId=" + houseId;
    }


    @PostMapping("/transferRegisterFee")
    public String transferDepositAndFee(@RequestParam(value = "transfer", required = false) String transfer,
                                        @RequestParam("houseId") int houseId,
                                        @RequestParam(value = "move_in_date", required = false) String move_in_date,
                                        @RequestParam("registerId") int registerId, RedirectAttributes redirectAttributes,
                                        Principal principal) {
        if (houseId <= 0) {
            return "redirect:/customer/get_all_house";
        }
        House house = houseService.findById(houseId);
        if (house == null) {
            return "redirect:/customer/get_all_house";
        }
        HouseRegister register = houseRegisterService.getByRegisterId(registerId);
        String username = principal.getName();
        Account account = accountService.findByUsername(username);
        if(register != null){
            if (!house.getAvailable_status().equals("1")) {
                //check xem nguoi dung da chuyen tien chua
                if (transfer != null) {
                    //KY KET HOP DONG
                    Contract contract = contractService.getContractByHouseId(houseId);
                    contract.setAccount(register.getAccount());
                    if(move_in_date!=null && !move_in_date.isEmpty()) contract.setMove_in_date(move_in_date);
                    contract.setSigned_date(LocalDateTime.now().toString());
                    contractService.updateContract(contract);
                    //THAY DOI REGISTER
                    register.setResult("Successful");
                    register.setDeposit_status("Transferred");
                    houseRegisterService.update(register);
                    house.setAvailable_status("1");
                    //THAY DOI HOUSE
                    houseService.update(house);
                    // Tạo thông báo sau khi đăng ký thành công
                    Notification notification = new Notification();
                    notification.setContent("You have transfer deposit successful!");
                    notification.setCreated_date(LocalDateTime.now().toString());
                    notification.setRead_status("unread"); // Trạng thái chưa đọc
                    notification.setHouse(house);
                    notification.addAccount(account);

                    // Lưu thông báo vào cơ sở dữ liệu và gửi SSE cho client
                    notificationService.saveNotification(notification);
                    accountService.updateWithNotification(account, notification);
                    notificationService.sendNotification(notification); // Gửi SSE tới client
                    redirectAttributes.addFlashAttribute("error", "Renting successfully");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Please make sure to transfer the register fee as soon as possible");
                    // Tạo thông báo sau khi đăng ký thành công
                    Notification notification = new Notification();
                    notification.setContent("Please make sure to transfer to complete the register fee!");
                    notification.setCreated_date(LocalDateTime.now().toString());
                    notification.setRead_status("unread"); // Trạng thái chưa đọc

                    // Lưu thông báo vào cơ sở dữ liệu và gửi SSE cho client
                    account.addNotification(notification);
                    notification.addAccount(account);
                    notificationService.saveNotification(notification);
                    notificationService.sendNotification(notification); // Gửi SSE tới client
                    redirectAttributes.addFlashAttribute("error", "Renting successfully");
                }
            }
            else{
                redirectAttributes.addFlashAttribute("error", "This house had been rented");
            }
        }
        else{
            redirectAttributes.addFlashAttribute("error", "You must register first");
        }
        return "redirect:/customer/viewHouseDetail?houseId=" + houseId;
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        model.addAttribute("isLoggedIn", isLoggedIn); // Truyền biến vào view
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

    @GetMapping("/filter_houses")
    public String filterHouses(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "province", required = false) String province,
            @RequestParam(value = "district", required = false) String district,
            @RequestParam(value = "ward", required = false) String ward,
            Model model) {
        // Gọi service để lấy danh sách nhà dựa vào các bộ lọc
        List<House> filteredHouses = houseService.filterHouses(status, province, district, ward);
        System.out.println(filteredHouses.size());
        // Thêm danh sách nhà đã lọc vào model
        model.addAttribute("listHouse", filteredHouses);

        // Trả về fragment để cập nhật danh sách nhà trong giao diện
        return "customer/houseList :: houseListFragment";
    }
    @GetMapping("/viewRegisterHistory")
    public String getRegisterHistory(Model model, Principal principal) {
        Account account = accountService.findByUsername(principal.getName());
        List<HouseRegister> registerList = houseRegisterService.getAllByAccountId(account.getId());
        model.addAttribute("registerList", registerList);
        return "customer/registerHistory";
    }

    @GetMapping("/viewNotification")
    public String getNotificationList(Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        model.addAttribute("isLoggedIn", isLoggedIn); // Truyền biến vào view

        Account account = accountService.findByUsername(principal.getName());
        model.addAttribute("accountId", account.getId());

        List<NotificationDTO> notificationDTOList = notificationService.getNotificationsForAccount(account) ;
        model.addAttribute("notifications", notificationDTOList);
        return "customer/notificationList";
    }
}
