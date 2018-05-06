import hospital.dto.PatientDto;
import hospital.service.patient.PatientService;
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

@RunWith(SpringRunner.class)
@Configuration
@PropertySource("classpath:testing.properties")
@EnableJpaRepositories(basePackages = "hospital.repository")
@ComponentScan({"hospital.launcher", "hospital.entity", "hospital.dto", "hospital.repository", "hospital.service", "hospital.controller", "hospital.converter", "hospital.config"})
@EntityScan(basePackages ={"hospital.entity"})
public class PatientServiceIntegTest {
    @Autowired
    private PatientService patientService;

    @Before
    public void setup(){
        patientService.removeAll();
    }

    @Test
    public void findAllWhenNone(){
        Assert.assertEquals(0, patientService.findAll().size());
    }

    @Test
    public void findAll(){
        PatientDto patientDto1 = new PatientDto("Porcusor Guineea", "0000000000000", "1996-02-06");
        patientService.create(patientDto1);
        Assert.assertEquals(1, patientService.findAll().size());
    }

    @Test
    public void register(){
        PatientDto patientDto1 = new PatientDto("Porcusor Guineea", "0000000000000", "1996-02-06");
        patientService.create(patientDto1);
        PatientDto back =patientService.create(patientDto1);
        PatientDto find= patientService.findById(back.getId());
        Assert.assertEquals("Porcusor Guineea", find.getName());
    }

    @Test
    public void update(){
        PatientDto patientDto1 = new PatientDto("Porcusor Guineea", "0000000000000", "1996-02-06");
        patientService.create(patientDto1);
        PatientDto back =patientService.create(patientDto1);
        back.setName("Porc Mare");
        patientService.update(back);
        PatientDto find= patientService.findById(back.getId());
        Assert.assertEquals("Porc Mare", find.getName());
    }
}
