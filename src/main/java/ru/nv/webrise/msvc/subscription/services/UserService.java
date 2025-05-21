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

        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("SERVICE createUser: email \"{}\", first name \"{}\", last name \"{}\" - error: {}",
                    email, firstName, lastName, e.getMessage());
            throw e;
        }

        return user;

    }
}
