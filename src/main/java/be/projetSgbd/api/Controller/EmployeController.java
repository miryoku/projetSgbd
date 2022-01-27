package be.projetSgbd.api.Controller;

import java.util.List;
import java.util.Optional;

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
import be.projetSgbd.api.BLL.Models.EmployeInsert;
import be.projetSgbd.api.BLL.Models.IEmploye;
import be.projetSgbd.api.BLL.Services.EmployeService;
import be.projetSgbd.api.Exception.EmailException;
import be.projetSgbd.api.Exception.MdpException;
import be.projetSgbd.api.Exception.ModelException;

@RestController
public class EmployeController {

	@Autowired
	private EmployeService employeService;
	@GetMapping("/employe")
	public ResponseEntity<Iterable<Employe>> GetEmploye(){
		try {
			return new ResponseEntity<Iterable<Employe>>(employeService.GetEmployes(),HttpStatus.OK);			
		}catch(Exception ex) {
			return new ResponseEntity("Error",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/employe/{id}")
	public ResponseEntity<Employe> GetEmployeById(@PathVariable("id") final long id) {
		try {
			Employe employe= employeService.GetEmploye(id);
			return new ResponseEntity<Employe>(employe,HttpStatus.OK);			
		}catch(Exception ex) {
			return new ResponseEntity("Error",HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/employe")
	public  ResponseEntity<Employe> createEmploye(@RequestBody EmployeInsert employe) {

		try {
			Employe e=employeService.SaveEmploye(employe);
			return new ResponseEntity<Employe>(e,HttpStatus.OK);			
		}catch(EmailException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}catch(MdpException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}catch(ModelException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/employe/{id}")
	public ResponseEntity<Employe> updateEmploye(@PathVariable("id") final long id,@RequestBody EmployeInsert employe){
		try {		
			return new ResponseEntity<Employe>(employeService.UpdateEmploye(employe),HttpStatus.OK);			
		}catch(EmailException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}catch(MdpException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}catch(ModelException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}	catch(Exception ex) {
			return new ResponseEntity("error",HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@DeleteMapping("/employe/{id}")
	public ResponseEntity<Void> DeleteCentre(@PathVariable("id") final Long id) {
		try {
				employeService.DeleteEmploye(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
}
