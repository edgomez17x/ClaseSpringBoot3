package med.voll.api.domain.medico;

import med.voll.api.domain.direccion.DireccionRec;

public record RespuestaMedicoRec(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        DireccionRec direccion
) {
}
