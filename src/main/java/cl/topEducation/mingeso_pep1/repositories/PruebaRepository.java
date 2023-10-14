package cl.topEducation.mingeso_pep1.repositories;

import cl.topEducation.mingeso_pep1.entities.PruebaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PruebaRepository extends CrudRepository<PruebaEntity, String> {
}
