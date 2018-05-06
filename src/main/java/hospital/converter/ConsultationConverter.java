package hospital.converter;

import hospital.dto.ConsultationDto;
import hospital.entity.Consultation;
import hospital.entity.Patient;
import hospital.entity.User;

import java.util.List;

public interface ConsultationConverter {
    ConsultationDto toDto(Consultation consultation);
    List<ConsultationDto> toDto(List<Consultation> consultations);
    Consultation fromDto(ConsultationDto consultationDto, Patient patient, User doctor);

}
