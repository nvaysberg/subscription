package ru.nv.webrise.msvc.subscription.persistence.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import ru.nv.webrise.msvc.subscription.persistence.entities.embedded.ServiceFields;

import java.util.Set;

import static ru.nv.webrise.msvc.subscription.Settings.*;

@Entity
@Getter
@Setter
@Builder                    // DO NOT USE toBuilder() - SUPERCLASS FIELDS ARE NOT BEING COPIED!!!
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "services",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "uniqueId"),
                @UniqueConstraint(columnNames = "name")
        })
public class Service extends ServiceFields {

    @Column(nullable = false, length = MAX_LEN_UNIQUE_ID)
    private String uniqueId;    // UUID

    @Column(nullable = false, length = MAX_LEN_SERVICE_NAME)
    private String name;

    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    Set<Subscription> subscriptions;

    @JsonManagedReference
    public Set<Subscription> getSubscriptions() { return this.subscriptions; }

}
