package hospital.controller;

import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value ="/doctor/doctorMenu")
public class DoctorMenuController {
    @GetMapping()
    @Order(value = 1)
    public String index() {
        return "doctorMenu";
    }

    @PostMapping(params = "patientRecordsBtn")
    public String patientRecords() {
        return "redirect:/doctor/patientDocTable";
    }

    @PostMapping(params = "manageConsultBtn")
    public String consultManage() {
        return "redirect:/doctor/fillConsTable";
    }


    @PostMapping(value = "/logout", params="logoutBtn")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}
