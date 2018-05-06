package hospital.controller;

import hospital.dto.ConsultationDto;
import hospital.service.consultation.ConsultationService;
import hospital.service.patient.PatientService;
import hospital.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ConsultationRecordController {
    private ConsultationService consultationService;
    private PatientService patientService;
    private UserService userService;

    @Autowired
    public ConsultationRecordController(ConsultationService consultationService, PatientService patientService, UserService userService) {
        this.consultationService = consultationService;
        this.patientService = patientService;
        this.userService = userService;
    }

    @GetMapping(value="/doctor/fillConsTable")
    public String consultationsForDoctor(Model model, Principal principal) {
        Long id = userService.findIdForUser(principal.getName());
        List<ConsultationDto> consultationDtos = consultationService.findAllConsultationForDoctor(id);
        model.addAttribute("consultationDtos", consultationDtos);
        return "fillConsTable";
    }

    @GetMapping(value="/consultation/{consultationId}")
    public String updateConsultation(Model model, @PathVariable(value = "consultationId") Long id) {
        ConsultationDto consultationDto =consultationService.findById(id);
        model.addAttribute("consultationDto", consultationDto);
        return "consultation";
    }

    @PostMapping(value="/consultation/{consultationId}", params = "updateBtn")
    public String updateConsultation(@ModelAttribute ConsultationDto consultationDto){
        consultationService.fillConsultationDetails(consultationDto);
        return "redirect:/doctor/fillConsTable";
    }
}
