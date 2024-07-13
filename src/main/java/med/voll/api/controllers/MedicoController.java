package med.voll.api.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.model.Medico;
import med.voll.api.records.*;
import med.voll.api.repository.MedicoRepository;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<RespuestaMedicoRec> registrarMedico(@RequestBody @Valid MedicoRec medicoRec, UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoRepository.save(new Medico(medicoRec));
        RespuestaMedicoRec respuestaMedicoRec = new RespuestaMedicoRec(medico.getId(),
                medico.getNombre(), medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                new DireccionRec(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        URI uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(respuestaMedicoRec);
    }

    @GetMapping
    public ResponseEntity<Page<ListadoMedicoRec>> listadoMedicos(@PageableDefault(size = 2) Pageable pageable){
//        return medicoRepository.findAll(pageable).map(ListadoMedicoRec::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(pageable).map(ListadoMedicoRec::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<RespuestaMedicoRec> actualizarMedico(@RequestBody @Valid ActualizarMedico actualizarMedico){
        Medico medico = medicoRepository.getReferenceById(actualizarMedico.id());
        medico.actualizarDatos(actualizarMedico);
        return ResponseEntity.ok(new RespuestaMedicoRec(medico.getId(),
                medico.getNombre(), medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                new DireccionRec(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento())
        ));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> eliminaMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaMedicoRec> retornaMedicoById(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new RespuestaMedicoRec(medico.getId(),
                medico.getNombre(), medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                new DireccionRec(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento())
        ));
    }
//    public void eliminaMedico(@PathVariable Long id){
//        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }
}
