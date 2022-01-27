package be.projetSgbd.api.BLL.Interface;

import java.util.Optional;

import be.projetSgbd.api.BLL.Models.Patient;
import be.projetSgbd.api.Exception.EmailException;
import be.projetSgbd.api.Exception.MdpException;
import be.projetSgbd.api.Exception.ModelException;
import be.projetSgbd.api.Exception.NumNatExeception;

public interface IPatientService {
	
	public Patient GetPatient(final long id);
	
	public Iterable<Patient> GetPatientByCenter(final long id);
	
	public Iterable<Patient> GetPatients();
	
	public void DeletePatients(final long id);
	
	public Patient SavePatient(Patient patient) throws NumNatExeception,ModelException;
	public Patient UpdatePatient(Patient patient) throws EmailException,MdpException,ModelException;

	public Patient CreateAccount(Patient p) throws EmailException,NumNatExeception,MdpException;
}


