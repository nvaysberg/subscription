package ru.nv.webrise.msvc.subscription.persistence.entities.embedded;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

// DO NOT USE toBuilder() - SUPERCLASS FIELDS ARE NOT BEING COPIED!!!
@MappedSuperclass
public class ServiceFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Timestamp the record was created and last updated
    @Column(name="ts_created")
    private LocalDateTime timestampCreated;
    @Column(name="ts_updated")
    private LocalDateTime timestampUpdated;

    @PrePersist
    private void beforeCreate() {
        timestampCreated = LocalDateTime.now(ZoneOffset.UTC);
        timestampUpdated = timestampCreated;
    }

    @PreUpdate
    private void beforeUpdate() {
        timestampUpdated = LocalDateTime.now(ZoneOffset.UTC);
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper()
                    // this is necessary for Jackson to process LocalDateTime and to avoid this error:
                    // Java 8 date/time type `java.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: java.util.LinkedHashMap["fprtTs"])
                    .registerModule(new JavaTimeModule())
                    // this is to write LocalDateTime as ISO string, not array of numbers
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return e.getMessage() + " : " + super.toString();
        }
    }

}
