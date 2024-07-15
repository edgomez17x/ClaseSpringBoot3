package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.AgendarConsultaRec;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoOcupado implements ValidadorDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;
    public void validar(AgendarConsultaRec acr){
        var fechaFinConsulta = acr.fecha().plusHours(1);
        var consultaActiva = consultaRepository.existsByMedicoIdAndFechaBetween(acr.idMedico(), acr.fecha(), fechaFinConsulta);
        if(consultaActiva){
            throw new ValidacionDeIntegridad("Este medico se encuentra ocupado en este horario");
        }
    }
}
