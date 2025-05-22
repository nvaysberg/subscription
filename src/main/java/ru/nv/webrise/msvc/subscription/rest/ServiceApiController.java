package ru.nv.webrise.msvc.subscription.rest;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nv.webrise.msvc.subscription.rest.payload.request.CreateServiceRequest;
import ru.nv.webrise.msvc.subscription.rest.payload.response.MessageResponse;
import ru.nv.webrise.msvc.subscription.services.ServiceService;

@RestController
@Slf4j
@RequestMapping("/services")
public class ServiceApiController {

    @Autowired
    private ServiceService serviceService;

    @CrossOrigin(origins = "*")                                 // CORS
    @PostMapping("")
    public ResponseEntity<?> createService(@Valid @RequestBody CreateServiceRequest request) {
        log.debug("API createService: name \"{}\"", request.getName());
        try {
            return ResponseEntity.ok(serviceService.createService(request.getName()));
        } catch (Exception e) {
            log.error("API createService: name \"{}\" - error: {}", request.getName(), e.getMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "*")                                 // CORS
    @GetMapping("")
    public ResponseEntity<?> listServices() {
        log.debug("API listServices");
        try {
            return ResponseEntity.ok(serviceService.listServices());
        } catch (Exception e) {
            log.error("API listServices - error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse(e.getMessage()));
        }
    }
}
