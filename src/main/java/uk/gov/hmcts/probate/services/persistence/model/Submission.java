package uk.gov.hmcts.probate.services.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.*;
import uk.gov.hmcts.probate.services.persistence.transformers.json.JsonBinaryType;
import uk.gov.hmcts.probate.services.persistence.transformers.json.JsonStringType;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
    @TypeDef(name = "json", typeClass = JsonStringType.class)
})

@Entity
public class Submission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;


    @CreationTimestamp
    private Date creationTime;

    @UpdateTimestamp
    private Date modificationTime;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @JsonProperty("submitdata")
    private Object submitData;
}






