package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DireccionRec;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Debería retornar nulo cuando el medico se encuentre en consulta con otro paciente en ese horario")
    void seleccionarMedicoEspecialidadFechaCaso1() {
        var proximoLunes10H = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);
        var medico=registrarMedico("Jose","j@mail.com","123456",Especialidad.CARDIOLOGIA);
        var paciente=registrarPaciente("antonio", "a@mail.com","654321");
        registrarConsulta(medico, paciente, proximoLunes10H);
        var medicoLibre = medicoRepository.seleccionarMedicoEspecialidadFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);
        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Debería retornar un medico cuando realice la consulta en la base de datos para ese horario")
    void seleccionarMedicoEspecialidadFechaCaso2() {
        var proximoLunes10H = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);
        var medico=registrarMedico("Jose","j@mail.com","123456",Especialidad.CARDIOLOGIA);
        var medicoLibre = medicoRepository.seleccionarMedicoEspecialidadFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);
        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(medico, paciente, fecha));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private MedicoRec datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new MedicoRec(
                nombre,
                email,
                documento,
                especialidad,
                datosDireccion(),
                "61999999999"
        );
    }

    private PacienteRec datosPaciente(String nombre, String email, String documento){
        return new PacienteRec(
                nombre,
                email,
                documento,
                datosDireccion(),
                "1234567890"
        );
    }

    private DireccionRec datosDireccion(){
        return new DireccionRec(
                "loca",
                "axul",
                "acapulco",
                321,
                "12"
        );
    }

}