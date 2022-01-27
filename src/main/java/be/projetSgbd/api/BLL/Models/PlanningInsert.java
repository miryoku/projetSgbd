package be.projetSgbd.api.BLL.Models;

import lombok.Data;

@Data
public class PlanningInsert {

	private long id;
	
	private String dates;
	
	private String heure;
	
	private String lot_Vaccin;
	
	private long id_centre;
	
	private long id_vaccin;
	
	private long id_patient;
}
