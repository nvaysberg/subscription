package ru.nv.webrise.msvc.subscription.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nv.webrise.msvc.subscription.persistence.entities.Subscription;
import ru.nv.webrise.msvc.subscription.persistence.entities.User;
import ru.nv.webrise.msvc.subscription.persistence.repositories.SubscriptionRepository;
import ru.nv.webrise.msvc.subscription.tools.Utils;

@Slf4j
@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription addSubscription(User user, ru.nv.webrise.msvc.subscription.persistence.entities.Service service) {
        log.debug("SERVICE subscription.addSubscription: user unique ID \"{}\", service unique ID \"{}\"",
                user.getUniqueId(), service.getUniqueId());

        Subscription subscription = Subscription.builder()
                .uniqueId(Utils.generateUniqueId())
                .user(user)
                .service(service)
                .build();

        subscriptionRepository.save(subscription);

        return subscription;
    }
}
