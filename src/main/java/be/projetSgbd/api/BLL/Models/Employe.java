package be.projetSgbd.api.BLL.Models;

import lombok.Data;

@Data
public class Employe {

	private long id;
	private String nom;
	private String prenom;
	private String email;
	private String typeEmploye;
}
