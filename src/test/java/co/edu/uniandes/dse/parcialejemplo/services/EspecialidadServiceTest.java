package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(EspecialidadService.class)
class EspecialidadServiceTest {
    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<MedicoEntity> medicoList = new ArrayList<>();

    private List<EspecialidadEntity> especialidadList = new ArrayList<>();

    /**
     * Configuración inicial de la prueba.
     */
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MedicoEntity");
        entityManager.getEntityManager().createQuery("delete from EspecialidadEntity");
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
            entityManager.persist(especialidadEntity);
            especialidadList.add(especialidadEntity);
        }

        MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
        entityManager.persist(medicoEntity);
        medicoEntity.getEspecialidades().add(especialidadList.get(0));
        especialidadList.get(0).getMedicos().add(medicoEntity);
    }

    /**
     * Prueba para crear una especialidad.
     */
    @Test
    void testCreateEspecialidad() throws IllegalOperationException {
        EspecialidadEntity newEspecialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
        newEspecialidadEntity.setDescripcion("0123456789");
        EspecialidadEntity result = especialidadService.createEspecialidad(newEspecialidadEntity);
        assertNotNull(result);
        EspecialidadEntity entity = entityManager.find(EspecialidadEntity.class, result.getId());
        assertNotNull(entity);
        assertEquals(newEspecialidadEntity.getId(), entity.getId());
        assertEquals(newEspecialidadEntity.getNombre(), entity.getNombre());
        assertEquals(newEspecialidadEntity.getDescripcion(), entity.getDescripcion());
        assertEquals(newEspecialidadEntity.getMedicos(), entity.getMedicos());
    }

    /**
     * Prueba para crear una especialidad con descripcion menor a 10 caracteres.
     */
    @Test
    void testCreateInvalidEspecialidad() throws IllegalOperationException {
        assertThrows(IllegalOperationException.class, ()->{
            EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);
            newEntity.setDescripcion("sopa");
            especialidadService.createEspecialidad(newEntity);
        });
    }
}
