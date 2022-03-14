package be.projetSgbd.api.BLL.Services;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import be.projetSgbd.api.BLL.Interface.IPlanningService;
import be.projetSgbd.api.BLL.Models.Centre;
import be.projetSgbd.api.BLL.Models.Patient;
import be.projetSgbd.api.BLL.Models.PatientInfoForAccueillante;
import be.projetSgbd.api.BLL.Models.Planning;
import be.projetSgbd.api.BLL.Models.PlanningInsert;
import be.projetSgbd.api.DAL.Models.CentreEntity;
import be.projetSgbd.api.DAL.Models.IPlanningEntity;
import be.projetSgbd.api.DAL.Models.PatientEntity;
import be.projetSgbd.api.DAL.Models.PlanningEntity;
import be.projetSgbd.api.DAL.Models.VaccinEntity;
import be.projetSgbd.api.DAL.Repositories.CentreRepository;
import be.projetSgbd.api.DAL.Repositories.IPlanningRepository;
import be.projetSgbd.api.DAL.Repositories.PatientRepository;
import be.projetSgbd.api.DAL.Repositories.PlanningRepository;
import be.projetSgbd.api.DAL.Repositories.VaccinRepository;
import be.projetSgbd.api.Exception.DateException;
import be.projetSgbd.api.Exception.NumNatExeception;
import be.projetSgbd.api.Exception.VaccinException;
import be.projetSgbd.api.utilitaire.Utilitaire;
import lombok.Data;
import net.bytebuddy.implementation.bytecode.Throw;

import static java.util.stream.Collectors.toList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;


@Data
@Service
public class PlanningService implements IPlanningService {

	@Autowired
	private PlanningRepository planningRepository;
	
	@Autowired
	private IPlanningRepository iPlanningRepository;
	
	@Autowired
	private CentreRepository centreRepository;

	@Autowired
	private VaccinRepository vaccinRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	
	
	
	@Override
	public Iterable<Planning> GetPlannings() {
		Iterable<PlanningEntity> planing = planningRepository.findAll();
		List<PlanningEntity> c = new ArrayList<PlanningEntity>();
		for (PlanningEntity planningEntity : planing) {
			c.add(planningEntity);
		}
		return ToModelList(c);
	}

	@Override
	public Planning GetPlanning(long id) {
		Optional<PlanningEntity> p = planningRepository.findById(id);
		return ToBLL(p.get());
	}

	@Override
	public void DeletePlanning(long id) {
		planningRepository.deleteById(id);
	}

	
	//check par rapport au vaccin si il a prix trop de redenez vous
	@Override
	public Planning SavePlanning(PlanningInsert p) throws DateException,VaccinException, ParseException, NumNatExeception{
		Planning planning = new Planning();
		planning.setDates(p.getDates());
		planning.setHeure(p.getHeure());
		planning.setId_centre(p.getId_centre());
		checkPlanning(planning);
		
		
		checkVaccinInsert(p);
		
		Planning x=new Planning();
		x.setDates(p.getDates());
		x.setHeure(p.getHeure());
		x.setId_centre(p.getId_centre());
		x.setId_vaccin(p.getId_vaccin());
		Planning save= ToBLL(planningRepository.save(ToDAL(x)));
		System.out.println("ok");
		IPlanningEntity ip = new IPlanningEntity();
		ip.setId_patient(p.getId_patient());
		ip.setId_planning(save.getId());
		iPlanningRepository.save(ip);
		return save;
	}

	@Override
	public Planning UpdatePlanning(Planning planning) throws DateException {
		checkPlanning(planning);
		Planning save= ToBLL(planningRepository.save(ToDAL(planning)));
		return save;
	}
	
	@Override
	public int UpdateLotVaccin(PlanningInsert p) {
		int x =planningRepository.updateLotVaccin(p.getLot_Vaccin(),p.getId_centre(),p.getHeure(),p.getDates());		
		return x;
	}
	
	
	
	public List<PatientInfoForAccueillante> GetPlanningForAcceuillantEntrant(long id_centre) {
		
	       DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	       Date date = new Date();
	       System.out.println(format.format(date));
	       return peuplePatientForAcceuillant(planningRepository.findForAcceuillanteEntre(id_centre,format.format(date)));
	}

	public List<PatientInfoForAccueillante> GetPlanningForAcceuillantsortie(long id_centre) {
		
	       DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	       Date date = new Date();
	       return peuplePatientForAcceuillant(planningRepository.findForAcceuillantesortie(id_centre,format.format(date)));
	}
	
	private List<PatientInfoForAccueillante> peuplePatientForAcceuillant(Iterable<PlanningEntity> planing)
	{
		List<PatientInfoForAccueillante> c = new ArrayList<PatientInfoForAccueillante>();
		for (PlanningEntity planningEntity : planing) {
			
			PatientInfoForAccueillante p = new PatientInfoForAccueillante();
			p.setHeure(planningEntity.getHeure());
			p.setNomVaccin(vaccinRepository.findById(planningEntity.getId_vaccin()).get().getNom());
			p.setId(planningEntity.getId());
			p.setId_patient(iPlanningRepository.findById_planning(planningEntity.getId()).getId_patient());
			Optional<PatientEntity> patient= patientRepository.findById(p.getId_patient());
			p.setNom(patient.get().getNom());
			p.setPrenom(patient.get().getPrenom());
			p.setNumnat(patient.get().getNumNat());
			c.add(p);
		}
		return c;
	}
	
	
	
	
	
	
	
