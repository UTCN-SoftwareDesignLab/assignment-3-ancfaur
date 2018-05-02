package hospital.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class PatientDto {
    private Long id;

    @Pattern(regexp = "^[a-zA-Z\\s]+", message="Patient's name is invalid, it should only contain letters")
    private String name;

    @Pattern(regexp = "^[1-9]+$", message = "CNP should only contain digits" )
    @Size(min = 13, max = 13, message = "CNP must have a length of 13")
    private String cnp;

    private Date birthdate;

    public PatientDto(){}


    public PatientDto(String name, String cnp, Date birthdate) {
        this.name = name;
        this.cnp = cnp;
        this.birthdate = birthdate;
    }

    public PatientDto(Long id, String name, String cnp, Date birthdate) {
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
