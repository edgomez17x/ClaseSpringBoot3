package med.voll.api.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DireccionRec(
        @NotBlank String calle,
        @NotBlank String distrito,
        @NotBlank String ciudad,
        @NotNull int numero,
        @NotBlank String complemento
) {
}
