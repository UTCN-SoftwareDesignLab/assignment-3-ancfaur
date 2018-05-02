package hospital.service.patient;

import hospital.dto.PatientDto;

public interface PatientService {
    PatientDto create(PatientDto patientDto);
    void update(PatientDto patientDto);
}
