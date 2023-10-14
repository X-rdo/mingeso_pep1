package cl.topEducation.mingeso_pep1.repositories;

import cl.topEducation.mingeso_pep1.entities.CuotaEntity;
import cl.topEducation.mingeso_pep1.entities.PruebaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PruebaRepository extends CrudRepository<PruebaEntity, Long> {

    List<PruebaEntity> findByRut(String rut);
}
