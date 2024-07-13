package med.voll.api.records;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.model.Medico;

public record RespuestaMedicoRec(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        DireccionRec direccion
) {
}
