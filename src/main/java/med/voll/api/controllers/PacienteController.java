package med.voll.api.controllers;


import jakarta.validation.Valid;
import med.voll.api.domain.paciente.AgregaPacienteRec;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRec;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public ResponseEntity<AgregaPacienteRec> agregaPaciente(@RequestBody @Valid PacienteRec pacienteRec, UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = pacienteRepository.save(new Paciente(pacienteRec));
        AgregaPacienteRec agregaPacienteRec = new AgregaPacienteRec(paciente.getId(), paciente.getNombre());
        URI uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(agregaPacienteRec);
    }
}
