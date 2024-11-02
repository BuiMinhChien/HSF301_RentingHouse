package com.spring.mvc.controller;

import com.spring.mvc.entity.House;
import com.spring.mvc.service.HouseService;
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

    public CustomerController(NewsService newsService, TagForNewsService tagForNewsService,
                              TagService tagService, QrCode qrCode, HouseService houseService) {
        this.newsService = newsService;
        this.tagForNewsService = tagForNewsService;
        this.tagService = tagService;
        this.qrCode = qrCode;
        this.houseService = houseService;
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


}
