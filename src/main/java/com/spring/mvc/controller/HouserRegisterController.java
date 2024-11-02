package com.spring.mvc.controller;

import com.spring.mvc.dto.HouseRegisterDTO;
import com.spring.mvc.entity.Amenities;
import com.spring.mvc.entity.FireEquipments;
import com.spring.mvc.service.AmenitiesService;
import com.spring.mvc.service.FireEquipmentService;
import com.spring.mvc.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("house-listing")
public class HouserRegisterController {
    private HouseService houseService;
    private FireEquipmentService fireEquipmentService;
    private AmenitiesService amenitiesService;

    public HouserRegisterController(HouseService houseService, FireEquipmentService fireEquipmentService, AmenitiesService amenitiesService) {
        this.houseService = houseService;
        this.fireEquipmentService = fireEquipmentService;
        this.amenitiesService = amenitiesService;
    }

    @GetMapping("register-form")
    public String houserRegister(Model model) {
        HouseRegisterDTO houseRegisterDTO = new HouseRegisterDTO();
        model.addAttribute("houseRegisterDTO", houseRegisterDTO);
        List<FireEquipments> fireEquipments = fireEquipmentService.findAll();
        model.addAttribute("fireEquipments", fireEquipments);
        List<Amenities> listAmenities = amenitiesService.findAll();
        model.addAttribute("listAmenities", listAmenities);
        return "house_listing_agent/house-register-form";
    }
    @PostMapping("register")
    public String houserRegister(@ModelAttribute("houseRegisterDTO") HouseRegisterDTO houseRegisterDTO, Model model) {
        return "house_listing_agent/house-register-success";
    }

}
