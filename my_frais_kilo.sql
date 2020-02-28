-- creation des tables

drop table if exists NOTIFICATION;
drop table if exists TRAJET;
drop table if exists VEHICULE;
drop table if exists ADRESSE;
drop table if exists UTILISATEUR;


create table UTILISATEUR(
	id_utilisateur int(11) AUTO_INCREMENT,
	nom varchar(70) not null,
	prenom varchar(70) not null,
	email varchar(70) not null,
	mdp varchar(250) not null,
	fonction varchar(15) DEFAULT 'UTILISATEUR',
constraint UK_UTILISATEUR_ID UNIQUE(id_utilisateur),
constraint UK_UTILISATEUR_MAIL UNIQUE(email),
constraint PK_UTILISATEUR_ID primary key (id_utilisateur)
)engine=INNODB;

create table ADRESSE(
id_adresse int(11) auto_INCREMENT,
id_utilisateur int(11),
numero varchar(15) not null,
rue varchar(70) not null,
code_postal varchar(5) not null,
ville varchar(70) not null,
id_trajet int (11),
constraint UK_UTILISATEUR_ID UNIQUE(id_adresse),
constraint PK_ADRESSE_ID_UTIL primary key (id_adresse),
constraint FK_ID_UTIL_ADR foreign key (id_utilisateur) references UTILISATEUR(id_utilisateur)
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
  	route varchar(30) not null,
  	id_adresse_dep INT(11) not null,
	id_adresse_arr INT(11) not null,
  	nb_km int(3) not null,
	cout int(10) not null,
	archive boolean DEFAULT false,
constraint UK_TRAJET_ID UNIQUE(id_trajet),
constraint PK_TRAJET_ID primary key (id_trajet),
constraint FK_ID_UTIL foreign key(id_utilisateur) references UTILISATEUR(id_utilisateur),
constraint FK_ID_ADD_DEP foreign key(id_adresse_dep) references ADRESSE(id_adresse),
constraint FK_ID_ADD_ARR foreign key(id_adresse_arr) references ADRESSE(id_adresse)
)engine=INNODB;


create table NOTIFICATION(
  	id_notification INT(11) NOT NULL AUTO_INCREMENT,
	id_utilisateur_destinataire int(11) not null,
	id_utilisateur_destinateur int(11) not null,
  	date_notification date,
  	commentaire varchar(255) not null,
	archive boolean DEFAULT false,
constraint UK_NOTIFICATION_ID UNIQUE(id_notification),
constraint PK_NOTIFICATION_ID primary key (id_notification),
constraint FK_ID_UTIL_DESTINATAIRE foreign key(id_utilisateur_destinataire) references UTILISATEUR(id_utilisateur),
constraint FK_ID_UTIL_DESTINATEUR foreign key(id_utilisateur_destinateur) references UTILISATEUR(id_utilisateur)
)engine=INNODB;
