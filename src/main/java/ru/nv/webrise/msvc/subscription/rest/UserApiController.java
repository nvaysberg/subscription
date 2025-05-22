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
import ru.nv.webrise.msvc.subscription.rest.payload.request.AddSubscriptionRequest;
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
    @GetMapping("")
    public ResponseEntity<?> listUsers() {
        log.debug("API listUsers");
        try {
            return ResponseEntity.ok(userService.listUsers());
        } catch (Exception e) {
            log.error("API listUsers - error: {}", e.getMessage());
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

    @CrossOrigin(origins = "*")                                 // CORS
    @DeleteMapping("{uniqueId:.+}")
    public ResponseEntity<?> deleteUser(@PathVariable("uniqueId") @NotBlank @Size(min = 1, max = Settings.MAX_LEN_UNIQUE_ID) String uniqueId) {
        log.debug("API deleteUser: id \"{}\"", uniqueId);
        try {
            return userService.deleteUser(uniqueId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("API deleteUser: id \"{}\" - error: {}", uniqueId, e.getMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "*")                                 // CORS
    @PostMapping("{uniqueId:.+}/subscriptions")
    public ResponseEntity<?> addSubscription(@PathVariable("uniqueId") @NotBlank @Size(min = 1, max = Settings.MAX_LEN_UNIQUE_ID) String uniqueId,
                                             @Valid @RequestBody AddSubscriptionRequest request) {
        log.debug("API addSubscription: user unique ID \"{}\", service unique ID \"{}\"",
                uniqueId, request.getUniqueId());
        try {
            return ResponseEntity.ok(userService.addSubscription(uniqueId, request.getUniqueId()));
        } catch (ClassNotFoundException e) {
            log.error("API addSubscription: user unique ID \"{}\", service unique ID \"{}\" - error: {}",
                    uniqueId, request.getUniqueId(), e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("API addSubscription: user unique ID \"{}\", service unique ID \"{}\" - error: {}",
                    uniqueId, request.getUniqueId(), e.getMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "*")                                 // CORS
    @GetMapping("{uniqueId:.+}/subscriptions")
    public ResponseEntity<?> listUserSubscriptions(@PathVariable("uniqueId") @NotBlank @Size(min = 1, max = Settings.MAX_LEN_UNIQUE_ID) String uniqueId) {
        log.debug("API getUserSubscriptions: unique ID \"{}\"", uniqueId);
        try {
            User user = userService.getUserInfo(uniqueId);
            return user != null
                    ? ResponseEntity.ok(user.getSubscriptions())
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("API getUserSubscriptions: unique ID \"{}\" - error: {}", uniqueId, e.getMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse(e.getMessage()));
        }
    }

}
