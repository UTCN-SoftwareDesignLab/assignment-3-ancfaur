package hospital.service.consultation;

import hospital.dto.ConsultationDto;

import java.util.List;

public interface ConsultationService {
    ConsultationDto create(ConsultationDto consultationDto) throws ConsultationOverlapException;
    void delete(Long id);
    void update(ConsultationDto consultationDto) throws ConsultationOverlapException;
    List<ConsultationDto> findAllConsultationForDoctor(Long doctorId);
    List<ConsultationDto> findFutureScheduleForDoctor(Long doctorId);
    List<ConsultationDto> findAllForPatient(Long patientId);
    ConsultationDto findById(Long id);
    void fillConsultationDetails(ConsultationDto consultationDto);

    // for testing
    void removeAll();
}
