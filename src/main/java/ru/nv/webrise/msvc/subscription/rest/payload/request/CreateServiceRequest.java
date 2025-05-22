package ru.nv.webrise.msvc.subscription.rest.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import static ru.nv.webrise.msvc.subscription.Settings.*;

@Getter
@Setter
public class CreateServiceRequest {

    @NotBlank
    @Size(min = 1, max = MAX_LEN_SERVICE_NAME)
    private String name;

}
