package ru.nv.webrise.msvc.subscription.rest.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import static ru.nv.webrise.msvc.subscription.Settings.MAX_LEN_SERVICE_NAME;
import static ru.nv.webrise.msvc.subscription.Settings.MAX_LEN_UNIQUE_ID;

@Getter
@Setter
public class AddSubscriptionRequest {

    @NotBlank
    @Size(min = 1, max = MAX_LEN_UNIQUE_ID)
    private String uniqueId;

}
