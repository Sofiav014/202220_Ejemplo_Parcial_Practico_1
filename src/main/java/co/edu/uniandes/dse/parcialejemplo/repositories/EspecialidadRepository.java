package co.edu.uniandes.dse.parcialejemplo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;

import java.util.List;

@Repository
public interface EspecialidadRepository extends JpaRepository<EspecialidadEntity, Long>{
    List<EspecialidadEntity> findByNombre(String nombre);
}
