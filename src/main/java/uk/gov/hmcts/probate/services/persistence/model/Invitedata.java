package uk.gov.hmcts.probate.services.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "invitedata")
public class Invitedata implements Serializable {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("formdataId")
    private String formdataId;

    @JsonProperty("mainExecutorName")
    private String mainExecutorName;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("agreed")
    private Boolean agreed;

    @CreationTimestamp
    private Date creationTime;

    @UpdateTimestamp
    private Date modificationTime;
}
