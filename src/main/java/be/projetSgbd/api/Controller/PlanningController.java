package be.projetSgbd.api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import be.projetSgbd.api.BLL.Models.Employe;
import be.projetSgbd.api.BLL.Models.PatientInfoForAccueillante;
import be.projetSgbd.api.BLL.Models.Planning;
import be.projetSgbd.api.BLL.Models.PlanningInsert;
import be.projetSgbd.api.BLL.Services.PlanningService;
import be.projetSgbd.api.DAL.Models.PlanningEntity;
import be.projetSgbd.api.Exception.DateException;
import be.projetSgbd.api.Exception.VaccinException;

@RestController
public class PlanningController {

	@Autowired
	private PlanningService planningService;
	
	@GetMapping("/planning")
	public ResponseEntity<Iterable<Planning>> GetPatient(){
		try {
			return new ResponseEntity<Iterable<Planning>>(planningService.GetPlannings(),HttpStatus.OK);			
		}catch(Exception ex) {
			return new ResponseEntity("Error",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/planning/{id}")
	public ResponseEntity<Planning> GetEmployeById(@PathVariable("id") final long id) {
		try {
			return new ResponseEntity<Planning>(planningService.GetPlanning(id),HttpStatus.OK);			
		}catch(Exception ex) {
			return new ResponseEntity("Error",HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	@PostMapping("/planning")
	public ResponseEntity<Planning> createPatient(@RequestBody PlanningInsert planning){
		try {
			Planning e = planningService.SavePlanning(planning);
			return new ResponseEntity<Planning>(e,HttpStatus.OK);
		}catch(DateException ex) {
			return new ResponseEntity(ex.getMessage(),HttpStatus.BAD_REQUEST);
		}
		catch(VaccinException ex) {
			return new ResponseEntity(ex.getMessage(),HttpStatus.BAD_REQUEST);
		}catch(Exception ex) {
			return new ResponseEntity(ex.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/planning/{id}")
	public ResponseEntity<Planning> updatePatient(@PathVariable("id") final long id,@RequestBody Planning planning){
		try {
			Planning e = planningService.GetPlanning(id);
			if(planning.getDates()!=null) {
				e.setDates(planning.getDates());
			}
			if(planning.getHeure()!=null) {
				e.setHeure(planning.getHeure());
			}
			if(planning.getId()!=0) {
				e.setId(planning.getId());
			}
			if(planning.getId_centre()!=0) {
				e.setId_centre(planning.getId_centre());
			}
			if(planning.getId_vaccin()!=0) {
				e.setId_vaccin(planning.getId_vaccin());
			}
			return new ResponseEntity<Planning>(planningService.UpdatePlanning(e),HttpStatus.OK);			
		}catch(DateException ex) {
			return new ResponseEntity(ex.getMessage(),HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity("Error",HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@DeleteMapping("/planning/{id}")
	public ResponseEntity<Void> DeletePatient(@PathVariable("id") final Long id) {
		try {
			planningService.DeletePlanning(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/planning/lot")
	public ResponseEntity<Integer> UpdateLot(@RequestBody PlanningInsert p){
		try {
			return new ResponseEntity<Integer>(planningService.UpdateLotVaccin(p),HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);

		}
	}
	
	
	@GetMapping("/planning/AcceuillantEntrant/{id}")
	public ResponseEntity<Iterable<PatientInfoForAccueillante>> GetPlanningForAcceuillantEntre(@PathVariable("id") final long id){
		try {
			return new ResponseEntity<Iterable<PatientInfoForAccueillante>>(planningService.GetPlanningForAcceuillantEntrant(id),HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);

		}
	}
	
	@GetMapping("/planning/Acceuillantsortie/{id}")
	public ResponseEntity<Iterable<PatientInfoForAccueillante>> GetPlanningForAcceuillantsortie(@PathVariable("id") final long id){
		try {
			return new ResponseEntity<Iterable<PatientInfoForAccueillante>>(planningService.GetPlanningForAcceuillantsortie(id),HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);

		}
	}
	
	
	
	

}
