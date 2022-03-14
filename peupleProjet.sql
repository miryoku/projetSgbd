use projetSgbd


insert into vaccins(nbDose,nom,condition) values(2,'Astrazeneca','Uniquement pour personne de plus de 41 ans au premier Janvier 2020, 4 à 
	6 semaines entre les doses'),
	(2,'Pfizer','4 à 6 semaines entre les doses'),
	(2,'Moderna','4 à 8 semaines entre les doses'),
	(1,'Johnson & Johnson','')
insert into centre(horaire_begin,horaire_end,adresse) values('9:00','18:00','rue des charlattant n1, la louviere')

insert into planning(dates,heure,id_vaccin,lot_Vaccin,id_centre) values (getdate(),'11:50',1,'1',1),
	(getdate(),'10:50',2,'1',1),
	(getdate(),'12:50',3,'1',1),
	(getdate(),'10:45',4,'1',1)
insert into TypeEmploye(nomEmploye)values('administrateur général'),('administrateur'),('operateur sortie'),('operateur entre'),('infirmiere'),('medecin')
insert into employe(nom,prenom,email,mdp,id_typeEmploye) values('adminG','chef Supreme','PetitDictateur@gmail.be','admin',1),
	('admin1','petit chef1','admin1@gmail.be','admin',2),
	('admin2','petit chef2','admin2@gmail.be','admin',2),
	('opS1','ops1','ops1@gmail.be','admin',3),
	('opS2','ops2','ops2@gmail.be','admin',3),
	('opE1','ope1','ope1@gmail.be','admin',4),
	('opE2','ope2','ope1@gmail.be','admin',4),
	('inf1','inf1','inf1@gmail.be','admin',5),
	('inf2','inf2','inf2@gmail.be','admin',5),
	('med1','med1','med1@gmail.be','admin',6)

insert into IEmploye(id_employe,id_centre) values(2,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1)

insert into patient(nom,prenom,numnat,codeSecret,mdp,email,id_planning,id_centre,descriptions_symptome)values ('p1','p1','0000000000000','secret','mdp','p1@p1.be',1,1,'confulsion'),
	('p2','p2','0000000000','secret','mdp','p2@p2.be',2,1,'saignez du nez')
	insert into patient(nom,prenom,numnat,codeSecret,mdp,email,id_planning,id_centre)values 
		('p3','p3','0000000000','secret','mdp','p3@p3.be',3,1)

insert into IPlanning(id_planning,id_patient) values(1,1),
	(2,2),
	(3,3)

