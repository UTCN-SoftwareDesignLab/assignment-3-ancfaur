package hospital.converter;

import hospital.dto.PatientDto;
import hospital.entity.Patient;

import java.util.List;

public interface PatientConverter {
    Patient fromDto(PatientDto patientDto);
    PatientDto toDto (Patient patient);
    List<Patient> fromDto(List<PatientDto> patientDtos);
    List<PatientDto> toDto(List<Patient> patients);
}
