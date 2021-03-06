package uk.gov.hmcts.probate.services.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    @Column(updatable = false)
    @CreationTimestamp
    private Date creationTime;

    @UpdateTimestamp
    private Date modificationTime;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @JsonProperty("submitdata")
    private Object submitData;

    private void writeObject(ObjectOutputStream out)
        throws IOException {
        out.defaultWriteObject();
        out.writeObject(submitData);
    }

    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        submitData = in.readObject();
    }
}






