package be.projetSgbd.api.BLL.Interface;

import java.text.ParseException;
import java.util.Optional;

import be.projetSgbd.api.BLL.Models.Centre;
import be.projetSgbd.api.Exception.ModelException;


public interface ICentreService {

	public Centre GetCentre(final long id);
	
	public Iterable<Centre> GetCentres();
	
	public void DeleteCentre(final long id);
	
	public Centre SaveCentre(Centre centre)throws ParseException,ModelException;
	
	public Centre UpdateCentre(Centre centre)throws ParseException,ModelException ;


}
