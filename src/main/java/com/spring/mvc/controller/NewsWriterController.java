package com.spring.mvc.controller;

import com.spring.mvc.common.FileUploadUtil;
import com.spring.mvc.dto.NewsDTO;
import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.News;
import com.spring.mvc.entity.TagForNews;
import com.spring.mvc.service.AccountService;
import com.spring.mvc.service.ImageService;
import com.spring.mvc.service.NewsService;
import com.spring.mvc.service.TagForNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/news_writer")
public class NewsWriterController {
    private NewsService newsService;
    private TagForNewsService tagForNewsService;
    private AccountService accountService;
    private ImageService imageService;
    private FileUploadUtil fileUploadUtil;

    @Autowired
    public NewsWriterController(NewsService newsService, TagForNewsService tagForNewsService,
                                AccountService accountService, ImageService imageService, FileUploadUtil fileUploadUtil) {
        this.newsService = newsService;
        this.tagForNewsService = tagForNewsService;
        this.accountService = accountService;
        this.imageService = imageService;
        this.fileUploadUtil = fileUploadUtil;
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
                          @RequestParam(value = "selectedTags") List<Integer> selectedTags,
                          @RequestParam(value = "images") List<MultipartFile> images,
                          RedirectAttributes redirectAttributes, Principal  principal) throws IOException {
        News news = new News();
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setCreated_date(LocalDateTime.now().toString());
        fileUploadUtil.UploadImagesForNews(images, news);

        String username = principal.getName();
        Account account = accountService.findByUsername(username);
        news.setAccount(account);

        if (selectedTags != null) {
            for (Integer tagId : selectedTags) {
                TagForNews tag = tagForNewsService.getTagById(tagId);
                news.addTag(tag);
                tag.addNews(news);
            }
        }
        newsService.save(news);
        redirectAttributes.addAttribute("message", "The news was created successfully");
        return "redirect:/news_writer/create_news";
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
    public String getNewsById(@RequestParam("newsId") int newsId, Model model) {
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


    @GetMapping("/deleteNews")
    public String deleteNews(@RequestParam("newsId") int newsId, Principal principal) {
        String username = principal.getName();
        Account this_user = accountService.findByUsername(username);
        if (newsId <= 0) {
            return "redirect:/news_writer/get_own_news_list";
        }
        News news = newsService.getNewsById(newsId);
        if(news==null){
            return "redirect:/news_writer/get_own_news_list";
        }
        if(news.getAccount().getId()==this_user.getId()) {
            fileUploadUtil.deleteFile(news.getImages().getPath());
            newsService.deleteNewsById(newsId);
        }
        return "redirect:/news_writer/get_own_news_list";
    }

//    @PostMapping("/uploadAvatar")
//    public String uploadAvatar(@RequestParam("avatar") MultipartFile avatar, Model model) {
//        Account account = userDetailsService.accountAuthenticated();
//        if (account != null&&avatar!=null) {
//            uploadFile.UploadAvatar(avatar, account);
//        }
//        return "redirect:/news_writer/profile";
//    }
}