	public Planning  ToBLL(PlanningEntity e) {
		Planning m= new Planning();
		m.setId(e.getId());
		m.setDates(e.getDates());
		m.setHeure(e.getHeure());
		return m;
	}
	
	public PlanningEntity ToDAL(Planning e) {
		PlanningEntity m = new PlanningEntity();
		m.setId(e.getId());
		m.setDates(e.getDates());
		m.setHeure(e.getHeure());
		m.setId_centre(e.getId_centre());
		m.setId_vaccin(e.getId_vaccin());
		m.setLot_Vaccin(e.getLot_Vaccin());
		return m;
	}
	
	public List<Planning> ToModelList(List<PlanningEntity> c){
		if(Objects.isNull(c)) {
			return Collections.emptyList();
		}
		return c.stream().map(e->ToBLL(e)).collect(toList());
	}

	
	private void checkPlanning(Planning p) throws DateException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(p.getDates(), formatter);
		 DateTimeFormatter jourDate = DateTimeFormatter.ofPattern("dd");
		 DateTimeFormatter moisDate = DateTimeFormatter.ofPattern("MM");
		 DateTimeFormatter anneeDate = DateTimeFormatter.ofPattern("yyyy");
		 if(Integer.parseInt(anneeDate.format(LocalDateTime.now()))>date.getYear()) { throw new DateException("date donnée inferieur ou egal a la date actuelle");}
		 if(Integer.parseInt(anneeDate.format(LocalDateTime.now()))==date.getYear()) {
			 if(Integer.parseInt(moisDate.format(LocalDateTime.now()))>=date.getMonthValue()) {
				 if(Integer.parseInt(jourDate.format(LocalDateTime.now()))>=date.getDayOfMonth()) {
					 throw new DateException("date donnée inferieur ou egal a la date actuelle");
				 }
			 }
		 }
		 
		    LocalTime heure = LocalTime.parse(p.getHeure());
		    if(heure.getMinute()%5!=0) {
		    	throw new DateException("heure du rendez-vous invalide (1 rendez vous toute les  5 minute");
		    }
			CentreEntity centre=centreRepository.findById(p.getId_centre()).get();
			LocalTime centreHeureOpen= LocalTime.parse(centre.getHoraire_Begin());
			LocalTime centreHeureClose = LocalTime.parse(centre.getHoraire_End());
			if(heure.getHour()<centreHeureOpen.getHour()) {
				throw new DateException("heure donnée inferieur ou égal a la date d'ouverture du centre");
			}
			if(heure.getHour()==centreHeureOpen.getHour()) {
				if(heure.getMinute()<centreHeureOpen.getMinute()) {
					throw new DateException("heure donnée inferieur ou égal a la date d'ouverture du centre");					
				}
				
			}
			if(heure.getHour()>centreHeureClose.getHour()) {
				throw new DateException("heure donnée superieur ou égal a la date d'ouverture du centre");
			}
			if(heure.getHour()==centreHeureClose.getHour()) {
				if(heure.getMinute()>centreHeureClose.getMinute()) {
					throw new DateException("heure donnée superieur ou égal a la date d'ouverture du centre");					
				}
				
			}
			
			PlanningEntity rdvExits=planningRepository.findByRdv(heure.toString(),date.toString(),p.getId_centre());
			if(rdvExits!=null) {
				throw new DateException("cette plage horaire deja utilise");
			}
		 
	}
	
	
	
	public void checkVaccinInsert(PlanningInsert p) throws VaccinException, NumNatExeception, ParseException {
		Optional<VaccinEntity> v=vaccinRepository.findById(p.getId_vaccin());
		int nbRdv=planningRepository.findBynbRdv(p.getId_patient());
		if(v.get().getNbDose()<=nbRdv) {
			throw new VaccinException("Le patient possedez trop de rendez vous");
		}
		if(p.getId_vaccin()==1) {
			Optional<PatientEntity> patientInfo= patientRepository.findById(p.getId_patient());
			int annee = Integer.parseInt(patientInfo.get().getNumNat().substring(0,2));
			Utilitaire u = new Utilitaire();
			boolean flag= u.CheckNumNat2000Or1900(patientInfo.get().getNumNat());
			if(flag) {
				annee+=1900;
				System.err.println(2020-annee);
				System.err.println(2020-annee==41);
				if(2020-annee<=41) {
					throw new VaccinException("Vous etes trop jeune pour ce vaccin ");
				}
			}else {
				annee+=2000;
				throw new VaccinException("Vous etes trop jeune pour ce vaccin ");
			}
		}
		//4 parce que j&j est la position dans la db
		if(p.getId_vaccin()!=4 && nbRdv!=0) {
			PlanningEntity planningPatient = planningRepository.findByRdvByIdPatient(p.getId_patient());
			if(planningPatient.getId_vaccin() != p.getId_vaccin()) {
				throw new VaccinException("Pas le meme vaccin");
			}
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 Date dateAvant = sdf.parse(planningPatient.getDates());
	         Date dateApres = sdf.parse(p.getDates());
	         long diff = dateApres.getTime() - dateAvant.getTime();
	         float res = (diff / (1000*60*60*24));	         
			if(res<28){
				throw new VaccinException("La date de rendez vous n'est pas assez espace");
			}
			if(res>56 ) {
				throw new VaccinException("La date de rendez a trop espace");				
			}
			else if(res>42&& p.getId_vaccin()!=3) {
				throw new VaccinException("La date de rendez a trop espace");
			}
	
		}
	}
	
	
}
