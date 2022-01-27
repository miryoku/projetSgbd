package be.projetSgbd.api.DAL.Models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name="Employe")
public class EmployeEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String nom;
	
	private String prenom;
	
	private String email;
	
	@OneToOne
	@JoinColumn(name="id_typeEmploye")
	private TypeEmployeEntity typeEmployeEntity;
	
	private String mdp;
}
