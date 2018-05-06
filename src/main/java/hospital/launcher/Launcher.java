package hospital.launcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages ={"hospital.entity"})
@SpringBootApplication(scanBasePackages = {"hospital.launcher", "hospital.entity", "hospital.constants", "hospital.repository", "hospital.service", "hospital.converter", "hospital.controller", "hospital.config"})
@EnableJpaRepositories(basePackages = {"hospital.repository"})
public class Launcher{
    public static void main(String[] args) {
        SpringApplication.run(hospital.launcher.Launcher.class, args);
    }
}