package uk.gov.hmcts.probate.services.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "registry")
public class Registry implements Serializable {

    @Id
    private String name;

    private String address;

    private String email;

    private Long ratio;

    private Long count;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public Long getRatio() {
        return ratio;
    }

    public Long getCount() {
        return count;
    }

    public void incrementCount() {
        count++;
    }

    public void resetCount() {
        count = 0L;
    }

    public String capitalizeRegistryName() {
        return name.substring(0,1).toUpperCase()
                + name.substring(1).toLowerCase();
    }
}
