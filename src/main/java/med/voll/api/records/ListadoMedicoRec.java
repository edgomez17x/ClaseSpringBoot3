package med.voll.api.records;

import med.voll.api.model.Medico;

public record ListadoMedicoRec(
        Long id,
        String nombre,
        Especialidad especialidad,
        String documento,
        String email
) {
    public ListadoMedicoRec(Medico medico){
        this(medico.getId(), medico.getNombre(), medico.getEspecialidad(), medico.getDocumento(), medico.getEmail());
    }
}
