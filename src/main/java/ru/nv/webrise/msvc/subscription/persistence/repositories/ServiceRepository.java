package ru.nv.webrise.msvc.subscription.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nv.webrise.msvc.subscription.persistence.entities.Service;

import java.util.Optional;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {
    Optional<Service> findByUniqueId(String uniqueId);
}
