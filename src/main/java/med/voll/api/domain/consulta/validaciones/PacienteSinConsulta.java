package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.AgendarConsultaRec;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;
    public void validar(AgendarConsultaRec acr){
        var fechaApertura = acr.fecha().withHour(7);
        var fechaCierre = acr.fecha().withHour(18);
        var consultaActiva = consultaRepository.existsByPacienteIdAndFechaBetween(acr.idPaciente(), fechaApertura, fechaCierre);
        if(consultaActiva){
            throw new ValidacionDeIntegridad("No se permite agendar 2 citas el mismo día");
        }
    }
}
