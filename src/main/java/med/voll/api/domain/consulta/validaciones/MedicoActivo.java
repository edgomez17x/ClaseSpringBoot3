package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.AgendarConsultaRec;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidadorDeConsultas {
    @Autowired
    private MedicoRepository medicoRepository;
    public void validar(AgendarConsultaRec acr){
        if(acr.idMedico()!=null){
            var medicoActivo = medicoRepository.findActivoById(acr.idMedico());
            if(medicoActivo){
                throw new ValidacionDeIntegridad("No se permite agendar cita para medicos inactivos");
            }
        }
    }
}
