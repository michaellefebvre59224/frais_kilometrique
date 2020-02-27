-- creation des tables
drop table if exists TRAJET;
drop table if exists VEHICULE;
drop table if exists UTILISATEUR;

create table UTILISATEUR(
	id_utilisateur int(11) AUTO_INCREMENT,
	nom varchar(70) not null,
	prenom varchar(70) not null,
	email varchar(70) not null,
	mdp varchar(250) not null,
	fonction varchar(15) DEFAULT 'UTILISATEUR',
	adresse VARCHAR(250),
constraint UK_UTILISATEUR_ID UNIQUE(id_utilisateur),
constraint UK_UTILISATEUR_MAIL UNIQUE(email),
constraint PK_UTILISATEUR_ID primary key (id_utilisateur)
)engine=INNODB;

create table VEHICULE(
  	id_vehicule INT(11) NOT NULL AUTO_INCREMENT,
  	marque  varchar(40) not null,
  	modele varchar(40) not null,
  	puissance int(2) not null,
  	immat varchar(10) not null,
	id_utilisateur int(11) not null,
constraint UK_VEHICULE_ID UNIQUE(id_vehicule),
constraint PK_VEHICULE_ID primary key (id_vehicule),
constraint FK_ID_UTIL_V foreign key(id_utilisateur) references UTILISATEUR(id_utilisateur)
)engine=INNODB;

create table TRAJET(
  	id_trajet INT(11) NOT NULL AUTO_INCREMENT,
	id_utilisateur int(11) not null,
  	date_trajet date,
  	route ENUM('ALLER','RETOUR','ALLER_RETOUR') not null,
  	adr_depart VARCHAR(100) not null,
	adr_arrive VARCHAR(100) not null,
  	nb_km int(3) not null,
	cout int(10) not null,
	archive boolean DEFAULT false,
constraint UK_TRAJET_ID UNIQUE(id_trajet),
constraint PK_TRAJET_ID primary key (id_trajet),
constraint FK_ID_UTIL foreign key(id_utilisateur) references UTILISATEUR(id_utilisateur)
)engine=INNODB;
