package com.spring.mvc.cotroller;

import com.spring.mvc.dto.AgentDTO;
import com.spring.mvc.dto.StatusDTO;
import com.spring.mvc.entity.Paging;
import com.spring.mvc.service.AgentService;
import com.spring.mvc.service.StatusService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/agents")
public class HomeController {
    private AgentService agentService;
    private StatusService statusService;
    private Paging paging;

    public HomeController(AgentService agentService, StatusService statusService) {
        this.agentService = agentService;
        this.statusService = statusService;
        paging = new Paging(3, 1);
    }

    @GetMapping("/list")
    public String list(Model model) {
        return "redirect:/agents/list/page?pageSize=3&currentPage=1"; //mac dinh page size la 3 va page dau tien la 1
    }

//    @GetMapping("/list/page")
//    public String getAllAgentsPagination(@RequestParam("pageSize") Optional<String> pageSize,
//                                         @RequestParam("currentPage") Optional<String> currentPage,
//                                         @RequestParam("option") Optional<String> option,
//                                         @RequestParam("status") Optional<String> status,
//                                         @RequestParam("agentName") Optional<String> agentName,
//                                         @RequestParam("account") Optional<String> account,
//                                         Model model, HttpSession session) {
//        Object admin = session.getAttribute("admin");
//        if (admin == null) {
//            return "redirect:/";
//        }
//        model.addAttribute("admin", admin);
//        if (pageSize.isPresent() && Integer.parseInt(pageSize.get()) > 0) {
//            paging.setPageSize(Integer.parseInt(pageSize.get()));
//        }
//        paging.setTotalRows(agentService.getTotalRows());
//        if (option.isPresent() && option.get().equals("previous")) {
//            if (Integer.parseInt(currentPage.get()) > 1) {
//               paging.setCurrentPage(Integer.parseInt(currentPage.get()) - 1);
//            }
//        }
//        if (option.isPresent() && option.get().equals("next")) {
//            if (Integer.parseInt(currentPage.get()) < paging.getTotalPages()) {
//                paging.setCurrentPage(Integer.parseInt(currentPage.get()) + 1);
//            }
//        }
//        List<AgentDTO> list = agentService.getAllAgentPagination(paging);
//        model.addAttribute("list", list);
//        model.addAttribute("paging", paging);
//        List<StatusDTO> listStatus = statusService.getAllStatus();
//        model.addAttribute("listStatus", listStatus);
//        return "AgentList";
//    }

