package uk.gov.hmcts.probate.services.persistence.model;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Entity(name = "registry")
public class Registry implements Serializable {

    @Id
    private String id;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String address;

    @NotNull
    @PositiveOrZero
    private Long ratio;

    @NotNull
    @PositiveOrZero
    private Long counter;

    @AssertTrue(message="counter should be less than or equal to ratio")
    private boolean isValid() {
        return this.counter <= this.ratio;
    }

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
        return StringUtils.capitalize(id);
    }
}
