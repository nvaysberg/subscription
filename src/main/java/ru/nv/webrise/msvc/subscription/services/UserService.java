package ru.nv.webrise.msvc.subscription.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nv.webrise.msvc.subscription.persistence.entities.Subscription;
import ru.nv.webrise.msvc.subscription.persistence.entities.User;
import ru.nv.webrise.msvc.subscription.persistence.repositories.UserRepository;
import ru.nv.webrise.msvc.subscription.tools.Utils;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private SubscriptionService subscriptionService;

    public User createUser(String email, String firstName, String lastName) {

        log.debug("SERVICE createUser: email \"{}\", first name \"{}\", last name \"{}\"",
                email, firstName, lastName);

        User user = User.builder()
                .uniqueId(Utils.generateUniqueId())
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        userRepository.save(user);

        return user;
    }

    public Iterable<User> listUsers() {

        log.debug("SERVICE listUsers");

        return userRepository.findAll();
    }

    public User getUserInfo(String uniqueId) {
        log.debug("SERVICE getUserInfo: unique ID \"{}\"", uniqueId);

        return userRepository.findByUniqueId(uniqueId).orElse(null);
    }

    public User updateUser(String uniqueId, String email, String firstName, String lastName) {
        log.debug("SERVICE updateUser: unique ID \"{}\", email \"{}\", first name \"{}\", last name \"{}\"",
                uniqueId, email, firstName, lastName);

        User user = userRepository.findByUniqueId(uniqueId).orElse(null);
        if (user == null) {
            return null;
        }

        // Cannot clear required fields
        if (email != null) {
            user.setEmail(email);
        }
        // Optional fields can be cleared
        user.setFirstName(firstName);
        user.setLastName(lastName);

        userRepository.save(user);

        return user;
    }

    @Transactional
    public boolean deleteUser(String uniqueId) {
        log.debug("SERVICE deleteUser: unique ID \"{}\"", uniqueId);

        return userRepository.deleteByUniqueId(uniqueId) > 0;
    }

    public Subscription addSubscription(String userUniqueId, String serviceUniqueId) throws ClassNotFoundException{
        log.debug("SERVICE user.addSubscription: user unique ID \"{}\", service unique ID \"{}\"",
                userUniqueId, serviceUniqueId);

        ru.nv.webrise.msvc.subscription.persistence.entities.Service service =
                serviceService.getServiceInfo(serviceUniqueId);
        if (service == null) {
            throw new ClassNotFoundException("Сервис не найден");
        }

        User user = getUserInfo(userUniqueId);
        if (user == null) {
            throw new ClassNotFoundException("Пользователь не найден");
        }

        return subscriptionService.addSubscription(user, service);
    }

    public Iterable<Subscription> listSubscriptions(String userUniqueId) {
        log.debug("SERVICE user.listSubscriptions: user unique ID \"{}\"", userUniqueId);

        User user = getUserInfo(userUniqueId);
        if (user == null) {
            return null;
        }

        return subscriptionService.listSubscriptions(user);
    }
}
