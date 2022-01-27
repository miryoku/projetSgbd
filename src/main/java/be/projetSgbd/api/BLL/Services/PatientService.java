package be.projetSgbd.api.BLL.Services;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.projetSgbd.api.BLL.Interface.IPatientService;
import be.projetSgbd.api.BLL.Models.Patient;
import be.projetSgbd.api.DAL.Models.CentreEntity;
import be.projetSgbd.api.DAL.Models.PatientEntity;
import be.projetSgbd.api.DAL.Repositories.CentreRepository;
import be.projetSgbd.api.DAL.Repositories.PatientRepository;
import be.projetSgbd.api.Exception.EmailException;
import be.projetSgbd.api.Exception.MdpException;
import be.projetSgbd.api.Exception.ModelException;
import be.projetSgbd.api.Exception.NumNatExeception;
import be.projetSgbd.api.utilitaire.Utilitaire;
import lombok.Data;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;

@Data
@Service
public class PatientService implements IPatientService  {

	private Utilitaire u= new Utilitaire();
	
	@Autowired
	private PatientRepository patientRepository;	
	
	@Override
	public Patient GetPatient(long id) {
		Optional<PatientEntity> p = patientRepository.findById(id);	
		
		return ToBLL(p.get());
	}

	@Override
	public Iterable<Patient> GetPatientByCenter(long id) {
		Iterable<PatientEntity> patients = patientRepository.findByCentre(id);
		List<PatientEntity> c = new ArrayList<PatientEntity>();
		for (PatientEntity patient : patients) {
			c.add(patient);
		}
		return ToModelList(c);

	}

	@Override
	public Iterable<Patient> GetPatients() {
		Iterable<PatientEntity> patients = patientRepository.findAll();
		List<PatientEntity> c = new ArrayList<PatientEntity>();
		for (PatientEntity patient : patients) {
			c.add(patient);
		}
		return ToModelList(c);	}

	@Override
	public void DeletePatients(long id) {
		patientRepository.deleteById(id);
		
	}

	@Override
	public Patient SavePatient(Patient patient) throws NumNatExeception,ModelException {
		
			u.CheckNumNat(patient.getNumNat());
	        char[] charSearch1 = {' '};
	        char[] charSearch2 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};		
	        if (u.stringVoid(patient.getNom())) {
	            throw new ModelException("le nom est vide");
	        }
	        if (u.checkString(patient.getNom(), charSearch2)) {
	            throw new ModelException("le nom possede des nombre");
	        }
	        if (u.checkSpace(patient.getNom(), charSearch1)) {
	            throw new ModelException("le nom est juste un espace");
	        }
	      
	        if (u.stringVoid(patient.getPrenom())) {
	            throw new ModelException("le prenom est vide");
	        }
	        if (u.checkString(patient.getPrenom(), charSearch2)) {
	            throw new ModelException("le prenom possede des nombre");
	        }
	        if (u.checkSpace(patient.getPrenom(), charSearch1)) {
	            throw new ModelException("le prenom est juste un espace");
	        }	
	        if (u.stringVoid(patient.getCodeSecret())) {
	            throw new ModelException("le code secret est vide");
	        }
	        String numNat =patientRepository.findByNumNat(patient.getNumNat());
	        if(numNat!=null) {
	        	throw new ModelException("Le numero national Existe deja");
	        }
	        
	        
	    patientRepository.InsertP(patient.getNom(),patient.getPrenom(),patient.getNumNat(),patient.getCodeSecret());
		PatientEntity save =new PatientEntity();
		save.setNom(patient.getNom());
		save.setPrenom(patient.getPrenom());
		save.setNumNat(patient.getNumNat());
		return ToBLL(save);
	}

	@Override
	public Patient UpdatePatient(Patient patient) throws EmailException,MdpException,ModelException{
		u.CheckEmail(patient.getEmail());
		u.CheckMdp(patient.getMdp());
		
		if(patientRepository.findById(patient.getId()).isEmpty()) {
			throw new ModelException("Patient n'existe pas");
		}
		patientRepository.updateP(patient.getEmail(),patient.getMdp(),patient.getId());
		return patient;
	}
	
	public Patient CreateAccount(Patient p) throws EmailException,NumNatExeception,MdpException {
		
		
		u.CheckNumNat(p.getNumNat());
		u.CheckEmail(p.getEmail());
		u.CheckMdp(p.getMdp());
		patientRepository.validInscirption(p.getNumNat(),p.getCodeSecret(),p.getEmail(),p.getMdp());
		return p;
	}
	
	public Patient ToBLL(PatientEntity c) {
		Patient m=new Patient();
		m.setDescriptions_symptome(c.getDescriptions_symptome());
		m.setEmail(c.getEmail());
		m.setId(c.getId());
		m.setNom(c.getNom());
		m.setNumNat(c.getNumNat());
		m.setPrenom(c.getPrenom());
		return m;	
	}
	
	public Patient ToBLL2(PatientEntity c) {
		Patient m=new Patient();
		m.setId(c.getId());
		m.setNom(c.getNom());
		m.setNumNat(c.getNumNat());
		m.setPrenom(c.getPrenom());
		return m;	
	}
	
	
	
	public List<Patient> ToModelList(List<PatientEntity> c){
		if(Objects.isNull(c)) {
			return Collections.emptyList();
		}
		return c.stream().map(e->ToBLL2(e)).collect(toList());
	}
	
	public PatientEntity ToDAL(Patient e) {
		PatientEntity m = new PatientEntity();
		m.setDescriptions_symptome(e.getDescriptions_symptome());
		m.setEmail(e.getEmail());
		m.setId(e.getId());
		m.setNom(e.getNom());
		m.setPrenom(e.getPrenom());
		m.setNumNat(e.getNumNat());
		return m;
	}
	
	
}
