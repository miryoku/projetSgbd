package be.projetSgbd.api.DAL.Repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import be.projetSgbd.api.DAL.Models.IEmployeEntity;

public interface IEmployeRepository extends CrudRepository<IEmployeEntity, Long>{

	@Modifying
	@Query(value="update IEmploye set id_centre=:newValue where id_employe= :id",nativeQuery = true)
	@Transactional
	public void update(@Param("newValue") long id_centre, @Param("id") long id_employe);
}
