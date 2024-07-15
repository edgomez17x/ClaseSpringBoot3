package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaConsultaService;
import med.voll.api.domain.consulta.AgendarConsultaRec;
import med.voll.api.domain.consulta.DetalleConsultaRec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaConsultaService agendaConsultaService;

    @PostMapping
    public ResponseEntity agendar(@RequestBody @Valid AgendarConsultaRec agendarConsultaRec){
        var response = agendaConsultaService.agendar(agendarConsultaRec);
        return ResponseEntity.ok(response);
    }

}
