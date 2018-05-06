package hospital.controller;

import hospital.dto.AppoitmentDto;
import hospital.dto.ConsultationDto;
import hospital.dto.PatientDto;
import hospital.dto.UserDto;
import hospital.service.consultation.ConsultationOverlapException;
import hospital.service.consultation.ConsultationService;
import hospital.service.patient.PatientService;
import hospital.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static hospital.constants.ApplicationConstants.Roles.DOCTOR;

@Controller
@RequestMapping(value="/secretary/secretaryMenu")
public class SecretaryController {
    private ConsultationService consultationService;
    private PatientService patientService;
    private UserService userService;

    private SimpMessagingTemplate messagingTemplate;


    @Autowired
    public SecretaryController(ConsultationService consultationService, PatientService patientService, UserService userService, SimpMessagingTemplate simpMessagingTemplate) {
        this.consultationService = consultationService;
        this.patientService = patientService;
        this.userService = userService;
        this.messagingTemplate = simpMessagingTemplate;
    }

    @GetMapping()
    public String userForm(Model model) {
        model.addAttribute(new PatientDto());
        model.addAttribute(new ConsultationDto());
        return "secretaryMenu";
    }

    private void reintroduceAttributes(Model model, PatientDto patientDto){
        model.addAttribute(patientDto);
        model.addAttribute(new ConsultationDto());
    }

    @PostMapping(params = "createBtn")
    public String createPatient(@ModelAttribute @Valid PatientDto patientDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            reintroduceAttributes(model, patientDto);
            return "secretaryMenu";
        }
        patientService.create(patientDto);
        return "redirect:/secretary/secretaryMenu";
    }

    @PostMapping(params = "updateBtn")
    public String updatePatient(@ModelAttribute @Valid PatientDto patientDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            reintroduceAttributes(model, patientDto);
            return "secretaryMenu";
        }
        patientService.update(patientDto);
        return "redirect:/secretary/secretaryMenu";
    }

    @PostMapping(value = "/showAllPatients", params = "showAllPatBtn")
    public String showAllPatients(Model model) {
        List<PatientDto> patientDtos = patientService.findAll();
        model.addAttribute("patientDtos", patientDtos);
        return "patientSecretTable";
    }

    @PostMapping(value = "/showAllDoctors", params = "showAllDocBtn")
    public String showAllDoctors(Model model) {
        List<UserDto> userDtos = userService.findAllWithRole(DOCTOR);
        model.addAttribute("userDtos", userDtos);
        return "userTable";
    }

    @PostMapping(params = "searchConsultBtn")
    public String delete(@RequestParam("doctorId") String doctorId, Model model) {
        List<ConsultationDto> consultationDtos = consultationService.findFutureScheduleForDoctor(Long.parseLong(doctorId));
        model.addAttribute("consultationDtos", consultationDtos);
        return "consultSecretTable";
    }

    @PostMapping(params = "createConsultBtn")
    @MessageMapping("/send")
    public String createConsultation(@ModelAttribute @Valid ConsultationDto consultationDto, BindingResult bindingResult, Principal principal) throws ConsultationOverlapException {
        if (bindingResult.hasErrors()) {
            return "secretaryMenu";
        }
        consultationDto.setDescription("not yet");
        consultationDto.setDiagnostic("not yet");
        consultationService.create(consultationDto);

        //socket bussiness
        AppoitmentDto appoitmentDto = new AppoitmentDto();
        PatientDto patientDto = patientService.findById(consultationDto.getPatient_id());
        UserDto doctor = userService.findById(consultationDto.getDoctor_id());

        appoitmentDto.setContent("Patient :" + patientDto.getName() + " Date:" + consultationDto.getDate() +
                " scheduled by " + principal.getName());

        messagingTemplate.convertAndSendToUser(doctor.getUsername(), "/queue/reply", appoitmentDto);
        return "redirect:/secretary/secretaryMenu";
    }

    @PostMapping(params = "updateConsultBtn")
    public String updateConsultation(@ModelAttribute @Valid ConsultationDto consultationDto, BindingResult bindingResult) throws ConsultationOverlapException {
        if (bindingResult.hasErrors()) {
            return "secretaryMenu";
        }
        consultationDto.setDescription("not yet");
        consultationDto.setDiagnostic("not yet");
        consultationService.update(consultationDto);
        return "redirect:/secretary/secretaryMenu";
    }

    @PostMapping(value = "/error")
    @ExceptionHandler({ConsultationOverlapException.class})
    public String consultationOverlap(Model model, ConsultationOverlapException consultationOverlapException) {
        model.addAttribute("errorCause", new String(consultationOverlapException.getMessage()));
        return "error";
    }

    @PostMapping(value = "/logout", params = "logoutBtn")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }


}
