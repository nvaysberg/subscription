package ru.nv.webrise.msvc.subscription.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nv.webrise.msvc.subscription.persistence.entities.User;
import ru.nv.webrise.msvc.subscription.persistence.repositories.UserRepository;
import ru.nv.webrise.msvc.subscription.tools.Utils;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
}
