package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DireccionRec;

public record ActualizarMedico(
        @NotNull Long id,
        String nombre,
        String documento,
        DireccionRec direccion) {
}
