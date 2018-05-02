package hospital.entity.builder;
import hospital.entity.Patient;

import java.util.Date;

public class PatientBuilder {
    private Patient patient;

    public PatientBuilder(){
        patient = new Patient();
    }

    public PatientBuilder setId(Long id){
        patient.setId(id);
        return this;
    }

    public PatientBuilder setName(String name){
        patient.setName(name);
        return this;
    }

    public PatientBuilder setCnp(String cnp){
        patient.setCnp(cnp);
        return this;
    }

    public PatientBuilder setBirthdate(Date date){
        patient.setBirthdate(date);
        return this;
    }

    public Patient build(){
        return patient;
    }

}
