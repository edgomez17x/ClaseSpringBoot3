package med.voll.api.controllers;

import jakarta.validation.Valid;
import med.voll.api.model.Usuario;
import med.voll.api.records.AutenticacionUsuarioRec;
import med.voll.api.security.JWTTokenRec;
import med.voll.api.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<JWTTokenRec> autenticacionUsuario(@RequestBody @Valid AutenticacionUsuarioRec autenticacionUsuarioRec){
        Authentication token = new UsernamePasswordAuthenticationToken(autenticacionUsuarioRec.login(), autenticacionUsuarioRec.clave());
        var usuarioAutenticado = authenticationManager.authenticate(token);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new JWTTokenRec(JWTtoken));
    }

}
