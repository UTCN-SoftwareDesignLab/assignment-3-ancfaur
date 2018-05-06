package hospital.converter.consultation;

import hospital.converter.DateConverter;
import hospital.dto.ConsultationDto;
import hospital.entity.Consultation;
import hospital.entity.Patient;
import hospital.entity.User;
import hospital.entity.builder.ConsultationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConsultationConverterImpl implements ConsultationConverter {

    @Autowired
    private DateConverter dateConverter;
    @Override

    public ConsultationDto toDto(Consultation consultation) {
        if (consultation==null) return null;
        ConsultationDto consultationDto = new ConsultationDto(consultation.getId(), consultation.getDoctor().getId(), consultation.getPatient().getId(), consultation.getDiagnostic(), consultation.getDescription(), consultation.getDate().toString());
        return consultationDto;
    }

    @Override
    public Consultation fromDto(ConsultationDto consultationDto, Patient patient, User doctor){
       Consultation consultation = new ConsultationBuilder()
               .setId(consultationDto.getId())
               .setDoctor(doctor)
               .setPatient(patient)
               .setDate(dateConverter.toDate("withTimeInfo", consultationDto.getDate()))
               .setDescription(consultationDto.getDescription())
               .setDiagnostic(consultationDto.getDiagnostic())
               .build();
       return consultation;
    }

    public List<ConsultationDto> toDto(List<Consultation> consultations){
        List<ConsultationDto> consultationDtos = new ArrayList<>();
        for(Consultation consultation:consultations){
            consultationDtos.add(toDto(consultation));
        }
        return consultationDtos;
    }
}
