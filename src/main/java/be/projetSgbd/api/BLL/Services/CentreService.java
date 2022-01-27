package be.projetSgbd.api.BLL.Services;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.projetSgbd.api.BLL.Interface.ICentreService;
import be.projetSgbd.api.BLL.Models.Centre;
import be.projetSgbd.api.DAL.Models.CentreEntity;
import be.projetSgbd.api.DAL.Repositories.CentreRepository;
import be.projetSgbd.api.Exception.ModelException;
import lombok.Data;
import static java.util.stream.Collectors.toList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


@Data
@Service
public class CentreService implements ICentreService {
	
	@Autowired
	private CentreRepository centreRepository;
	
	@Override
	public Centre GetCentre(long id) {
		Optional<CentreEntity> centre = centreRepository.findById(id);		
		return ToBLL(centre.get());
	}

	@Override
	public Iterable<Centre> GetCentres() {
		Iterable<CentreEntity> centres = centreRepository.findAll();
		List<CentreEntity> c = new ArrayList<CentreEntity>();
		for (CentreEntity centreEntity : centres) {
			c.add(centreEntity);
		}
		return ToModelList(c);
	}

	@Override
	public void DeleteCentre(long id) {
		centreRepository.deleteById(id);
	}

	@Override
	public Centre SaveCentre(Centre centre) throws ParseException,ModelException {
		CheckHeure(centre.getHoraire_Begin(),centre.getHoraire_End());
		CentreEntity saveCentre = centreRepository.save(ToDAL(centre));
		return ToBLL(saveCentre);
	}

	@Override
	public Centre UpdateCentre(Centre centre) throws ParseException,ModelException{
		CheckHeure(centre.getHoraire_Begin(),centre.getHoraire_End());
		CentreEntity saveCentre = centreRepository.save(ToDAL(centre));
		return ToBLL(saveCentre);
	}
	
	public Centre ToBLL(CentreEntity c) {
		Centre m= new Centre();
		m.setId(c.getId());
		m.setAdresse(c.getAdresse());
		m.setHoraire_Begin(c.getHoraire_Begin());
		m.setHoraire_End(c.getHoraire_End());
		return m;
	}
	
	public List<Centre> ToModelList(List<CentreEntity> centre){
		if(Objects.isNull(centre)) {
			return Collections.emptyList();
		}
		return centre.stream().map(e->ToBLL(e)).collect(toList());
	}
	
	public CentreEntity ToDAL(Centre e) {
		CentreEntity m = new CentreEntity();
		m.setId(e.getId());
		m.setAdresse(e.getAdresse());
		m.setHoraire_Begin(e.getHoraire_Begin());
		m.setHoraire_End(e.getHoraire_End());
		return m;
	}
	
	private void CheckHeure(String dateB,String dateF) throws ModelException, ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date dateBegin = new Date(sdf.parse(dateB).getTime());
		Date dateEnd = new Date(sdf.parse(dateF).getTime());
		
		System.out.println(dateBegin.compareTo(dateEnd));
		if(dateBegin.compareTo(dateEnd) >= 0) {
			throw new ModelException("La date de debut et superieur ou egale");
		}
		
	}

}
