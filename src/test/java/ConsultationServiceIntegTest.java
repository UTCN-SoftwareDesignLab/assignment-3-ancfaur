import hospital.dto.ConsultationDto;
import hospital.dto.PatientDto;
import hospital.dto.UserDto;
import hospital.service.consultation.ConsultationOverlapException;
import hospital.service.consultation.ConsultationService;
import hospital.service.patient.PatientService;
import hospital.service.user.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import static hospital.constants.ApplicationConstants.Roles.DOCTOR;

@RunWith(SpringRunner.class)
@Configuration
@PropertySource("classpath:testing.properties")
@EnableJpaRepositories(basePackages = "hospital.repository")
@ComponentScan({"hospital.launcher", "hospital.entity", "hospital.dto", "hospital.repository", "hospital.service", "hospital.controller", "hospital.converter", "hospital.config"})
@EntityScan(basePackages ={"hospital.entity"})
public class ConsultationServiceIntegTest {
    @Autowired
    private ConsultationService consultationService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private UserService userService;

    private Long doctorId;
    private Long patientId;


    @Before
    public void setup(){
        consultationService.removeAll();
        patientService.removeAll();
        userService.removeAll();

        UserDto doctor = new UserDto();
        doctor.setUsername("doctor@yahoo.com");
        doctor.setPassword("Password1#");
        doctor.setRole(DOCTOR);
        UserDto backDoc =userService.register(doctor);
        doctorId = backDoc.getId();

        PatientDto patientDto = new PatientDto("Porcusor Guineea", "0000000000000", "1996-02-06");
        PatientDto backPat =patientService.create(patientDto);
        patientId = backPat.getId();

    }

    @Test
    public void createWhenAllowed() {
        ConsultationDto consultationDto = new ConsultationDto(doctorId, patientId, "Diabet", "Mananca multe bomboane", "2017-05-04T12:12");
        ConsultationDto back;
        try {
            back =consultationService.create(consultationDto);
            Assert.assertNotNull(consultationService.findById(back.getId()));
        } catch (ConsultationOverlapException e) {
            assert(false);
        }
    }

    @Test
    public void createWhenNotAllowed() {
        ConsultationDto consultationDto = new ConsultationDto(doctorId, patientId, "Diabet", "Mananca multe bomboane", "2017-05-04T12:12");
        ConsultationDto back1= null, back2=null;

        try {
            back1 =consultationService.create(consultationDto);
            back2 =consultationService.create(consultationDto);
            assert(false);
        } catch (ConsultationOverlapException e) {
            Assert.assertNotNull(consultationService.findById(back1.getId()));
            Assert.assertNull(back2);
        }
    }

    @Test
    public void delete() throws ConsultationOverlapException {
        UserDto doctor = new UserDto();
        doctor.setUsername("doctore@yahoo.com");
        doctor.setPassword("Passworde1#");
        doctor.setRole(DOCTOR);
        UserDto backDoc =userService.register(doctor);
        PatientDto patientDto1 = new PatientDto("Porcusor Guineea", "0000210000000", "1996-02-06");
        PatientDto backPat =patientService.create(patientDto1);

        ConsultationDto consultationDto1 = new ConsultationDto(backDoc.getId(), backPat.getId(), "Diabet", "Mananca multe bomboane", "2017-05-04T12:12");
        ConsultationDto backCons = consultationService.create(consultationDto1);
        consultationService.delete(backCons.getId());
        Assert.assertNull(consultationService.findById(backCons.getId()));
    }

    @Test
    public void update() throws ConsultationOverlapException {
        ConsultationDto consultationDto = new ConsultationDto(doctorId, patientId, "Diabet", "Mananca multe bomboane", "2017-05-04T12:12");
        ConsultationDto back =consultationService.create(consultationDto);
        back.setDate("2018-05-04T12:12");
        consultationService.update(back);
        Assert.assertEquals("2018-05-04 12:12:00.0", consultationService.findById(back.getId()).getDate());
    }

    @Test
    public void findConsultForDoctor() throws ConsultationOverlapException {
        ConsultationDto consultationDto = new ConsultationDto(doctorId, patientId, "Diabet", "Mananca multe bomboane", "2017-05-04T12:12");
        ConsultationDto back =consultationService.create(consultationDto);
        Assert.assertEquals(1, consultationService.findAllConsultationForDoctor(doctorId).size());
    }

    @Test
    public void findConsultForPatient() throws ConsultationOverlapException {
        ConsultationDto consultationDto = new ConsultationDto(doctorId, patientId, "Diabet", "Mananca multe bomboane", "2017-05-04T12:12");
        ConsultationDto back =consultationService.create(consultationDto);
        Assert.assertEquals(1, consultationService.findAllForPatient(patientId).size());
    }




}
