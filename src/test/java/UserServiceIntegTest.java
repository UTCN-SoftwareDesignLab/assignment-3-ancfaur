import hospital.dto.UserDto;
import hospital.entity.Role;
import hospital.repository.RoleRepository;
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

import static hospital.constants.ApplicationConstants.Roles.*;


@RunWith(SpringRunner.class)
@Configuration
@PropertySource("classpath:testing.properties")
@EnableJpaRepositories(basePackages = "hospital.repository")
@ComponentScan({"hospital.launcher", "hospital.entity", "hospital.dto", "hospital.repository", "hospital.service", "hospital.controller", "hospital.converter", "hospital.config"})
@EntityScan(basePackages ={"hospital.entity"})
public class UserServiceIntegTest {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;

    @Before
    public void setup(){
        roleRepository.deleteAll();
        userService.removeAll();

        Role adminRole = new Role(ADMINISTRATOR);
        Role secretRole = new Role(SECRETARY);
        Role docRole = new Role(DOCTOR);
        roleRepository.save(adminRole);
        roleRepository.save(secretRole);
        roleRepository.save(docRole);
    }

    @Test
    public void findAll(){
        int noBefore = userService.findAll().size();

        UserDto admin = new UserDto();
        admin.setUsername("administrator@yahoo.com");
        admin.setPassword("Password1#");
        admin.setRole(ADMINISTRATOR);
        userService.register(admin);

        UserDto secret = new UserDto();
        secret.setUsername("secret@yahoo.com");
        secret.setPassword("Password1#");
        secret.setRole(SECRETARY);
        userService.register(secret);

        UserDto doctor = new UserDto();
        doctor.setUsername("doctor@yahoo.com");
        doctor.setPassword("Password1#");
        doctor.setRole(DOCTOR);
        userService.register(doctor);

        Assert.assertEquals(noBefore +3, userService.findAll().size());
    }

    @Test
    public void register(){
        UserDto admin = new UserDto();
        admin.setUsername("administrator@yahoo.com");
        admin.setPassword("Password1#");
        admin.setRole(ADMINISTRATOR);

        UserDto back =userService.register(admin);
        UserDto registered= userService.findById(back.getId());
        Assert.assertEquals("administrator@yahoo.com", registered.getUsername());
        Assert.assertTrue(userService.checkPasswords("Password1#", registered.getPassword()));
    }

    @Test
    public void updateUser(){
        UserDto admin = new UserDto();
        admin.setUsername("administrator@yahoo.com");
        admin.setPassword("Password1#");
        admin.setRole(ADMINISTRATOR);

        UserDto back= userService.register(admin);
        back.setUsername("changedUsername@yahoo.com");
        back.setPassword("ChangedPass#1");
        userService.update(back);
        UserDto updated =userService.findById(back.getId());
        Assert.assertEquals("changedUsername@yahoo.com", updated.getUsername());
        Assert.assertTrue(userService.checkPasswords("ChangedPass#1", updated.getPassword()));
    }

    @Test
    public void deleteUser(){
        UserDto admin = new UserDto();
        admin.setUsername("administrator@yahoo.com");
        admin.setPassword("Password1#");
        admin.setRole(ADMINISTRATOR);

        UserDto back= userService.register(admin);
        userService.delete(back.getId());
        Assert.assertNull(userService.findById(back.getId()));
    }
}