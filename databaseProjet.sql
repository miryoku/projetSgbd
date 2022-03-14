/*create database projetSgbd;

*/
use projetSgbd;

drop table IPlanning
drop table Patient
drop Table IEmploye
drop table planning
drop table centre
drop table employe
drop table horaire
drop table TypeEmploye
drop table Vaccins





create table TypeEmploye(
	id int identity(1,1) primary key,
	nomEmploye varchar(50)
);

create table Horaire(
	id int identity(1,1) primary key
);

create table Employe(
	id int identity(1,1) primary key,
	nom varchar(50),
	prenom varchar(50),
	email varchar(50),
	mdp varchar(50),
	id_typeEmploye int,
	id_horaire int,
	constraint FK_Employe_TypeEmploye foreign key(id_typeEmploye)
		references TypeEmploye(id),
	constraint FK_Employe_horaire foreign key(id_horaire)
		references Horaire(id)
);



create table Vaccins(
	id int identity(1,1) primary key,
	nbDose int,
	nom varchar(50),
	condition text
);


create table Centre(
	id int identity(1,1) primary key,
	Horaire_Begin time,
	Horaire_End time,
	adresse varchar(150)
	
);



create table planning(
	id int identity(1,1) primary key,
	dates date,
	heure time,
	id_Vaccin int,
	id_centre int,
	lot_Vaccin varchar(50),
	constraint FK_planning_vaccin foreign key (id_vaccin)
		references Vaccins(id),
	constraint FK_Centre_planning foreign key (id_centre)
		references centre(id)
);

create table IEmploye(
	id int identity(1,1) primary key,
	id_Centre int,
	id_Employe int,
	constraint FK_IEmploye_Centre foreign key(id_centre)
		references Centre(id) on delete cascade,
	constraint FK_IEmploye_Employe foreign key(id_Employe)
		references Employe(id)
);

create table Patient(
	id int identity(1,1) primary key,
	nom varchar(50),
	prenom varchar(50),
	NumNat varchar(13),
	codeSecret varchar(50),
	mdp varchar(50),
	email varchar(50),
	id_planning int,
	id_centre int,
	descriptions_symptome varchar(250),
	constraint FK_patient_Centre foreign key(id_centre) 
		references centre(id) on delete cascade
);

create table IPlanning(
	id int identity(1,1) primary key,
	id_planning int,
	id_patient int,
	constraint FK_IPlanning_Planning foreign key (id_planning)
		references planning(id) on delete cascade,
	constraint FK_IPlannin_patient foreign key (id_patient)
		references patient(id) on delete cascade
);
