package hospital.converter;
import hospital.dto.PatientDto;
import hospital.entity.Patient;
import hospital.entity.builder.PatientBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PatientConverterImpl implements PatientConverter {
    @Override
    public Patient fromDto(PatientDto patientDto) {
        Patient patient = new PatientBuilder()
                .setId(patientDto.getId())
                .setName(patientDto.getName())
                .setCnp(patientDto.getCnp())
                .setBirthdate(patientDto.getBirthdate())
                .build();
        return patient;
    }

    @Override
    public PatientDto toDto(Patient patient) {
        if (patient==null) return null;
        PatientDto patientDto = new PatientDto(patient.getId(), patient.getName(), patient.getCnp(), patient.getBirthdate());
        return patientDto;
    }

    @Override
    public List<Patient> fromDto(List<PatientDto> patientDtos) {
        List<Patient> patients = new ArrayList<>();
        for (PatientDto patientDto: patientDtos){
            patients.add(fromDto(patientDto));
        }
        return patients;
    }

    @Override
    public List<PatientDto> toDto(List<Patient> patients) {
        List<PatientDto> patientDtos = new ArrayList<>();
        for (Patient patient:patients){
            patientDtos.add(toDto(patient));
        }
        return patientDtos;
    }
}
