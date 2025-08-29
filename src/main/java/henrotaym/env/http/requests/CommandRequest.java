package henrotaym.env.http.requests;

import henrotaym.env.enums.MethodeName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommandRequest(
    @NotBlank @NotNull MethodeName methode,
    @NotBlank @NotNull String url,
    @NotBlank @NotNull String descript) {}
