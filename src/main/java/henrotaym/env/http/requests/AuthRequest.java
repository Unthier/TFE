package henrotaym.env.http.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthRequest(@NotBlank @NotNull String mail, @NotBlank @NotNull String password) {}
