package be.projetSgbd.api.DAL.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="Planning")
public class PlanningEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String dates;
	
	private String heure;
	
	private String lot_Vaccin;
	
	private long id_centre;
	
	private long id_vaccin;

}


