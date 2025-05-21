package ru.nv.webrise.msvc.subscription.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nv.webrise.msvc.subscription.persistence.entities.Subscription;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
}
