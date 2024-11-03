package com.spring.mvc.controller;

import com.spring.mvc.common.FileUploadUtil;
import com.spring.mvc.dto.HouseRegisterDTO;
import com.spring.mvc.entity.*;
import com.spring.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/house-listing")
public class HouserRegisterController {
    private HouseService houseService;
    private FireEquipmentService fireEquipmentService;
    private AmenitiesService amenitiesService;
    private ImageService imageService;
    private AccountService accountService;
    private HouseOwnerService houseOwnerService;
    private ContractService contractService;
    private FileUploadUtil fileUploadUtil;

    public HouserRegisterController(HouseService houseService, FireEquipmentService fireEquipmentService, AmenitiesService amenitiesService, ImageService imageService, AccountService accountService, HouseOwnerService houseOwnerService, ContractService contractService, FileUploadUtil fileUploadUtil) {
        this.houseService = houseService;
        this.fireEquipmentService = fireEquipmentService;
        this.amenitiesService = amenitiesService;
        this.imageService = imageService;
        this.accountService = accountService;
        this.houseOwnerService = houseOwnerService;
        this.contractService = contractService;
        this.fileUploadUtil = fileUploadUtil;
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
    public String houserRegister(@ModelAttribute("houseRegisterDTO") HouseRegisterDTO houseform,
                                 @RequestParam(value = "fireEquipments") List<Integer> fireEquipments,
                                 @RequestParam(value = "amenities") List<Integer> amenities,
                                 @RequestParam(value = "images") List<MultipartFile> images,
                                 @RequestParam(value = "documents") List<MultipartFile> documents,
                                 Model model, Principal principal) {
        //Lâý session người đăng nhap
        System.out.println(houseform.getProvince());
        String username = principal.getName();
        Account account = accountService.findByUsername(username);
        //Lưu House vào database
        LocalDateTime createdDate = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = createdDate.format(formatter);
        House house = new House(houseform.getName(), houseform.getWard(), houseform.getDistrict(), houseform.getProvince(),
                                houseform.getLocation(), houseform.getLand_space(), houseform.getLiving_space(), houseform.getNumber_bed_room(),
                                houseform.getDescription(), houseform.getNumber_bath(), houseform.getCoordinates_on_map(),"0", formattedDate, account);

        //Lưu  Contract
        Contract contract = new Contract(houseform.getPrice(), houseform.getLease_duration_day(), formattedDate);
        //Gắn các mối quan hệ
        //------------------------------------------------------------------
        //Amenities cho House (many-to-many)
        if (amenities != null) {
            for (Integer id : amenities) {
                Amenities amenity = amenitiesService.findById(id);
                house.addAmenities(amenity);
                amenity.addHouse(house);
            }
        }
        //FireEquipment cho House
        if(fireEquipments != null){
            for (Integer id : fireEquipments) {
                FireEquipments fireEquipment = fireEquipmentService.findById(id);
                house.addFireEquipments(fireEquipment);
                fireEquipment.addHouse(house);
            }
        }
        //Quét dữ liệu kiểm tra HouseOwner đã tồn tại hay chưa = email
        HouseOwner houseOwner = houseOwnerService.findByEmail(houseform.getHouseOwnerEmail());
        if(houseOwner == null){
            //Lưu HouseOwner vào database nếu chưa tồn tại
            houseOwner = new HouseOwner(houseform.getHouseOwnerPhone()) ;
            //House cho HouseOwner
            house.setOwner(houseOwner);
            houseOwnerService.save(houseOwner);
        }
        //Contract toi House
        contract.setHouse(house);
        //Contract toi HouseOwner
        contract.setOwner(houseOwner);
        //create_by to Contract
        contract.setCreated_by(account);
        //Document to Contract
        fileUploadUtil.UploadDocumentForContract(documents, contract);
        //Luu lan luot tat ca
        houseOwnerService.save(houseOwner);
        houseService.save(house);
        //Image to House
        fileUploadUtil.UploadImagesForHouse(images, house);
        contractService.save(contract);
        return "house_listing_agent/house-register-success";
    }

    @GetMapping()
    public String homeAgent(Model model) {
        List<House> houses =houseService.getAllHouses();
        model.addAttribute("houses", houses);
        return "house_listing_agent/HomeAgent";
    }

    @GetMapping("owner")
    public String listOwner(Model model) {
        List<HouseOwner> houseOwner = houseOwnerService.findAll();
        model.addAttribute("houseOwner", houseOwner);
        return "house_listing_agent/HomeAgent";
    }
    @GetMapping("owner-detail")
    public String viewOwner(@RequestParam("id") int id, Model model, Principal principal) {
        HouseOwner houseOwner = houseOwnerService.findById(id);
        List<House> allHouses = houseService.findHousebyHouseOwner(houseOwner);
        String username = principal.getName();
        Account account = accountService.findByUsername(username);
        model.addAttribute("houseOwner", houseOwner);
        model.addAttribute("allHouses", allHouses);
        model.addAttribute("staff", account);
        return "house_listing_agent/houseOwner";
    }

}
