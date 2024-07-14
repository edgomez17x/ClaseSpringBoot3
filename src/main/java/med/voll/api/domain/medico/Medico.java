package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Getter
@Entity
@Table(name = "Medicos")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;
    private boolean activo;

    public Medico(MedicoRec medicoRec) {
        this.nombre = medicoRec.nombre();
        this.direccion = new Direccion(medicoRec.direccion());
        this.documento = medicoRec.documento();
        this.email = medicoRec.email();
        this.especialidad = medicoRec.especialidad();
        this.telefono = medicoRec.telefono();
        this.activo = true;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Long getId() {
        return id;
    }

    public void actualizarDatos(ActualizarMedico actualizarMedico) {
        this.nombre = actualizarMedico.nombre()!=null?actualizarMedico.nombre():this.nombre;
        this.documento = actualizarMedico.documento()!=null?actualizarMedico.documento():this.documento;
        this.direccion = actualizarMedico.direccion()!=null?direccion.actualizaDireccion(actualizarMedico.direccion()):this.direccion;
    }

    public void desactivarMedico() {
        this.activo = false;
    }

    public String getTelefono() {
        return telefono;
    }

    public Direccion getDireccion(){
        return direccion;
    }
}
