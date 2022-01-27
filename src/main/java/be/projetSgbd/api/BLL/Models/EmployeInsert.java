package be.projetSgbd.api.BLL.Models;

import lombok.Data;

@Data
public class EmployeInsert {
	private long id;
	private String nom;
	private String prenom;
	private String email;
	private long Id_typeEmploye;
	private long id_Centre;
	private String mdp;

}
