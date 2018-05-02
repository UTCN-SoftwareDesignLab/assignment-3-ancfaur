package hospital.entity.builder;

import hospital.entity.Consultation;
import hospital.entity.Patient;
import hospital.entity.User;

import java.util.Date;

public class ConsultationBuilder {
    private Consultation consultation;

    public ConsultationBuilder(){
        consultation = new Consultation();
    }

    public ConsultationBuilder setId(Long id){
        consultation.setId(id);
        return this;
    }

    public ConsultationBuilder setDoctor(User doctor){
        consultation.setDoctor(doctor);
        return this;
    }

    public ConsultationBuilder setPatient(Patient patient){
        consultation.setPatient(patient);
        return this;
    }

    public ConsultationBuilder setDate(Date date){
        consultation.setDate(date);
        return this;
    }

    public ConsultationBuilder setDiagnostic(String diagnostic){
        consultation.setDiagnostic(diagnostic);
        return this;
    }

    public ConsultationBuilder setDescription(String description){
        consultation.setDescription(description);
        return this;
    }

    public Consultation build(){
        return this.consultation;
    }
}
