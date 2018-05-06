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

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public ConsultationDto create(ConsultationDto consultationDto) throws ConsultationOverlapException {
        Consultation consultation = getConsultationWithDependencies(consultationDto);
        checkAvaibility(consultation.getDoctor(), consultation.getDate());
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
    public void update(ConsultationDto consultationDto) throws ConsultationOverlapException {
        Consultation consultation = getConsultationWithDependencies(consultationDto);
        checkAvaibility(consultation.getDoctor(), consultation.getDate());
        consultationRepository.save(consultation);
    }

    // less then 15 minutes between consultations
    private void checkAvaibility(User doctor, Date date) throws ConsultationOverlapException {
        List<Consultation> consultations = consultationRepository.findByDoctor(doctor);
        for(Consultation consultation:consultations){
            if (Math.abs(consultation.getDate().getTime() - date.getTime())< 900000)
                throw new ConsultationOverlapException("Less then 15 minutes between previous consultation: "+ consultation.toString());
        }
    }

    @Override
    public List<ConsultationDto> findAllConsultationForDoctor(Long doctorId) {
       User doctor = userRepository.findById(doctorId).orElse(null);
       List<Consultation> consultations =consultationRepository.findByDoctor(doctor);
        return consultationConverter.toDto(consultations);
    }

    public List<ConsultationDto> findFutureScheduleForDoctor(Long doctorId){
        User doctor = userRepository.findById(doctorId).orElse(null);
        List<ConsultationDto> consultationDtos =consultationRepository.findByDoctor(doctor).stream()
                .filter(colsultation -> colsultation.getDate().after(new Date()))
                .sorted(Comparator.comparing(Consultation::getDate))
                .map(consultation-> consultationConverter.toDto(consultation))
                .collect(Collectors.toList());
        return consultationDtos;
    }

    @Override
    public List<ConsultationDto> findAllForPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        return consultationConverter.toDto(consultationRepository.findByPatient(patient));
    }

    @Override
    public ConsultationDto findById(Long id) {
       return consultationConverter.toDto(consultationRepository.findById(id).orElse(null));
    }

    @Override
    public void fillConsultationDetails(ConsultationDto consultationDto) {
        Consultation consultation = consultationRepository.findById(consultationDto.getId()).orElse(null);
        consultation.setDescription(consultationDto.getDescription());
        consultation.setDiagnostic(consultationDto.getDiagnostic());
        consultationRepository.save(consultation);
    }

    private Consultation getConsultationWithDependencies(ConsultationDto consultationDto){
        User doctor = userRepository.findById(consultationDto.getDoctor_id()).orElse(null);
        Patient patient = patientRepository.findById(consultationDto.getPatient_id()).orElse(null);
        Consultation consultation = consultationConverter.fromDto(consultationDto, patient, doctor);
        return consultation;
    }
}
