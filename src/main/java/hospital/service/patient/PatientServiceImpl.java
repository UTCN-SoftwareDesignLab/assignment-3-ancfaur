package hospital.service.patient;

import hospital.converter.patient.PatientConverter;
import hospital.dto.PatientDto;
import hospital.entity.Patient;
import hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepository;
    private PatientConverter patientConverter;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, PatientConverter patientConverter){
        this.patientRepository = patientRepository;
        this.patientConverter = patientConverter;
    }

    @Override
    public PatientDto create(PatientDto patientDto) {
        Patient patient = patientConverter.fromDto(patientDto);
        Patient back = patientRepository.save(patient);
        patientDto.setId(back.getId());
        return patientDto;
    }

    @Override
    public void update(PatientDto patientDto) {
        Patient patient = patientConverter.fromDto(patientDto);
        patientRepository.save(patient);
    }

    @Override
    public List<PatientDto> findAll() {
        List<Patient> patients = patientRepository.findAll();
        return patientConverter.toDto(patients);
    }

    @Override
    public PatientDto findById(Long id){
        return patientConverter.toDto(patientRepository.findById(id).orElse(null));
    }

    @Override
    public void removeAll() {
        patientRepository.deleteAll();
    }
}
