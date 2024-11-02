package com.spring.mvc.controller;

import com.spring.mvc.entity.*;
import com.spring.mvc.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.mvc.common.QrCode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    public CustomerController(NewsService newsService, TagForNewsService tagForNewsService,
                              TagService tagService, QrCode qrCode, HouseService houseService,
                              AccountService accountService, HouseRegisterService houseRegisterService) {
        this.newsService = newsService;
        this.tagForNewsService = tagForNewsService;
        this.tagService = tagService;
        this.qrCode = qrCode;
        this.houseService = houseService;
        this.accountService = accountService;
        this.houseRegisterService = houseRegisterService;
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

    @GetMapping("/get_all_house")
    public String getAllHouse(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        model.addAttribute("isLoggedIn", isLoggedIn);
        List<House> houseList = houseService.getAllHouses();
        model.addAttribute("listHouse", houseList);
        return "customer/houseList";
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
            return "redirect:/customer/get_all_house";
        }
        News news = newsService.getNewsById(newsId);
        if (news == null) {
            return "redirect:/customer/get_all_house";
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
                               Model model, Principal  principal) {
        if (houseId <= 0) {
            return "redirect:/customer/get_all_auction";
        }
        House house = houseService.findById(houseId);
        if (house == null) {
            return "redirect:/customer/get_all_auction";
        }
        //lay ra nguoi dang ky
        String username = principal.getName();
        Account account = accountService.findByUsername(username);
//        String embedUrl = GetSrcInGoogleMapEmbededURLUtil.extractSrcFromIframe(auction.getAsset().getCoordinatesOnMap());
//        model.addAttribute("embedUrl", embedUrl);
        model.addAttribute("house", house);
        qrCode.setAmount(house.getContracts().get(0).getPrice()+"");
        qrCode.setDescription("UserId " + account.getId() + " contract price " + house.getId());
        model.addAttribute("qrCode", qrCode);
        HouseRegister register = houseRegisterService.getByHouseIdAccountId(houseId, account.getId());
        if (register != null) {
            model.addAttribute("register", register);
        }
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "customer/houseDetail";
    }

//    @GetMapping("/joinAuctionDetail")
//    public String accessAuction(@RequestParam String auctionId, Model model) {
//        // Truy vấn Account now
//        int auctionIdUsing = Integer.parseInt(auctionId);
//        Account currentAccount = userDetailsService.accountAuthenticated();
//
//        if (currentAccount == null) {
//            return getAuctionById("User account not found.", auctionIdUsing, model);
//        }
//
//        // Kiểm tra xem người dùng có được phép truy cập phiên đấu giá không
//        if (!auctionService.isUserAllowedToAccessAuction(auctionIdUsing, currentAccount.getAccountId())) {
//            return getAuctionById("You are not allowed to access this auction.", auctionIdUsing, model);
//        }
//
//        // Lấy thông tin phiên đấu giá
//        AuctionSession auctionSession = auctionService.getAuctionSessionById(auctionIdUsing);
//        List<Bid> bidList = bidService.getAllBidsByAuctionId(auctionIdUsing);
//
//        LocalDateTime now = LocalDateTime.now();
//
//        // Kiểm tra thời gian của phiên đấu giá
//        if (now.isBefore(auctionSession.getStartTime())) {
//            return getAuctionById("The auction has not started yet.", auctionIdUsing, model);
//        } else if (auctionSession.getActualEndTime() != null && now.isAfter(auctionSession.getActualEndTime())) {
//            return getAuctionById("The auction has already ended.", auctionIdUsing, model);
//        }
//
//        AuctionSession auction = auctionService.getAuctionSessionById(auctionIdUsing);
//        String embedUrl = GetSrcInGoogleMapEmbededURLUtil.extractSrcFromIframe(auction.getAsset().getCoordinatesOnMap());
//        model.addAttribute("embedUrl", embedUrl);
//        model.addAttribute("auction", auction);
//
//        // Truyền dữ liệu phiên đấu giá đến View
//        model.addAttribute("auctionSession", auctionSession);
//        model.addAttribute("accountCustomer", currentAccount);
//        model.addAttribute("bidList", bidList);
//
//        // Trả về tên của view đấu giá
//        return "customer/auctionPage";
//    }
//
//    @PostMapping("/registerAuction")
//    public String registerAuction(@RequestParam(value = "validate", required = false) String validate,
//                                  @RequestParam("auctionId") int auctionId, RedirectAttributes redirectAttributes) {
//        if (auctionId <= 0) {
//            return "redirect:/customer/get_all_auction";
//        }
//        AuctionSession auction = auctionService.getAuctionSessionById(auctionId);
//        if (auction == null) {
//            return "redirect:/customer/get_all_auction";
//        }
//        //check xem nguoi dung da validate tai khoan chua
//        Account this_user = userDetailsService.accountAuthenticated();
//        // Kiểm tra tài khoản đã xác thực chưa
//        if (this_user.getVerify() == 1) {
//            if (validate != null) {
//                // Cập nhật trạng thái đăng ký vào database
//                AuctionRegister register = new AuctionRegister(auction, this_user, "Waiting for payment", null, null, null, LocalDateTime.now());
//                auctionRegisterService.createAuctionRegister(register);
//
//                // Tạo thông báo sau khi đăng ký thành công
//                Notification notification = new Notification();
//                notification.setContent("You have registered to participate in the auction, please transfer money to complete the procedure.");
//                notification.setCreatedDate(LocalDateTime.now());
//                notification.setReadStatus("unread"); // Trạng thái chưa đọc
//
//                // Lưu thông báo vào cơ sở dữ liệu và gửi SSE cho client
//                notification.addAccount(this_user);
//                this_user.addNotification(notification);
//                notificationService.saveNotification(notification);
//                notificationService.sendNotification(notification); // Gửi SSE tới client
//
//                redirectAttributes.addFlashAttribute("error", "Registration successful, please transfer the deposit and register fee");
//            }
//        } else {
//            // Tài khoản chưa xác thực
//            redirectAttributes.addFlashAttribute("error", "Please complete your personal information before registering for the auction");
//            if (LocalDateTime.now().isAfter(auction.getRegistrationOpenDate()) && LocalDateTime.now().isBefore(auction.getRegistrationCloseDate())) {
//                if (this_user.getVerify() == 1) {
//                    //kiem tra xem nguoi dung da tick het chua
//                    if (validate != null) {
//                        //cap nhat trang thai vao database
//                        AuctionRegister register = new AuctionRegister(auction, this_user, "Waiting for payment", null, null, null, LocalDateTime.now());
//                        auctionRegisterService.createAuctionRegister(register);
//                        redirectAttributes.addFlashAttribute("error", "Registration successful, please transfer the deposit and register fee");
//                    }
//                } else {
//                    //gui thong bao tai khoan chua validate
//                    redirectAttributes.addFlashAttribute("error", "Please complete your personal information before registering for the auction");
//                }
//            }
//        }
//
//        return "redirect:/customer/viewAuctionDetail?auctionId=" + auctionId;
//    }
//
//
//    @PostMapping("/transferDepositAndFee")
//    public String transferDepositAndFee(@RequestParam(value = "transfer", required = false) String transfer,
//                                        @RequestParam("auctionId") int auctionId,
//                                        @RequestParam("auctionRegisterId") int auctionRegisterId, RedirectAttributes redirectAttributes) {
//        if (auctionId <= 0) {
//            return "redirect:/customer/get_all_auction";
//        }
//        AuctionSession auction = auctionService.getAuctionSessionById(auctionId);
//        if (auction == null) {
//            return "redirect:/customer/get_all_auction";
//        }
//        if (LocalDateTime.now().isAfter(auction.getRegistrationOpenDate()) && LocalDateTime.now().isBefore(auction.getRegistrationCloseDate())) {
//            //check xem nguoi dung da chuyen tien chua
//            if (transfer != null) {
//                AuctionRegister auctionRegister = auctionRegisterService.getAuctionRegisterById(auctionRegisterId);
//                auctionRegister.setRegisterStatus("Waiting for confirmation");
//                auctionRegisterService.updateRegisterStatus(auctionRegister);
//                redirectAttributes.addFlashAttribute("error", "Please wait while we confirm the transaction, the result will be sent to you via notification");
//            } else {
//                redirectAttributes.addFlashAttribute("error", "Please make sure to transfer the deposit and register fee before the auction registration deadline");
//            }
//            Account this_user = userDetailsService.accountAuthenticated();
//            //check xem nguoi dung da chuyen tien chua
//            if (transfer != null) {
//                AuctionRegister auctionRegister = auctionRegisterService.getAuctionRegisterById(auctionRegisterId);
//                auctionRegister.setRegisterStatus("dang cho xac nhan chuyen tien");
//                auctionRegisterService.updateRegisterStatus(auctionRegister);
//                redirectAttributes.addFlashAttribute("error", "Please wait while we confirm the transaction, the result will be sent to you via notification");
//
//                // Tạo thông báo sau khi đăng ký thành công
//                Notification notification = new Notification();
//                notification.setContent("You have transfer deposit and fee. Please wait while we confirm the transaction, the result will be sent to you via notification");
//                notification.setCreatedDate(LocalDateTime.now());
//                notification.setReadStatus("unread"); // Trạng thái chưa đọc
//
//                // Lưu thông báo vào cơ sở dữ liệu và gửi SSE cho client
//                notification.addAccount(this_user);
//                this_user.addNotification(notification);
//                notificationService.saveNotification(notification);
//                notificationService.sendNotification(notification); // Gửi SSE tới client
//            } else {
//                redirectAttributes.addFlashAttribute("error", "Please make sure to transfer the deposit and register fee before the auction registration deadline");
//                // Tạo thông báo sau khi đăng ký thành công
//                Notification notification = new Notification();
//                notification.setContent("Please make sure to transfer the deposit and register fee before the auction registration deadline");
//                notification.setCreatedDate(LocalDateTime.now());
//                notification.setReadStatus("unread"); // Trạng thái chưa đọc
//
//                // Lưu thông báo vào cơ sở dữ liệu và gửi SSE cho client
//                notification.addAccount(this_user);
//                this_user.addNotification(notification);
//                notificationService.saveNotification(notification);
//                notificationService.sendNotification(notification); // Gửi SSE tới client
//            }
//        }
//        return "redirect:/customer/viewAuctionDetail?auctionId=" + auctionId;
//    }

}
