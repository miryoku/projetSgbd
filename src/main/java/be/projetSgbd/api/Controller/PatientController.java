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

import be.projetSgbd.api.BLL.Models.Patient;
import be.projetSgbd.api.BLL.Services.PatientService;
import be.projetSgbd.api.Exception.EmailException;
import be.projetSgbd.api.Exception.MdpException;
import be.projetSgbd.api.Exception.ModelException;
import be.projetSgbd.api.Exception.NumNatExeception;

@RestController
public class PatientController {
 @Autowired
 private PatientService patientService;
 
	@GetMapping("/patient")
	public ResponseEntity<Iterable<Patient>> GetPatient(){
		try {
			return new ResponseEntity<Iterable<Patient>>(patientService.GetPatients(),HttpStatus.OK);			
		}catch(Exception ex) {
			return new ResponseEntity("Error",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/patient/{id}")
	public ResponseEntity<Patient> GetEmployeById(@PathVariable("id") final long id) {
		try {
			Patient patient= patientService.GetPatient(id);
			return new ResponseEntity<Patient>(patient,HttpStatus.OK);			
		}catch(Exception ex) {
			return new ResponseEntity("Error",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/patient/center/{id}")
	public ResponseEntity<Iterable<Patient>> GetPatientByCenter2(@PathVariable("id") final Long id) {
		try {
			Iterable<Patient> patient= patientService.GetPatientByCenter(id);
			return new ResponseEntity<Iterable<Patient>>(patient,HttpStatus.OK);			
		}catch(Exception ex) {
			return new ResponseEntity("Error",HttpStatus.BAD_REQUEST);
		}	
	}
	
	
	@PostMapping("/patient")
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patient){
		
		try {
			Patient e = patientService.SavePatient(patient);
			return new ResponseEntity<Patient>(e,HttpStatus.OK);
		}catch(NumNatExeception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}catch(ModelException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}catch(Exception ex) {
			return new ResponseEntity("Error",HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/patient/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable("id") final long id,@RequestBody Patient patient){
		try {			
			return new ResponseEntity<Patient>(patientService.UpdatePatient(patient),HttpStatus.OK);			
		}catch(EmailException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}catch(MdpException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}catch(ModelException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@DeleteMapping("/patient/{id}")
	public ResponseEntity<Void> DeletePatient(@PathVariable("id") final Long id) {
		try {
				patientService.DeletePatients(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/patient/validAccount")
	public ResponseEntity<Patient> ValidAccount(@RequestBody Patient p ){
		try {			
			return new ResponseEntity<Patient>(patientService.CreateAccount(p),HttpStatus.OK);			
		}catch(EmailException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}catch(MdpException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}catch(NumNatExeception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
