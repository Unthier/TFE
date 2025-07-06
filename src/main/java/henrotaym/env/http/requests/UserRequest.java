package henrotaym.env.http.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
    @NotBlank String name,
    @Valid String password,
    // @NotNull Integer catchingNumber
    @NotNull String mail) {}
