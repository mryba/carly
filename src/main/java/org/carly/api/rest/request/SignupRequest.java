package org.carly.api.rest.request;

import lombok.Getter;
import lombok.Setter;
import org.carly.core.security.validation.*;
import org.carly.core.shared.model.Address;


import javax.validation.constraints.*;

@PasswordMatcher
@Getter
@Setter
public class SignupRequest {
    @Size(min = 3, max = 30)
    private String companyName;

    @Size(min = 3, max = 20)
    private String firstName;

    @Size(min = 3, max = 20)
    private String lastName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 20)
    private String phone;

    private String gender;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotEmpty
    @Size(min = 6, max = 40)
    private String rePassword;

    private Address address;

}
