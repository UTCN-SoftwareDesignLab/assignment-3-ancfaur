package hospital.service.consultation;

import hospital.dto.ConsultationDto;

public interface ConsultationService {
    ConsultationDto create(ConsultationDto consultationDto);
    void delete(Long id);
    void update(ConsultationDto consultationDto);
}
