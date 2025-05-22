package ru.nv.webrise.msvc.subscription.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nv.webrise.msvc.subscription.Settings;
import ru.nv.webrise.msvc.subscription.persistence.entities.User;
import ru.nv.webrise.msvc.subscription.rest.payload.request.CreateUserRequest;
import ru.nv.webrise.msvc.subscription.rest.payload.request.UpdateUserRequest;
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
            return ResponseEntity.internalServerError().body(new MessageResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "*")                                 // CORS
    @GetMapping("{uniqueId:.+}")
    public ResponseEntity<?> getUserInfo(@PathVariable("uniqueId") @NotBlank @Size(min = 1, max = Settings.MAX_LEN_UNIQUE_ID) String uniqueId) {
        log.debug("API getUserInfo: unique ID \"{}\"", uniqueId);
        try {
            User user = userService.getUserInfo(uniqueId);
            return user != null
                    ? ResponseEntity.ok(user)
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("API getUserInfo: unique ID \"{}\" - error: {}", uniqueId, e.getMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "*")                                 // CORS
    @PutMapping("{uniqueId:.+}")
    public ResponseEntity<?> updateUser(@PathVariable("uniqueId") @NotBlank @Size(min = 1, max = Settings.MAX_LEN_UNIQUE_ID) String uniqueId,
                                        @Valid @RequestBody UpdateUserRequest request) {
        log.debug("API updateUser: id \"{}\", email \"{}\", first name \"{}\", last name \"{}\"",
                uniqueId, request.getEmail(), request.getFirstName(), request.getLastName());
        try {
            return ResponseEntity.ok(userService.updateUser(
                    uniqueId, request.getEmail(), request.getFirstName(), request.getLastName()
            ));
        } catch (Exception e) {
            log.error("API updateUser: id \"{}\", email \"{}\", first name \"{}\", last name \"{}\" - error: {}",
                    uniqueId, request.getEmail(), request.getFirstName(), request.getLastName(), e.getMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse(e.getMessage()));
        }
    }

}
