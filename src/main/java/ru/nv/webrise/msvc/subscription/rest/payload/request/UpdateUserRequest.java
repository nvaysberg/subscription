package ru.nv.webrise.msvc.subscription.rest.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import static ru.nv.webrise.msvc.subscription.Settings.*;

@Getter
@Setter
public class UpdateUserRequest {

    @Size(min = 1, max = MAX_LEN_EMAIL)
    private String email;

    @Size(min = 1, max = MAX_LEN_FIRST_NAME)
    private String firstName;

    @Size(min = 1, max = MAX_LEN_LAST_NAME)
    private String lastName;

}
