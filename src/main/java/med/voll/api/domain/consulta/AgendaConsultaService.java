package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Especialidad;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AgendaConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;

    public void agendar(AgendarConsultaRec agendarConsultaRec){
        if(pacienteRepository.findById(agendarConsultaRec.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("Este paciente no fue encontrado");
        }
        Paciente paciente = pacienteRepository.getReferenceById(agendarConsultaRec.idPaciente());
        Medico medico = seleccionarMedico(agendarConsultaRec);
        Consulta consulta = new Consulta(medico, paciente, agendarConsultaRec.fecha());
        consultaRepository.save(consulta);
    }

    private Medico seleccionarMedico(AgendarConsultaRec acr) {
        if(acr.idMedico()!=null){
            return medicoRepository.getReferenceById(acr.idMedico());
        }
        if(acr.especialidad()==null){
            throw new ValidacionDeIntegridad("Se debe seleccionar una especialidad");
        }
        return medicoRepository.seleccionarMedicoEspecialidadFecha(acr.especialidad(), acr.fecha());
    }
}
