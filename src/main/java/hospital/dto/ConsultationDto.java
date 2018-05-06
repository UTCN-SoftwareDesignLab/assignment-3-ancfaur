package hospital.dto;

public class ConsultationDto {

    private Long id;
    private Long doctor_id;
    private Long patient_id;
    private String diagnostic;
    private String description;
    private String date;

    public ConsultationDto(){}

    public ConsultationDto(Long doctor_id, Long patient_id, String diagnostic, String description, String date) {
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
        this.diagnostic = diagnostic;
        this.description = description;
        this.date = date;
    }

    public ConsultationDto(Long id, Long doctor_id, Long patient_id, String diagnostic, String description, String date) {
        this.id = id;
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
        this.diagnostic = diagnostic;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(Long doctor_id) {
        this.doctor_id = doctor_id;
    }

    public Long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Long patient_id) {
        this.patient_id = patient_id;
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
}
