package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DetalleConsultaRec(
        Long id,
        Long idPaciente,
        Long idMedico,
        LocalDateTime fecha
) {
}
