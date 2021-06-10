package com.cnu.spg.login.request;

import com.cnu.spg.login.validate.FieldMatch;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})

@Getter
@Setter
public class ChangingPasswordRequest {
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String beforePassword;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String password;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String matchingPassword;

    public ChangingPasswordRequest() {
    }
}
