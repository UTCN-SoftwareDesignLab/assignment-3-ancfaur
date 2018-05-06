package hospital.service.patient;

import hospital.dto.PatientDto;

import java.util.List;

public interface PatientService {
    PatientDto create(PatientDto patientDto);
    void update(PatientDto patientDto);
    List<PatientDto> findAll();
    PatientDto findById(Long id);

    // for testing
    void removeAll();
}
