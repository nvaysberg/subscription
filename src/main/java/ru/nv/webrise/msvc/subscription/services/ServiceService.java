package ru.nv.webrise.msvc.subscription.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nv.webrise.msvc.subscription.persistence.repositories.ServiceRepository;
import ru.nv.webrise.msvc.subscription.tools.Utils;

@Slf4j
@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public ru.nv.webrise.msvc.subscription.persistence.entities.Service createService(String name) {

        log.debug("SERVICE createService: name \"{}\"", name);

        ru.nv.webrise.msvc.subscription.persistence.entities.Service service =
                ru.nv.webrise.msvc.subscription.persistence.entities.Service.builder()
                        .uniqueId(Utils.generateUniqueId())
                        .name(name)
                        .build();

        serviceRepository.save(service);

        return service;
    }

}
