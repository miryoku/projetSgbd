package be.projetSgbd.api.BLL.Services;

import org.springframework.stereotype.Service;

import be.projetSgbd.api.BLL.Interface.IEmployeService;
import be.projetSgbd.api.BLL.Models.Employe;
import be.projetSgbd.api.BLL.Models.EmployeInsert;
import be.projetSgbd.api.BLL.Models.IEmploye;
import be.projetSgbd.api.DAL.Models.EmployeEntity;
import be.projetSgbd.api.DAL.Models.IEmployeEntity;
import be.projetSgbd.api.DAL.Models.TypeEmployeEntity;
import be.projetSgbd.api.DAL.Repositories.EmployeRepository;
import be.projetSgbd.api.DAL.Repositories.IEmployeRepository;
import be.projetSgbd.api.Exception.EmailException;
import be.projetSgbd.api.Exception.MdpException;
import be.projetSgbd.api.Exception.ModelException;
import be.projetSgbd.api.utilitaire.Utilitaire;
import lombok.Data;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@Data
@Service
public class EmployeService implements IEmployeService{

	private Utilitaire u = new Utilitaire();
	
	@Autowired
	private EmployeRepository employeRepository;
	
	@Autowired
	private IEmployeRepository iEmployeRepository;
	
	

	public Employe GetEmploye(final long id){
		Optional<EmployeEntity> eIO = employeRepository.findById(id);
		return ToBLL(eIO.get());
	}
	
	public Iterable<Employe> GetEmployes(){
		Iterable<EmployeEntity> eI=employeRepository.findAll();
		List<EmployeEntity> e = new ArrayList<EmployeEntity>();
		for (EmployeEntity employeEntity : eI) {
			e.add(employeEntity);
		}
		return ToModelList(e);
	}
	
	public void DeleteEmploye(final long id) {
		employeRepository.deleteById(id);
	}
	
	public Employe SaveEmploye(EmployeInsert e) throws EmailException,MdpException,ModelException{
		  
			char[] charSearch1 = {' '};
	        char[] charSearch2 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};		
	        if (u.stringVoid(e.getNom())) {
	            throw new ModelException("le nom est vide");
	        }
	        if (u.checkString(e.getNom(), charSearch2)) {
	            throw new ModelException("le nom possede des nombre");

	        }
	        if (u.checkSpace(e.getNom(), charSearch1)) {
	            throw new ModelException("le nom est juste un espace");
	        }
	        if (u.stringVoid(e.getPrenom())) {
	            throw new ModelException("le prenom est vide");
	        }
	        if (u.checkString(e.getPrenom(), charSearch2)) {
	            throw new ModelException("le prenom possede des nombre");
	        }
	        if (u.checkSpace(e.getPrenom(), charSearch1)) {
	            throw new ModelException("le prenom est juste un espace");
	        }
	        u.CheckEmail(e.getEmail());
	        u.CheckMdp(e.getMdp());
		EmployeEntity saveEmploye = employeRepository.save(ToDAL(e));
		IEmploye ie = new IEmploye();
		ie.setId_Centre(e.getId_Centre());
		ie.setId_Employe(saveEmploye.getId());
		iEmployeRepository.save(ToDAL(ie));
		return ToBLL(saveEmploye);
	}
	
	
	public Employe UpdateEmploye(EmployeInsert employe) throws EmailException,MdpException,ModelException{
		u.CheckEmail(employe.getEmail());
        u.CheckMdp(employe.getMdp());
        employeRepository.update(employe.getEmail(),employe.getMdp(),employe.getId());
		return ToBLL(employeRepository.findById(employe.getId()).get());	
	}
	
	public  Employe ToBLL(EmployeEntity e) {
		Employe m = new Employe();
		m.setId(e.getId());
		m.setNom(e.getNom());
		m.setPrenom(e.getPrenom());
		m.setEmail(e.getEmail());
		m.setTypeEmploye(e.getTypeEmployeEntity().getNomEmploye());
		return m;
	}
	
	
	
	public List<Employe> ToModelList(List<EmployeEntity> employe){
		if(Objects.isNull(employe)) {
			return Collections.emptyList();
		}
		return employe.stream().map(e-> ToBLL(e)).collect(toList());
	}
	
	public EmployeEntity ToDAL(EmployeInsert e) {
		
		EmployeEntity m = new EmployeEntity();
		m.setId(e.getId());
		m.setNom(e.getNom());
		m.setPrenom(e.getPrenom());
		m.setEmail(e.getEmail());
		m.setMdp(e.getMdp());
		TypeEmployeEntity t  = new TypeEmployeEntity();
		t.setId(e.getId_typeEmploye());
     	m.setTypeEmployeEntity(t);
		return m;
	}
	
	public IEmployeEntity ToDAL(IEmploye i) {
		
		IEmployeEntity e = new IEmployeEntity();
		e.setId(i.getId());
		e.setId_Centre(i.getId_Centre());
		e.setId_Employe(i.getId_Employe());
		return e;
	}
	
	
}
