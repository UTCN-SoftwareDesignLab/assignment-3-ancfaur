package hospital.converter;

import hospital.dto.ConsultationDto;
import hospital.entity.Consultation;
import hospital.entity.Patient;
import hospital.entity.User;

public interface ConsultationConverter {
    ConsultationDto toDto(Consultation consultation);
    Consultation fromDto(ConsultationDto consultationDto, Patient patient, User doctor);
}
