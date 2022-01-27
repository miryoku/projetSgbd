package be.projetSgbd.api.BLL.Models;

import lombok.Data;

@Data
public class Patient {
	private long id;
	
	private String nom;
	
	private String prenom;
	
	private String  numNat;
	
	private String email;
	
	private String descriptions_symptome;
	
	private String mdp;
	
	private String codeSecret;
}
