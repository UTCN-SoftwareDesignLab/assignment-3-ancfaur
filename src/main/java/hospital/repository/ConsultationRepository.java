package hospital.repository;

import hospital.entity.Consultation;
import hospital.entity.Patient;
import hospital.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByDoctor(User doctor);
    List<Consultation> findByPatient(Patient patient);
}
