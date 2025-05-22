package ru.nv.webrise.msvc.subscription.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nv.webrise.msvc.subscription.rest.payload.response.MessageResponse;
import ru.nv.webrise.msvc.subscription.services.SubscriptionService;

@RestController
@Slf4j
@RequestMapping("/subscriptions")
public class SubscriptionApiController {

    @Autowired
    private SubscriptionService subscriptionService;

    @CrossOrigin(origins = "*")                                 // CORS
    @GetMapping("top")
    public ResponseEntity<?> getTopServices() {
        log.debug("API getTopServices");
        try {
            return ResponseEntity.ok(subscriptionService.getTopServices(3));
        } catch (Exception e) {
            log.error("API getTopServices - error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse(e.getMessage()));
        }
    }

}
