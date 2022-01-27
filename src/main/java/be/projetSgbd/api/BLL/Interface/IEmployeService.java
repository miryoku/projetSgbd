package be.projetSgbd.api.BLL.Interface;

import java.util.Optional;

import be.projetSgbd.api.BLL.Models.Employe;
import be.projetSgbd.api.BLL.Models.EmployeInsert;
import be.projetSgbd.api.Exception.EmailException;
import be.projetSgbd.api.Exception.MdpException;
import be.projetSgbd.api.Exception.ModelException;
import be.projetSgbd.api.Exception.NumNatExeception;



public interface IEmployeService {

	public Employe GetEmploye(final long id);
	public Iterable<Employe> GetEmployes();
	public void DeleteEmploye(final long id);
	public Employe SaveEmploye(EmployeInsert e) throws  EmailException,MdpException,ModelException;;
	public Employe UpdateEmploye(EmployeInsert employe) throws EmailException,MdpException,ModelException;;
	
}
