package uk.gov.hmcts.probate.services.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "registry")
public class Registry implements Serializable {

    @Id
    private String id;

    @NotNull
    private String email;

    @NotNull
    private String address;

    @NotNull
    private Long ratio;

    @NotNull
    private Long counter;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Long getRatio() {
        return ratio;
    }

    public Long getCounter() {
        return counter;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRatio(Long ratio) {
        this.ratio = ratio;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public void incrementCounter() {
        counter += 1;
    }

    public void resetCounter() {
        counter = 0L;
    }

    public String capitalizeRegistryName() {
        return id.substring(0,1).toUpperCase()
                + id.substring(1).toLowerCase();
    }
}
