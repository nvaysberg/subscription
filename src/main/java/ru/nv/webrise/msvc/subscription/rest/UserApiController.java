package ru.nv.webrise.msvc.subscription.rest;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nv.webrise.msvc.subscription.rest.payload.request.CreateUserRequest;
import ru.nv.webrise.msvc.subscription.rest.payload.response.MessageResponse;
import ru.nv.webrise.msvc.subscription.services.UserService;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserApiController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "*")                                 // CORS
    @PostMapping("")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.debug("API createUser: email \"{}\", first name \"{}\", last name \"{}\"",
                request.getEmail(), request.getFirstName(), request.getLastName());
        try {
            return ResponseEntity.ok(userService.createUser(
                    request.getEmail(), request.getFirstName(), request.getLastName()
            ));
        } catch (Exception e) {
            log.error("API createUser: email \"{}\", first name \"{}\", last name \"{}\" - error: {}",
                    request.getEmail(), request.getFirstName(), request.getLastName(), e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

}
