package hospital.controller;

import hospital.dto.ConsultationDto;
import hospital.dto.PatientDto;
import hospital.dto.UserDto;
import hospital.entity.Role;
import hospital.repository.RoleRepository;
import hospital.service.consultation.ConsultationService;
import hospital.service.patient.PatientService;
import hospital.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;

import static hospital.constants.ApplicationConstants.Roles.*;

@Controller
public class ExperimentController {
    private ConsultationService consultationService;
    private PatientService patientService;
    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public ExperimentController(ConsultationService consultationService, PatientService patientService, UserService userService, RoleRepository roleRepository) {
        this.consultationService = consultationService;
        this.patientService = patientService;
        this.userService = userService;
        this.roleRepository = roleRepository;

        // initialize
        addRoles();
        addUsers();
        addPatients();
        addConsultations();


        // operations on consultation
        deleteConsultation();
        updateConsultation();

        // operations on patients
        updatePatient();
    }


    public void addRoles(){
        Role adminRole = new Role(ADMINISTRATOR);
        Role doctRole = new Role(DOCTOR);
        Role secretRole = new Role(SECRETARY);
        roleRepository.save(adminRole);
        roleRepository.save(doctRole);
        roleRepository.save(secretRole);
    }

    private void addUsers() {
        UserDto doctor1 = new UserDto();
        doctor1.setUsername("doctor1@yahoo.com");
        doctor1.setPassword("Doctor1#");
        doctor1.setRole(DOCTOR);
        userService.register(doctor1);

        UserDto doctor2 = new UserDto();
        doctor2.setUsername("doctor2@yahoo.com");
        doctor2.setPassword("Doctor2#");
        doctor2.setRole(DOCTOR);
        userService.register(doctor2);

        UserDto admin = new UserDto();
        admin.setUsername("administrator@yahoo.com");
        admin.setPassword("Administrator1#");
        admin.setRole(ADMINISTRATOR);
        userService.register(admin);


        UserDto secretary = new UserDto();
        secretary.setUsername("secretary@yahoo.com");
        secretary.setPassword("Secretary1#");
        secretary.setRole(SECRETARY);
        userService.register(secretary);
    }

    private void addPatients() {
        PatientDto patientDto1 = new PatientDto("Porcusor Guineea", "0000000000000", new Date());
        patientService.create(patientDto1);

        PatientDto patientDto2 = new PatientDto("Papagal Albastru", "1111111111111", new Date());
        patientService.create(patientDto2);
    }

    private void addConsultations() {
        ConsultationDto consultationDto1 = new ConsultationDto(new Long(1), new Long(1), null, null, new Date());
        consultationService.create(consultationDto1);


        ConsultationDto consultationDto2 = new ConsultationDto(new Long(2), new Long(2), null, null, new Date());
        consultationService.create(consultationDto2);

    }

    private void deleteConsultation() {
        consultationService.delete(new Long(1));
    }


    private void updateConsultation() {
        ConsultationDto consultationDto = new ConsultationDto(new Long(2), new Long(1), new Long(2), null, null, new Date());
        consultationService.update(consultationDto);
    }

    private void updatePatient() {
        PatientDto patientDto = new PatientDto(new Long(1), "Porcusor de Guineea", "0000000000001", new Date());
        patientService.update(patientDto);
    }
}
