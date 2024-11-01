package com.spring.mvc.cotroller;

import com.spring.mvc.common.FileUploadUtil;
import com.spring.mvc.dto.NewsDTO;
import com.spring.mvc.entity.News;
import com.spring.mvc.entity.TagForNews;
import com.spring.mvc.service.NewsService;
import com.spring.mvc.service.TagForNewsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/news_writer")
public class NewsWriterController {
    private NewsService newsService;
    private TagForNewsService tagForNewsService;
    private FileUploadUtil uploadFile;

    @Autowired
    public NewsWriterController(NewsService newsService, TagForNewsService tagForNewsService, FileUploadUtil uploadFile) {
        this.newsService = newsService;
        this.tagForNewsService = tagForNewsService;
        this.uploadFile = uploadFile;
    }
    @GetMapping("/dashboard")
    public String dashboard( Model model) {
        return "newsWriter/Dashboard";
    }

//    @GetMapping("/profile")
//    public String showProfile(Model model) {
//        Account account = userDetailsService.accountAuthenticated();
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

    @PostMapping("/add")
    public String addNews(@ModelAttribute("newsDTO") NewsDTO newsDTO,
                          @RequestParam(value = "selectedTags", required = false) List<Integer> selectedTags,
                          @RequestParam("images") List<MultipartFile> images,
                          RedirectAttributes redirectAttributes) {
        News news = newsDTO.getNews();
        uploadFile.UploadImagesForNews(images, news);
        if(selectedTags != null) {
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
    public String getOwnNews(Model model) {
//        Account this_user = userDetailsService.accountAuthenticated();
        List<News> list = newsService.getAllNewsByAuthorId(5);
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
//    public String deleteNews(@RequestParam("newsId") int newsId) {
//        Account account = userDetailsService.accountAuthenticated();
//        if (newsId <= 0) {
//            return "redirect:/news_writer/get_own_news_list";
//        }
//        News news = newsService.getNewsById(newsId);
//        if(news==null){
//            return "redirect:/news_writer/get_own_news_list";
//        }
//        if(news.getStaff().getAccountId()==account.getAccountId()) {
//            uploadFile.deleteFile(news.getCover_photo().getPath());
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
