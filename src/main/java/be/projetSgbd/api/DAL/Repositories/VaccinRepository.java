package be.projetSgbd.api.DAL.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import be.projetSgbd.api.DAL.Models.VaccinEntity;

public interface VaccinRepository extends CrudRepository<VaccinEntity, Long>{

	

}
