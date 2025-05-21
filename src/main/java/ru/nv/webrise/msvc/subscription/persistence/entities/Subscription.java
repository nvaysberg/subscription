package ru.nv.webrise.msvc.subscription.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import ru.nv.webrise.msvc.subscription.persistence.entities.embedded.ServiceFields;

import static ru.nv.webrise.msvc.subscription.Settings.MAX_LEN_UNIQUE_ID;

@Entity
@Getter
@Setter
@Builder                    // DO NOT USE toBuilder() - SUPERCLASS FIELDS ARE NOT BEING COPIED!!!
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "subscriptions",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "service_id"})
        })
public class Subscription extends ServiceFields {

    @Column(nullable = false, length = MAX_LEN_UNIQUE_ID)
    private String uniqueId;    // UUID

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @JsonBackReference
    public User getUser() { return this.user; }

    @ManyToOne
    @JoinColumn(nullable = false, name = "service_id")
    private Service service;

    @JsonBackReference
    public Service getService() { return this.service; }

}