    @GetMapping("/list/page")
    public String getAllAgentsPagination(@RequestParam("pageSize") Optional<String> pageSize,
                                         @RequestParam("currentPage") Optional<String> currentPage,
                                         @RequestParam("option") Optional<String> option,
                                         @RequestParam("status") Optional<String> status,
                                         @RequestParam("agentName") Optional<String> agentName,
                                         @RequestParam("account") Optional<String> account,
                                         Model model, HttpSession session) {
        Object admin = session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/";
        }
        model.addAttribute("admin", admin);
        if (pageSize.isPresent() && Integer.parseInt(pageSize.get()) > 0) {
            paging.setPageSize(Integer.parseInt(pageSize.get()));
        }
        paging.setTotalRows(agentService.getTotalRowsForSearch(
                account.isPresent() ? account.get() : null,
                agentName.isPresent() ? agentName.get() : null,
                status.isPresent() ? (status.get().isEmpty() ? null : (Integer.parseInt(status.get()) == 0 ? null : status.get())) : null
        ));
        System.out.println("Paging1: " + paging.toString());
        if (option.isPresent() && option.get().equals("previous")) {
            if (Integer.parseInt(currentPage.get()) > 1) {
                paging.setCurrentPage(Integer.parseInt(currentPage.get()) - 1);
            }
        }
        if (option.isPresent() && option.get().equals("next")) {
            if (Integer.parseInt(currentPage.get()) < paging.getTotalPages()) {
                paging.setCurrentPage(Integer.parseInt(currentPage.get()) + 1);
            }
        }
        List<AgentDTO> list = agentService.findByAccountNameStatus(
                account.isPresent() ? account.get() : null,
                agentName.isPresent() ? agentName.get() : null,
                status.isPresent() ? (status.get().isEmpty() ? null : (Integer.parseInt(status.get()) == 0 ? null : status.get())) : null,
                paging
        );
        System.out.println("Paging2: " + paging.toString());
        model.addAttribute("list", list);
        model.addAttribute("paging", paging);
        List<StatusDTO> listStatus = statusService.getAllStatus();
        model.addAttribute("listStatus", listStatus);
        model.addAttribute("status", status.isPresent() ? (status.get().isEmpty() ? 0 : Integer.parseInt(status.get())) : 0);
        model.addAttribute("agentName", agentName.isPresent() ? agentName.get() : null);
        model.addAttribute("account", account.isPresent() ? account.get() : null);
        return "AgentList";
    }

    @GetMapping("/detail")
    public String getAgentById(@RequestParam("agentId") int agentId, Model model, HttpSession session) {
        Object admin = session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/";
        }
        model.addAttribute("admin", admin);
        AgentDTO agentDTO = agentService.getAgent(agentId);
        model.addAttribute("agent", agentDTO);
        List<StatusDTO> listStatus = statusService.getAllStatus();
        model.addAttribute("listStatus", listStatus);
        model.addAttribute("message", null);
        return "AgentDetail";
    }

    @GetMapping("/delete")
    public String deleteAgentById(@RequestParam("agentId") int agentId, Model model, HttpSession session) {
        Object admin = session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/";
        }
        agentService.deleteAgent(agentId);
        return "redirect:/agents/list";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model, HttpSession session) {
        Object admin = session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/";
        }
        model.addAttribute("admin", admin);
        List<StatusDTO> listStatus = statusService.getAllStatus();
        model.addAttribute("listStatus", listStatus);
        AgentDTO agentDTO = new AgentDTO();
        model.addAttribute("agent", agentDTO);
        return "CreateAgent";
    }

    @PostMapping("/create")
    public String createAgent(@Valid @ModelAttribute("agent") AgentDTO agentDTO, BindingResult results, Model model, HttpSession session) {
        //@Valid yêu cầu kiểm tra các ràng buộc (validation) trên đối tượng agent (dựa trên các annotation validation trong AgentDTO).
        //Tham số BindingResult results: Được dùng để kiểm tra xem có lỗi nào xảy ra khi thực hiện @Valid trên agent hay không. Nếu có lỗi, các lỗi này sẽ được chứa trong results.
        Object admin = session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/";
        }
        model.addAttribute("admin", admin);
        List<StatusDTO> listStatus = statusService.getAllStatus();
        model.addAttribute("listStatus", listStatus);
        try {
            //neu phat hien loi thi quay lai trang update
            if (results.hasErrors()) {
                model.addAttribute("message", "Có lỗi nhập liệu");
                model.addAttribute("agent", agentDTO);
                Status status = statusService.getStatusById(agentDTO.getStatus().getStatusId());
                agentDTO.setStatus(status);
                return "CreateAgent";
            }
            //cap nhat thong tin
            Agent newAgent = new Agent();
            newAgent.setAgentId(agentDTO.getAgentId());
            newAgent.setAgentName(agentDTO.getAgentName());
            newAgent.setEmail(agentDTO.getEmail());
            newAgent.setAddress(agentDTO.getAddress());
            Status status = statusService.getStatusById(agentDTO.getStatus().getStatusId());
            newAgent.setStatus(status);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            newAgent.setRegistrationDate(formatter.parse(agentDTO.getRegistrationDate()));
            newAgent.setAccountBalance(Integer.parseInt(agentDTO.getAccountBalance()));
            agentService.createAgent(newAgent);
            //quay lai trang update
            model.addAttribute("message", "Tạo mới đại lý thành công");
        } catch (Exception exception) {
            exception.printStackTrace();
            model.addAttribute("message", "Đã xảy ra vài ngoại lệ trong quá trình xử lý");
            return "CreateAgent";
        }
        AgentDTO clearAgent = new AgentDTO();
        model.addAttribute("agent", clearAgent);
        return "CreateAgent";
    }

    @PostMapping("/update")
    public String updateAgent(@Valid @ModelAttribute("agent") AgentDTO agentDTO, BindingResult results, Model model, HttpSession session) {
        //@Valid yêu cầu kiểm tra các ràng buộc (validation) trên đối tượng agent (dựa trên các annotation validation trong AgentDTO).
        //Tham số BindingResult results: Được dùng để kiểm tra xem có lỗi nào xảy ra khi thực hiện @Valid trên agent hay không. Nếu có lỗi, các lỗi này sẽ được chứa trong results.
        Object admin = session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/";
        }
        model.addAttribute("admin", admin);
        List<StatusDTO> listStatus = statusService.getAllStatus();
        model.addAttribute("listStatus", listStatus);
        try {
            //neu phat hien loi thi quay lai trang update
            if (results.hasErrors()) {
                model.addAttribute("message", "Có lỗi nhập liệu");
                model.addAttribute("agent", agentDTO);
                Status status = statusService.getStatusById(agentDTO.getStatus().getStatusId());
                agentDTO.setStatus(status);
                return "AgentDetail";
            }
            //cap nhat thong tin
            Agent newAgent = new Agent();
            newAgent.setAgentId(agentDTO.getAgentId());
            newAgent.setAgentName(agentDTO.getAgentName());
            newAgent.setEmail(agentDTO.getEmail());
            newAgent.setAddress(agentDTO.getAddress());
            Status status = statusService.getStatusById(agentDTO.getStatus().getStatusId());
            newAgent.setStatus(status);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            newAgent.setRegistrationDate(formatter.parse(agentDTO.getRegistrationDate()));
            newAgent.setAccountBalance(Integer.parseInt(agentDTO.getAccountBalance()));
            agentService.updateAgent(agentDTO.getAgentId(), newAgent);
            //quay lai trang update
            model.addAttribute("message", "Cập nhật đại lý thành công");
        } catch (Exception exception) {
            exception.printStackTrace();
            model.addAttribute("message", "Đã xảy ra vài ngoại lệ trong quá trình xử lý");
            return "AgentDetail";
        }
        return "AgentDetail";
    }
}
