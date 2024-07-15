package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Boolean existsByPacienteIdAndFechaBetween(Long aLong, LocalDateTime fechaApertura, LocalDateTime fechaCierre);

    Boolean existsByMedicoIdAndFechaBetween(Long aLong, LocalDateTime fecha, LocalDateTime fechaFinConsulta);
}
