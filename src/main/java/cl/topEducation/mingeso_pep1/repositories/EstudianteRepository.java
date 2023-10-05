package cl.topEducation.mingeso_pep1.repositories;

import cl.topEducation.mingeso_pep1.entities.EstudianteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends CrudRepository<EstudianteEntity, String> {
}
