package hospital.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)    // optinal=false =>doctor is mandatory
    @JoinColumn(name="doctor_id")
    private User doctor;

    @ManyToOne(optional = false)    // optinal=false =>patient is mandatory
    @JoinColumn(name="patient_id")
    private Patient patient;

    private String diagnostic;
    private String description;
    private Date date;

    public Consultation(){}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
