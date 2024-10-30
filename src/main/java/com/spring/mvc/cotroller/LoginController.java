package com.spring.mvc.cotroller;

import com.spring.mvc.dto.AdminDTO;
import com.spring.mvc.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    private AdminService adminService;
    public LoginController(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("admin", new AdminDTO());
        return "Login";
    }
    @PostMapping("/login")
    public String login(AdminDTO adminDTO, Model model, HttpSession session) {
        Admin admin =  adminService.searchAdminByGmailAndPassword(adminDTO.getEmail(),adminDTO.getPassword());
        if(admin!=null) {
            AdminDTO temp = new AdminDTO();
            temp.setAdminId(admin.getAdminId());
            temp.setEmail(admin.getEmail());
            temp.setPassword(admin.getPassword());
            temp.setAdminName(admin.getAdminName());
            session.setAttribute("admin", temp);
            return "redirect:/agents/list";
        } else {
            model.addAttribute("message", "Tên đăng nhập hoặc mật khẩu không đúng");
        }
        model.addAttribute("admin", adminDTO);
        return "Login";
    }
}
