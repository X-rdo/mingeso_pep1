package cl.topEducation.mingeso_pep1.repositories;

import cl.topEducation.mingeso_pep1.entities.CuotaEntity;
import cl.topEducation.mingeso_pep1.entities.EstudianteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuotaRepository extends CrudRepository<CuotaEntity, Long> {

    boolean existsByRut(String rut);
    List<CuotaEntity> findByRut(String rut);
}
