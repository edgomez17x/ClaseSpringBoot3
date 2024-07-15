package med.voll.api.controllers;

import med.voll.api.domain.consulta.AgendarConsultaRec;
import med.voll.api.domain.consulta.DetalleConsultaRec;
import med.voll.api.domain.medico.Especialidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<AgendarConsultaRec> agendarConsultaRecJacksonTester;
    @Autowired
    private JacksonTester<DetalleConsultaRec> detalleConsultaRecJacksonTester;

    @Test
    @WithMockUser
    @DisplayName("Debería retornar http 400 cuando los datos ingresados sean invalidos")
    void agendarCaso1() throws Exception {
        var response = mockMvc.perform(post("/consultas")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser
    @DisplayName("Debería retornar http 200 cuando los datos ingresados sean validos")
    void agendarCaso2() throws Exception {
        var fecha = LocalDateTime.now().plusHours(1);
        Especialidad especialidad = Especialidad.CARDIOLOGIA;
        DetalleConsultaRec detalleConsultaRec = new DetalleConsultaRec(null, 2L, 1L,fecha);

        var response = mockMvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(agendarConsultaRecJacksonTester.write(
                                new AgendarConsultaRec(null, 2L, 1L, fecha, null)).getJson())
        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        var jsonEsperado = detalleConsultaRecJacksonTester.write(detalleConsultaRec);
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}