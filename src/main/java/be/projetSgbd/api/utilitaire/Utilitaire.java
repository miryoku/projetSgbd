package be.projetSgbd.api.utilitaire;

import be.projetSgbd.api.Exception.EmailException;
import be.projetSgbd.api.Exception.MdpException;
import be.projetSgbd.api.Exception.NumNatExeception;

public class Utilitaire {

	public boolean checkString(String check, char array[]) {
        for (int i = 0; i < check.length(); i++) {
            char chr = check.charAt(i);

            for (int j = 0; j < array.length; j++) {
                if (array[j] == chr) {
                    return true;
                }
            }
        }
        return false;
    }
	
	public boolean checkDay(int day, int dayCheck) {

        if (dayCheck < day) {
            return true;
        }

        return false;
    }
	
	public boolean checkJourDays(int jour, int mois, int annee) {

	        switch (mois) {

	            case 1:
	                return checkDay(jour, 31);
	            case 2:
	                return checkDay(jour, bissextile(annee));
	            case 3:
	                return checkDay(jour, 31);
	            case 4:
	                return checkDay(jour, 30);
	            case 5:
	                return checkDay(jour, 31);
	            case 6:
	                return checkDay(jour, 30);
	            case 7:
	                return checkDay(jour, 31);
	            case 8:
	                return checkDay(jour, 31);
	            case 9:
	                return checkDay(jour, 30);
	            case 10:
	                return checkDay(jour, 31);
	            case 11:
	                return checkDay(jour, 30);
	            case 12:
	                return checkDay(jour, 31);
	        }

	        return true;
	    }
	 
	public int bissextile(int annee) {
	        int divisible4 = annee % 4;
	        int divisible100 = annee % 100;
	        int divisible400 = annee % 400;
	        int jour;
	        if (divisible400 == 0) {
	            jour = 29;
	        } else if (divisible100 == 0) {
	            jour = 28;
	        } else if (divisible4 == 0) {
	            jour = 29;
	        } else {
	            jour = 28;
	        }

	        return jour;
	    }
	   
	   public static boolean stringVoid(String check) {
	        if (check.length() == 0) {
	            return true;
	        }
	        return false;
	    }

	  

	   public boolean checkSpace(String check, char array[]) {
	        int k = 0;
	        for (int i = 0; i < check.length(); i++) {
	            char chr = check.charAt(i);

	            for (int j = 0; j < array.length; j++) {
	                if (array[j] == chr) {
	                    k++;
	                }
	            }
	        }
	        if (k == check.length()) {
	            return true;
	        }

	        return false;
	    }
	    
	   public void CheckEmail(String email) throws EmailException{
	    	if(email.indexOf('@')==-1) {
				 throw new EmailException("L'email ne contient pas @");
			}
			if(email.chars().filter(ch -> ch == '@').count()>=2){
				throw new EmailException("L'email contient plusieur @");
			}
			if(email.indexOf('.')==-1) {
				 throw new EmailException("L'email ne contient pas .");
			}
			if(email.chars().filter(ch -> ch == '.').count()>=2){
				throw new EmailException("L'email contient plusieur .");
			}
			if(email.indexOf('@')==0) {
				throw new EmailException("Le premeir caractere est un @");
			}
			if(email.indexOf('.')==0) {
				throw new EmailException("Le premeir caractere est un .");
			}
			if(email.indexOf('.')<=email.indexOf('@')) {
				throw new EmailException("le  .  est avant @");
			}
			if((email.indexOf('@')+1)==email.indexOf('.')) {
				throw new EmailException("il n'a rien entre @ et .");
			}
	    }
	    
	   public void CheckNumNat(String numNat) throws NumNatExeception{
			 if (numNat.length() < 11) {
		            throw new NumNatExeception("numNat moins de 11 caractères");
		        }
		        if (numNat.length() > 11) {
		            throw new NumNatExeception("numNat plus de 11 caractères");
		        }
		        char[] charSearch = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		        if (checkString(numNat, charSearch)) {
		            throw new NumNatExeception("numNat a des caractere dans la chaine");
		        }
		        int mois = Integer.parseInt(numNat.charAt(2) + "" + numNat.charAt(3));
		        if (mois > 12) {
		            throw new NumNatExeception("numNat le caractere 3 et 4 son superieur a 12");
		        }
		        int jour = Integer.parseInt(numNat.charAt(4) + "" + numNat.charAt(5));
		        int annee = Integer.parseInt(numNat.substring(0, 2));
		        boolean flag = checkJourDays(jour, mois, annee);
		        if (flag) {
		            throw new NumNatExeception("numNat le caractere 5 et 6 son superieur");
		        }
		        long numNat0A9 = Integer.parseInt(numNat.substring(0, 9));
		        int numNat10A11 = Integer.parseInt(numNat.substring(9, 11));
		        flag = false;
		        if (numNat0A9 % 97 == 97 - numNat10A11) {
		            flag = true;
		        } else if ((2000000000 + numNat0A9) % 97 == 97 - numNat10A11) {
		            flag = true;
		        }
		        if (!flag) {
		            throw new NumNatExeception("numNat le caractere 10 et 11 n'est pas un nombre valide");
		        }
	    }
	   
	   public boolean CheckNumNat2000Or1900(String numNat) throws NumNatExeception{
		   	boolean millinuim=false;
		   
			 if (numNat.length() < 11) {
		            throw new NumNatExeception("numNat moins de 11 caractères");
		        }
		        if (numNat.length() > 11) {
		            throw new NumNatExeception("numNat plus de 11 caractères");
		        }
		        char[] charSearch = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		        if (checkString(numNat, charSearch)) {
		            throw new NumNatExeception("numNat a des caractere dans la chaine");
		        }
		        int mois = Integer.parseInt(numNat.charAt(2) + "" + numNat.charAt(3));
		        if (mois > 12) {
		            throw new NumNatExeception("numNat le caractere 3 et 4 son superieur a 12");
		        }
		        int jour = Integer.parseInt(numNat.charAt(4) + "" + numNat.charAt(5));
		        int annee = Integer.parseInt(numNat.substring(0, 2));
		        boolean flag = checkJourDays(jour, mois, annee);
		        if (flag) {
		            throw new NumNatExeception("numNat le caractere 5 et 6 son superieur");
		        }
		        long numNat0A9 = Integer.parseInt(numNat.substring(0, 9));
		        int numNat10A11 = Integer.parseInt(numNat.substring(9, 11));
		        flag = false;
		        if (numNat0A9 % 97 == 97 - numNat10A11) {
		            flag = true;
		            millinuim=true;
		        } else if ((2000000000 + numNat0A9) % 97 == 97 - numNat10A11) {
		            flag = true;
		            millinuim=false;
		        }
		        if (!flag) {
		            throw new NumNatExeception("numNat le caractere 10 et 11 n'est pas un nombre valide");
		        }
		        return millinuim;
	    }
	   
	   
	   

	   public void CheckMdp(String mdp) throws MdpException {
	    	if(mdp.length()<8) {
				throw new MdpException("le mdp est inferieur a 8 caractere");
			}
	    }
	    
	    
	   
	   
	   
	   
}
