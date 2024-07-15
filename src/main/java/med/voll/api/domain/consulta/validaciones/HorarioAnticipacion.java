package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.AgendarConsultaRec;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
@Component
public class HorarioAnticipacion implements ValidadorDeConsultas{
    public void validar(AgendarConsultaRec acr){
        var ahora = LocalDateTime.now();
        var horaConsulta = acr.fecha();
        var diferencia30Min = Duration.between(ahora,horaConsulta).toMinutes()<30;
        if(diferencia30Min){
            throw new ValidacionDeIntegridad("La consulta se debe solicitar con 30 minutos de anticipación");
        }
    }
}
