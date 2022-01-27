package be.projetSgbd.api.DAL.Repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import be.projetSgbd.api.BLL.Models.Patient;
import be.projetSgbd.api.DAL.Models.PatientEntity;

public interface PatientRepository extends CrudRepository<PatientEntity,Long> {

	@Query(value ="Select * from Patient where id_centre=:id ", nativeQuery = true)
	public List<PatientEntity> findByCentre(@Param("id") final Long id);

	
	@Modifying
	@Query(value="update patient set email = :email, mdp=:mdp where id = :id",nativeQuery = true)
	@Transactional
	public void updateP(@Param("email") String email,@Param("mdp") String mdp,@Param("id") long id);
	
	@Modifying
	@Query(value="insert into patient(nom,prenom,numNat,codeSecret) values(:nom,:prenom,:numNat,:codeSecret)",nativeQuery = true)
	@Transactional
	public void InsertP(@Param("nom") String nom,@Param("prenom") String prenom,@Param("numNat") String numNat,@Param("codeSecret")String codeSecret);
	
	@Query(value="Select numNat from Patient where NumNat=:nN",nativeQuery=true)
	public String findByNumNat(@Param("nN")String numNat);
	
	
	@Modifying
	@Query(value="update patient set email = :email, mdp=:mdp where numNat=:numNat and codeSecret=:codeSecret",nativeQuery = true)
	@Transactional
	public void validInscirption(@Param("numNat") String numNat,@Param("codeSecret") String codeSecret,@Param("email") String email,@Param("mdp") String mdp);
	
	
	
}
