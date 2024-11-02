package com.spring.mvc.controller;

import com.spring.mvc.dto.HouseRegisterDTO;
import com.spring.mvc.entity.*;
import com.spring.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("house-listing")
public class HouserRegisterController {
    private HouseService houseService;
    private FireEquipmentService fireEquipmentService;
    private AmenitiesService amenitiesService;
    private ImageService imageService;
    private AccountService accountService;
    private HouseOwnerService houseOwnerService;
    private ContractService contractService;


    private static final String UPLOAD_DIRECTORY = "E:\\Semester5\\HFS301\\PROJECT\\HSF301_RentingHouse\\src\\main\\resources\\static\\image";

    public HouserRegisterController(HouseService houseService, FireEquipmentService fireEquipmentService, AmenitiesService amenitiesService, ImageService imageService, AccountService accountService, HouseOwnerService houseOwnerService, ContractService contractService) {
        this.houseService = houseService;
        this.fireEquipmentService = fireEquipmentService;
        this.amenitiesService = amenitiesService;
        this.imageService = imageService;
        this.accountService = accountService;
        this.houseOwnerService = houseOwnerService;
        this.contractService = contractService;
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
    public String houserRegister(@ModelAttribute("houseRegisterDTO") HouseRegisterDTO houseform, Model model, Principal principal) {
        //Lâý session người dđăng nhap
        String username = principal.getName();
        Account account = accountService.findByUsername(username);
        //Lưu House vào database
        LocalDateTime createdDate = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = createdDate.format(formatter);
        House house = new House(houseform.getName(), houseform.getWard(), houseform.getDistrict(), houseform.getProvince(),
                                houseform.getLocation(), houseform.getLand_space(), houseform.getLiving_space(), houseform.getNumber_bed_room(),
                                houseform.getDescription(), houseform.getNumber_bath(), houseform.getCoordinates_on_map(),"Rảnh", formattedDate, account);
        //Lưu HouseOwner vào database
        HouseOwner houseOwner = new HouseOwner(houseform.getHouseOwnerPhone(),houseform.getHouseOwnerAddress(), houseform.getHouseOwnerName()) ;
        //Lưu  Contract
        Contract contract = new Contract(houseform.getPrice(), houseform.getLease_duration_day(), formattedDate);
        //Gắn các mối quan hệ
        //--------------------------------------
        //Amenities cho House
        house.setAmenities(houseform.getAmenities());
        //Taglist cho House
        house.setTagList(houseform.getTagList());
        //FireEquipment cho House
        house.setFireEquipments(houseform.getFireEquipments());
        //House cho HouseOwner
        house.setOwner(houseOwner);
        //Contract toi House
         contract.setHouse(house);
        //Contract toi HouseOwner
        contract.setOwner(houseOwner);
        //Image to House
        //Luu lan luot tat ca
        houseService.save(house);
        houseOwnerService.save(houseOwner);
        contractService.save(contract);
        return "house_listing_agent/house-register-success";
    }

}
