package be.projetSgbd.api.DAL.Repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import be.projetSgbd.api.BLL.Models.Planning;
import be.projetSgbd.api.DAL.Models.PlanningEntity;

public interface PlanningRepository extends CrudRepository<PlanningEntity, Long>{

	@Query(value ="Select * from planning where heure=:heure and dates=:date and id_centre=:id ", nativeQuery = true)
	public PlanningEntity findByRdv(@Param("heure") final String heure,@Param("date")final String date,@Param("id")final long id_centre);


	@Modifying
	@Query(value="update planning set lot_vaccin = :lot where id_centre = :id_centre and heure like :heure and dates like :date",nativeQuery = true)
	@Transactional
	public int updateLotVaccin(@Param("lot") String lot, @Param("id_centre") long id_centre,@Param("heure") String heure,@Param("date") String date);
	
	
	@Query(value ="select count(*) from planning p, IPlanning i where p.id=i.id_planning and i.id_patient=:id_patient ", nativeQuery = true)
	public int findBynbRdv(@Param("id_patient")final long id_patient);
	
	@Query(value ="select * from planning p, IPlanning i where p.id=i.id_planning and i.id_patient=:id_patient", nativeQuery = true)
	public PlanningEntity findByRdvByIdPatient(@Param("id_patient")final long id_patient);

	@Query(value ="select * from planning where id_centre=:id and dates=:date and lot_vaccin is null", nativeQuery = true)
	public Iterable<PlanningEntity> findForAcceuillanteEntre(@Param("id") long id,@Param("date") String date);
	
	
	
	@Query(value ="select * from planning where id_centre=:id and dates=:date and lot_vaccin ='0'", nativeQuery = true)
	public Iterable<PlanningEntity> findForAcceuillantesortie(@Param("id") long id,@Param("date") String date);
}
