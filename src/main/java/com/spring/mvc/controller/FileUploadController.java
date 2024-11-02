package com.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
public class FileUploadController {

    @PostMapping("/upload")
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView("uploadResult");

        if (!file.isEmpty()) {
            try {
                // Đường dẫn lưu file
                String filePath = "D:/file/" + file.getOriginalFilename();
                file.transferTo(new File(filePath));
                modelAndView.addObject("message", "File uploaded successfully: " + file.getOriginalFilename());
            } catch (IOException e) {
                modelAndView.addObject("message", "File upload failed: " + e.getMessage());
            }
        } else {
            modelAndView.addObject("message", "File is empty.");
        }

        return modelAndView;
    }
}
