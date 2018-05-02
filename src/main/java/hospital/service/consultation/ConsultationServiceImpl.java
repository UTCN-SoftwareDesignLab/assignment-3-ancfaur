package hospital.service.consultation;

import hospital.converter.ConsultationConverter;
import hospital.dto.ConsultationDto;
import hospital.entity.Consultation;
import hospital.entity.Patient;
import hospital.entity.User;
import hospital.repository.ConsultationRepository;
import hospital.repository.PatientRepository;
import hospital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultationServiceImpl implements ConsultationService {
    private ConsultationRepository consultationRepository;
    private UserRepository userRepository;
    private PatientRepository patientRepository;
    private ConsultationConverter consultationConverter;

    @Autowired
    public ConsultationServiceImpl(ConsultationRepository consultationRepository, ConsultationConverter consultationConverter, UserRepository userRepository, PatientRepository patientRepository ) {
        this.consultationRepository = consultationRepository;
        this.consultationConverter = consultationConverter;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ConsultationDto create(ConsultationDto consultationDto) {
        Consultation consultation = getConsultationWithDependencies(consultationDto);
        Consultation back =consultationRepository.save(consultation);
        consultationDto.setId(back.getId());
        return  consultationDto;
    }

    @Override
    public void delete(Long id) {
        Consultation consultation = consultationRepository.findById(id).orElse(null);
        consultationRepository.delete(consultation);
    }

    @Override
    public void update(ConsultationDto consultationDto) {
        Consultation consultation = getConsultationWithDependencies(consultationDto);
        consultationRepository.save(consultation);
    }

    private Consultation getConsultationWithDependencies(ConsultationDto consultationDto){
        User doctor = userRepository.findById(consultationDto.getDoctor_id()).orElse(null);
        Patient patient = patientRepository.findById(consultationDto.getPatient_id()).orElse(null);
        Consultation consultation = consultationConverter.fromDto(consultationDto, patient, doctor);
        return consultation;
    }
}
