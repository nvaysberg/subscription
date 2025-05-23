package ru.nv.webrise.msvc.subscription.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table( name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "uniqueId"),
                @UniqueConstraint(columnNames = "email")
        })
public class User extends ServiceFields {

    @Column(nullable = false, length = MAX_LEN_UNIQUE_ID)
    private String uniqueId;    // UUID

    @Column(nullable = false, length = MAX_LEN_EMAIL)
    private String email;

    @Column(length = MAX_LEN_FIRST_NAME)
    private String firstName;

    @Column(length = MAX_LEN_LAST_NAME)
    private String lastName;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    Set<Subscription> subscriptions;

}
