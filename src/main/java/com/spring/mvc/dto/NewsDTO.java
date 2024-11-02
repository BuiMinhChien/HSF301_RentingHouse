package com.spring.mvc.dto;

import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Image;
import com.spring.mvc.entity.News;
import com.spring.mvc.entity.TagForNews;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Setter
@Getter
public class NewsDTO {
    private int id;
    private String title;
    private String content;
    private String created_date;
    private MultipartFile images;
    private Account account;
    private List<TagForNews> tags;

}
