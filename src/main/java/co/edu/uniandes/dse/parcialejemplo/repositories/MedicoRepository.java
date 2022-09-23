package co.edu.uniandes.dse.parcialejemplo.repositories;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import java.util.List;
public interface MedicoRepository extends JpaRepository<MedicoEntity, Long>{
    List<MedicoEntity> findByNombre(String nombre);
    List<MedicoEntity> findByApellido(String apellido);
}
