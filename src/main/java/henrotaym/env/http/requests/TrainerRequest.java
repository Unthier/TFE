package henrotaym.env.http.requests;

import jakarta.validation.constraints.NotBlank;

public record TrainerRequest(@NotBlank String name) {}
