package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

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
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(MedicoService.class)
class MedicoServiceTest {

    @Autowired
    private MedicoService medicoService;

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
            MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
            entityManager.persist(medicoEntity);
            medicoList.add(medicoEntity);
        }

        MedicoEntity medicoEntity = medicoList.get(2);
        EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
        especialidadEntity.getMedicos().add(medicoEntity);
        entityManager.persist(especialidadEntity);

        medicoEntity.getEspecialidades().add(especialidadEntity);
    }

    /**
     * Prueba para crear un medico
     */
    @Test
    void testCreateMedico() throws IllegalOperationException {
        MedicoEntity newMedicoEntity = factory.manufacturePojo(MedicoEntity.class);
        newMedicoEntity.setRegitroMedico("RM123");
        MedicoEntity result = medicoService.createMedico(newMedicoEntity);
        assertNotNull(result);
        MedicoEntity entity = entityManager.find(MedicoEntity.class, result.getId());
        assertNotNull(entity);
        assertEquals(newMedicoEntity.getId(), entity.getId());
        assertEquals(newMedicoEntity.getNombre(), entity.getNombre());
        assertEquals(newMedicoEntity.getApellido(), entity.getApellido());
        assertEquals(newMedicoEntity.getEspecialidades(), entity.getEspecialidades());
    }

    /**
     * Prueba para crear un medico con registro medico invalido
     */
    @Test
    void testCreateInvalidMedico() throws IllegalOperationException {
        assertThrows(IllegalOperationException.class, ()->{
            MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
            newEntity.setRegitroMedico("1234");
            medicoService.createMedico(newEntity);
        });
    }
}
