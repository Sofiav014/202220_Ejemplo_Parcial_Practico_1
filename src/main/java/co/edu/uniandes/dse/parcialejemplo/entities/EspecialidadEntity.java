package co.edu.uniandes.dse.parcialejemplo.entities;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class EspecialidadEntity extends BaseEntity {

    private String nombre;
    private String descripcion;

    @PodamExclude
    @ManyToMany
    private List<MedicoEntity> medicos = new ArrayList<>();

}
