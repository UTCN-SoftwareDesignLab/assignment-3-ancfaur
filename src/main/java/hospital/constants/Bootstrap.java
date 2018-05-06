package hospital.constants;

import hospital.dto.ConsultationDto;
import hospital.dto.PatientDto;
import hospital.dto.UserDto;
import hospital.entity.Role;
import hospital.repository.RoleRepository;
import hospital.service.consultation.ConsultationOverlapException;
import hospital.service.consultation.ConsultationService;
import hospital.service.patient.PatientService;
import hospital.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static hospital.constants.ApplicationConstants.Roles.*;

@Component
public class Bootstrap {
        private RoleRepository roleRepository;
        private UserService userService;
        private PatientService patientService;
        private ConsultationService consultationService;


        @Autowired
        public Bootstrap( RoleRepository roleRepository, PatientService patientService, UserService userService, ConsultationService consultationService){
            this.roleRepository = roleRepository;
            this.patientService = patientService;
            this.userService = userService;
            this.consultationService = consultationService;
        }

        @PostConstruct
        public void populateDb() throws ConsultationOverlapException {
            addRoles();
            addUsers();
            addPatients();
            addConsultations();
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
        doctor2.setPassword("Doctor1#");
        doctor2.setRole(DOCTOR);
        userService.register(doctor2);

        UserDto admin = new UserDto();
        admin.setUsername("administrator@yahoo.com");
        admin.setPassword("Administrator1#");
        admin.setRole(ADMINISTRATOR);
        userService.register(admin);


        UserDto secretary1 = new UserDto();
        secretary1.setUsername("secretary1@yahoo.com");
        secretary1.setPassword("Secretary1#");
        secretary1.setRole(SECRETARY);
        userService.register(secretary1);

        UserDto secretary2 = new UserDto();
        secretary2.setUsername("secretary2@yahoo.com");
        secretary2.setPassword("Secretary1#");
        secretary2.setRole(SECRETARY);
        userService.register(secretary2);
    }

    private void addPatients() {
        PatientDto patientDto1 = new PatientDto("Porcusor Guineea", "0000000000000", "1996-02-06");
        patientService.create(patientDto1);

        PatientDto patientDto2 = new PatientDto("Papagal Albastru", "11111111111111", "2015-02-03");
        patientService.create(patientDto2);
    }

    private void addConsultations() throws ConsultationOverlapException {
        ConsultationDto consultationDto1 = new ConsultationDto(new Long(1), new Long(1), "Diabet", "Mananca multe bomboane", "2017-05-04T12:12");
        consultationService.create(consultationDto1);

        ConsultationDto consultationDto2 = new ConsultationDto(new Long(2), new Long(2), "Luxatie", "I-am pus mana la loc", "2018-01-10T12:12");
        consultationService.create(consultationDto2);

        ConsultationDto consultationDto3 = new ConsultationDto(new Long(1), new Long(2), "Not yet", "Not yet", "2020-10-10T12:12");
        consultationService.create(consultationDto3);

        ConsultationDto consultationDto4 = new ConsultationDto(new Long(2), new Long(2), "Not yet", "Not yet", "2018-10-10T12:12");
        consultationService.create(consultationDto4);
    }

}
