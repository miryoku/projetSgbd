package be.projetSgbd.api.BLL.Interface;

import java.text.ParseException;
import java.util.Optional;


import be.projetSgbd.api.BLL.Models.Planning;
import be.projetSgbd.api.BLL.Models.PlanningInsert;
import be.projetSgbd.api.Exception.DateException;
import be.projetSgbd.api.Exception.NumNatExeception;
import be.projetSgbd.api.Exception.VaccinException;



public interface IPlanningService {

	public Iterable<Planning> GetPlannings();
	public Planning GetPlanning(final long id);
	public void DeletePlanning(final long id);
	public Planning SavePlanning(PlanningInsert p) throws DateException,VaccinException,ParseException,NumNatExeception;
	public Planning UpdatePlanning(Planning planning)throws DateException;
	public int UpdateLotVaccin(PlanningInsert p);
}
