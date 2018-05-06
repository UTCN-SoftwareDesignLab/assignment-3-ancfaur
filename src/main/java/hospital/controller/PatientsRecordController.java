package hospital.controller;

import hospital.dto.ConsultationDto;
import hospital.service.consultation.ConsultationService;
import hospital.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PatientsRecordController {
    private ConsultationService consultationService;
    private PatientService patientService;

    @Autowired
    public PatientsRecordController(ConsultationService consultationService, PatientService patientService) {
        this.consultationService = consultationService;
        this.patientService = patientService;
    }

    @GetMapping(value="/doctor/patientDocTable")
    public String allPatientsForm(Model model) {
        model.addAttribute("patientDtos", patientService.findAll());
        return "patientDocTable";
    }

    @GetMapping(value="/consultDocTable/{patientId}")
    public String consultationsForPatient(Model model, @PathVariable(value = "patientId") Long id) {
        List<ConsultationDto> consultationDtos = consultationService.findAllForPatient(id);
        model.addAttribute("consultationDtos", consultationDtos);
        return "consultDocTable";
    }

}
