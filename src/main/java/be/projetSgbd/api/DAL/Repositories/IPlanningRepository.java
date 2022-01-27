package be.projetSgbd.api.DAL.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import be.projetSgbd.api.DAL.Models.IPlanningEntity;
import be.projetSgbd.api.DAL.Models.PlanningEntity;

public interface IPlanningRepository extends CrudRepository<IPlanningEntity, Long> {

	@Query(value ="Select * from iplanning where id_planning=:id_planning ", nativeQuery = true)
	public IPlanningEntity findById_planning(@Param("id_planning") final long id_planning);

}
