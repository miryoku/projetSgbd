package be.projetSgbd.api.BLL.Models;

import lombok.Data;

@Data
public class PatientInfoForAccueillante {
	private long id;
	
	private String heure;
	
	private String numnat;
	
	private String nom;
	
	private String prenom;
	
	private String nomVaccin;
	
	private long id_patient;
	
	
}
