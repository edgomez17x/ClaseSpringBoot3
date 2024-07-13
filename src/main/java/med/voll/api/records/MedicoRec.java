package med.voll.api.records;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MedicoRec(
        @NotBlank String nombre,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "\\d{4,6}") String documento,
        @NotNull Especialidad especialidad,
        @NotNull @Valid DireccionRec direccion,
        @NotBlank String telefono
        ) {
}
