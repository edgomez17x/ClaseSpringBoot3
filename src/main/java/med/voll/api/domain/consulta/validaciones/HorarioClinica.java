package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.AgendarConsultaRec;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
@Component
public class HorarioClinica implements ValidadorDeConsultas {
    public void validar(AgendarConsultaRec acr){
        var domingo = DayOfWeek.SUNDAY.equals(acr.fecha().getDayOfWeek());
        var apertura = acr.fecha().getHour()<7;
        var cierre = acr.fecha().getHour()>19;
        if(domingo || apertura || cierre){
            throw new ValidacionDeIntegridad("El horario de atención de la semana es de lunes a sábado");
        }
    }
}
