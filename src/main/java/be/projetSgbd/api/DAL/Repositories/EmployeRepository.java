package be.projetSgbd.api.DAL.Repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import be.projetSgbd.api.DAL.Models.EmployeEntity;



public interface EmployeRepository  extends CrudRepository<EmployeEntity, Long>{

	
	@Modifying
	@Query(value="update employe set email = :email, mdp=:mdp where id=:id",nativeQuery = true)
	@Transactional
	public void	 update(@Param("email") String email,@Param("mdp") String mdp,@Param("id") long id);
	
	
}
