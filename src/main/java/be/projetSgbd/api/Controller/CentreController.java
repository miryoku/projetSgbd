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

import be.projetSgbd.api.BLL.Models.Centre;
import be.projetSgbd.api.BLL.Models.Employe;
import be.projetSgbd.api.BLL.Models.EmployeInsert;
import be.projetSgbd.api.BLL.Services.CentreService;
import be.projetSgbd.api.Exception.ModelException;

@RestController
public class CentreController {

	@Autowired
	private CentreService centreService;
	
	@GetMapping("/centre")
	public ResponseEntity<Iterable<Centre>> GetCentres(){
		try {
			return new ResponseEntity<Iterable<Centre>>(centreService.GetCentres(),HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity("error",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/centre/{id}")
	public ResponseEntity<Centre> GetEmployeById(@PathVariable("id") final long id) {
		try {
			Centre c= centreService.GetCentre(id);
			return new ResponseEntity<Centre>(c,HttpStatus.OK);			
		}catch(Exception ex) {
			return new ResponseEntity("Error",HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/centre")
	public  ResponseEntity<Centre> createEmploye(@RequestBody Centre c) {

		try {
			Centre e=centreService.SaveCentre(c);
			return new ResponseEntity<Centre>(e,HttpStatus.OK);			
		}catch(ModelException ex) {
			return new ResponseEntity(ex.getMessage(),HttpStatus.BAD_REQUEST);
		}catch(Exception ex) {
			return new ResponseEntity("Error",HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/centre/{id}")
	public ResponseEntity<Centre> updateEmploye(@PathVariable("id") final long id,@RequestBody Centre c){
		try {
			Centre e= centreService.GetCentre(id);
			
			if(c.getAdresse()!=null) {
				e.setAdresse(c.getAdresse());
			}
			if(c.getHoraire_Begin()!= null) {
				e.setHoraire_Begin(c.getHoraire_Begin());
			}
			if(c.getHoraire_End()!=null) {
				e.setHoraire_End(c.getHoraire_End());
			}
			
			return new ResponseEntity<Centre>(centreService.UpdateCentre(e),HttpStatus.OK);			
		}catch(ModelException ex) {
			return new ResponseEntity(ex.getMessage(),HttpStatus.BAD_REQUEST);
		}catch(Exception ex) {
			return new ResponseEntity("error",HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@DeleteMapping("/centre/{id}")
	public ResponseEntity<Void> DeleteCentre(@PathVariable("id") final Long id) {
		try {
				centreService.DeleteCentre(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
