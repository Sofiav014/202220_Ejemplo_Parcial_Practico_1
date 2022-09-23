package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoEspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    public EspecialidadEntity addEspecialidad (Long especialidadId, Long medicoId) throws IllegalOperationException, EntityNotFoundException {
        Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);
        Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadId);

        if (medicoEntity.isEmpty())
            throw new EntityNotFoundException("El medico con el id dado no fue encontrado");

        if (especialidadEntity.isEmpty())
            throw new EntityNotFoundException("La especialidad con el id dado no fue encontrada");

        medicoEntity.get().getEspecialidades().add(especialidadEntity.get());

        return especialidadEntity.get();
    }

}
