package com.spring.mvc.controller;

import com.spring.mvc.dto.NewsDTO;
import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Image;
import com.spring.mvc.entity.News;
import com.spring.mvc.entity.TagForNews;
import com.spring.mvc.service.AccountService;
import com.spring.mvc.service.ImageService;
import com.spring.mvc.service.NewsService;
import com.spring.mvc.service.TagForNewsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/news_writer")
public class NewsWriterController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private TagForNewsService tagForNewsService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private ImageService imageService;

    private static final String UPLOAD_DIRECTORY = "E:\\Semester5\\HFS301\\PROJECT\\HSF301_RentingHouse\\src\\main\\resources\\static\\image";



    @Autowired
    public NewsWriterController(NewsService newsService, TagForNewsService tagForNewsService,
                                AccountService accountService, ImageService imageService) {
        this.newsService = newsService;
        this.tagForNewsService = tagForNewsService;
        this.accountService = accountService;
        this.imageService = imageService;
    }
    @GetMapping("/dashboard")
    public String dashboard( Model model) {
        return "newsWriter/Dashboard";
    }

//    @GetMapping("/profile")
//    public String showProfile(Model model, Principal principal) {
//        String username = principal.getName();
//        Account account = accountService.findByUsername(username);
//        if (account != null) {
//            StaffDTO staffDTO = new StaffDTO();
//            staffDTO.setAccount(account);
//            staffDTO.setStaff(account.getStaff());
//            model.addAttribute("staffDTO", staffDTO);
//        }
//        return "/newsWriter/profile";
//    }

    @GetMapping("/create_news")
    public String createNews(Model model) {
        model.addAttribute("newsDTO", new NewsDTO());
        model.addAttribute("listTag", tagForNewsService.getAllTag());
        return "newsWriter/CreateNews";
    }

    @PostMapping("/create_news")
    public String addNews(@ModelAttribute("newsDTO") NewsDTO newsDTO,
//                          @RequestParam(value = "selectedTags") List<Integer> selectedTags,
                          @RequestParam(value = "images") MultipartFile images,
                          RedirectAttributes redirectAttributes, Principal  principal) throws IOException {



        News news = new News();

        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setCreated_date(LocalDateTime.now().toString());
        if (images != null && !images.isEmpty()) {
            // Đường dẫn lưu file
            String filePath = images.getOriginalFilename();
            images.transferTo(new File(filePath));

            // Lưu ảnh vào bảng Image
            Image image = new Image();
            image.setPath(UPLOAD_DIRECTORY+images.getOriginalFilename());
            image.setUploadDate(LocalDateTime.now().toString());
            imageService.saveImage(image);

            // Gán đối tượng Image cho News
            news.setImages(image);
        }

        String username = principal.getName();
        Account account = accountService.findByUsername(username);

        news.setAccount(account);

//        if (selectedTags != null) {
//            for (Integer tagId : selectedTags) {
//                TagForNews tag = tagForNewsService.getTagById(tagId);
//                news.addTag(tag);
//                tag.addNews(news);
//            }
//        }

        newsService.save(news);
        redirectAttributes.addAttribute("message", "The news was created successfully");
        return "newsWriter/CreateNews";
    }


    @GetMapping("/get_all_news_list")
    public String getAllNews(Model model) {
        List<News> list = newsService.getAllNews();
        model.addAttribute("listNews", list);
        model.addAttribute("pageTitle", "View all news");
        model.addAttribute("deletePermission", "false");
        return "newsWriter/NewsList";
    }

    @GetMapping("/get_own_news_list")
    public String getOwnNews(Model model, Principal principal) {
        String username = principal.getName();
        Account this_user = accountService.findByUsername(username);
        List<News> list = newsService.getAllNewsByAuthorId(this_user.getId());
        model.addAttribute("listNews", list);
        model.addAttribute("pageTitle", "View my own news");
        model.addAttribute("deletePermission", "true");
        return "newsWriter/NewsList";
    }

    @GetMapping("/viewDetail")
    public String getNewsById(@RequestParam("newsId") int newsId, Model model, HttpServletRequest request) {
        String previousUrl = request.getHeader("Referer");
        System.out.println(previousUrl);
        if (newsId <= 0) {
            return "redirect:/news_writer/get_all_news_list";
        }
        News news = newsService.getNewsById(newsId);
        if(news==null){
            return "redirect:/news_writer/get_all_news_list";
        }
        model.addAttribute("news", news);
        return "newsWriter/NewsDetail";
    }


//    @GetMapping("/deleteNews")
//    public String deleteNews(@RequestParam("newsId") int newsId, Principal principal) {
//        String username = principal.getName();
//        Account this_user = accountService.findByUsername(username);
//        if (newsId <= 0) {
//            return "redirect:/news_writer/get_own_news_list";
//        }
//        News news = newsService.getNewsById(newsId);
//        if(news==null){
//            return "redirect:/news_writer/get_own_news_list";
//        }
//        if(news.getAccount().getId()==this_user.getId()) {
//            uploadFile.deleteFile(news.getImages().getPath());
//            newsService.deleteNewsById(newsId);
//        }
//        return "redirect:/news_writer/get_own_news_list";
//    }

//    @PostMapping("/uploadAvatar")
//    public String uploadAvatar(@RequestParam("avatar") MultipartFile avatar, Model model) {
//        Account account = userDetailsService.accountAuthenticated();
//        if (account != null&&avatar!=null) {
//            uploadFile.UploadAvatar(avatar, account);
//        }
//        return "redirect:/news_writer/profile";
//    }
}
