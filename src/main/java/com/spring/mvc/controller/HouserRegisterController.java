package com.spring.mvc.controller;

import com.spring.mvc.dto.HouseRegisterDTO;
import com.spring.mvc.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("house-listing")
public class HouserRegisterController {
    @Autowired
    private HouseService houseService;

    @GetMapping("register-form")
    public String houserRegister(Model model) {
        HouseRegisterDTO houseRegisterDTO = new HouseRegisterDTO();
        model.addAttribute("houseRegisterDTO", houseRegisterDTO);
        return "house_listing_agent/test-form";
    }
    @PostMapping("register")
    public String houserRegister(@ModelAttribute("houseRegisterDTO") HouseRegisterDTO houseRegisterDTO, Model model) {
        return "house_listing_agent/house-register-success";
    }

}
