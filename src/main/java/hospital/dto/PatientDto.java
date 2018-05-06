package hospital.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PatientDto {
    private Long id;

    @Pattern(regexp = "^[a-zA-Z\\s]+", message="Patient's name is invalid, it should only contain letters")
    private String name;

    @Pattern(regexp = "^[0-9]+$", message = "CNP should only contain digits" )
    @Size(min = 13, max = 13, message = "CNP must have a length of 13")
    private String cnp;

    @Pattern(regexp = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$", message = "Date should be in format: yyyy-mm-dd")
    private String birthdate;

    public PatientDto(){}


    public PatientDto(String name, String cnp, String birthdate) {
        this.name = name;
        this.cnp = cnp;
        this.birthdate = birthdate;
    }

    public PatientDto(Long id, String name, String cnp, String birthdate) {
        this.id = id;
        this.name = name;
        this.cnp = cnp;
        this.birthdate = birthdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
