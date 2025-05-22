package ru.nv.webrise.msvc.subscription.persistence.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nv.webrise.msvc.subscription.persistence.entities.Subscription;
import ru.nv.webrise.msvc.subscription.persistence.entities.User;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    Iterable<Subscription> findByUser(User user);
    long deleteByUniqueId(String uniqueId);

    interface TopServicesTableEntry {
        String getName();
        String getUniqueId();
        Long getNumSubscriptions();
    }

    @Query("SELECT s.service.name as name, s.service.uniqueId as uniqueId, count(s.id) as numSubscriptions " +
            "FROM Subscription s " +
            "GROUP BY s.service " +
            "ORDER BY numSubscriptions DESC " +
            "LIMIT :numServices")
    Iterable<TopServicesTableEntry> getTopServices(@Param("numServices") Integer numServices);
}
