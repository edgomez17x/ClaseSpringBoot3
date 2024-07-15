package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Especialidad;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private List<ValidadorDeConsultas> vcList;

    public DetalleConsultaRec agendar(AgendarConsultaRec agendarConsultaRec){
        if(!(pacienteRepository.findById(agendarConsultaRec.idPaciente()).isPresent())){
            throw new ValidacionDeIntegridad("Este paciente no fue encontrado");
        }
        vcList.forEach(vc -> vc.validar(agendarConsultaRec));
        Paciente paciente = pacienteRepository.getReferenceById(agendarConsultaRec.idPaciente());
        Medico medico = seleccionarMedico(agendarConsultaRec);
        if(medico==null){
            throw new ValidacionDeIntegridad("No existen medicos disponibles");
        }
        Consulta consulta = new Consulta(medico, paciente, agendarConsultaRec.fecha());
        consulta = consultaRepository.save(consulta);
        return new DetalleConsultaRec(consulta);
    }

    private Medico seleccionarMedico(AgendarConsultaRec acr) {
        if(acr.idMedico()!=null){
            if(medicoRepository.findActivoById(acr.idMedico())){
                return medicoRepository.getReferenceById(acr.idMedico());
            }else{
                throw new ValidacionDeIntegridad("El medico enviado no existe o está como inactivo, se procede a seleccionar un aleatorio");
            }
        }
        if(acr.especialidad()==null){
            throw new ValidacionDeIntegridad("Se debe seleccionar una especialidad");
        }
        return medicoRepository.seleccionarMedicoEspecialidadFecha(acr.especialidad(), acr.fecha());
    }
}
