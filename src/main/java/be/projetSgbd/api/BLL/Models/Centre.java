package be.projetSgbd.api.BLL.Models;

import lombok.Data;

@Data
public class Centre {

	private long id;
	
	private String horaire_Begin;
	
	private String  horaire_End;
	
	private String adresse;
}
