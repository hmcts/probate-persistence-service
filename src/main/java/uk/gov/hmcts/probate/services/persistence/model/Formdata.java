package uk.gov.hmcts.probate.services.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.UpdateTimestamp;


import uk.gov.hmcts.probate.services.persistence.transformers.json.JsonBinaryType;
import uk.gov.hmcts.probate.services.persistence.transformers.json.JsonStringType;

@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})

@Entity
public class Formdata implements Serializable {

    @Id
    @JsonProperty("id")
    private String id;

    @CreationTimestamp
    private Date creationTime;

    @UpdateTimestamp
    private Date modificationTime;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @JsonProperty("formdata")
    private Object formData;

    @JsonProperty("submissionReference")
    private long submissionReference;

}
