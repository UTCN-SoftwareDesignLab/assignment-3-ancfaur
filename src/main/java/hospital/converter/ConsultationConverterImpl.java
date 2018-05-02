package hospital.converter;

import hospital.dto.ConsultationDto;
import hospital.entity.Consultation;
import hospital.entity.Patient;
import hospital.entity.User;
import hospital.entity.builder.ConsultationBuilder;
import org.springframework.stereotype.Component;

@Component
public class ConsultationConverterImpl implements ConsultationConverter {

    @Override
    public ConsultationDto toDto(Consultation consultation) {
        ConsultationDto consultationDto = new ConsultationDto(consultation.getId(), consultation.getDoctor().getId(), consultation.getPatient().getId(), consultation.getDiagnostic(), consultation.getDescription(), consultation.getDate());
        return consultationDto;
    }

    @Override
    public Consultation fromDto(ConsultationDto consultationDto, Patient patient, User doctor) {
       Consultation consultation = new ConsultationBuilder()
               .setId(consultationDto.getId())
               .setDoctor(doctor)
               .setPatient(patient)
               .setDate(consultationDto.getDate())
               .setDescription(consultationDto.getDescription())
               .setDiagnostic(consultationDto.getDiagnostic())
               .build();
       return consultation;
    }
}
