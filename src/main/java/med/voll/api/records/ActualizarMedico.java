package med.voll.api.records;

import jakarta.validation.constraints.NotNull;

public record ActualizarMedico(
        @NotNull Long id,
        String nombre,
        String documento,
        DireccionRec direccion) {
}
