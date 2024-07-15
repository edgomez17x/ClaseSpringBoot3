package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.AgendarConsultaRec;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class PacienteActivo implements ValidadorDeConsultas {
    @Autowired
    private PacienteRepository pacienteRepository;
    public void validar(AgendarConsultaRec acr){
        if(acr.idPaciente()==null){
            throw new ValidacionDeIntegridad("El id del paciente no debe ser nulo");
        }
        var pacienteActivo = pacienteRepository.findActivoTrueAndId(acr.idPaciente());
        if(!pacienteActivo){
            throw new ValidacionDeIntegridad("No se permite agendar cita para pacientes inactivos");
        }
    }
}
