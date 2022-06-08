DROP TABLE IF EXISTS Countries;

CREATE TABLE Countries(
    id TINYINT AUTO_INCREMENT PRIMARY KEY,
    name TEXT(100) NOT NULL,
    shortname TEXT(3) NOT NULL
);

DROP TABLE IF EXISTS h3bnbbdd.User;

CREATE TABLE h3bnbbdd.User(
    id INT AUTO_INCREMENT PRIMARY KEY,
    fname TEXT(50) NOT NULL,
    lname TEXT(50) NOT NULL,
    email TEXT(50) NOT NULL,
    password TEXT(50) NOT NULL,
    phone TEXT(50) NOT NULL
);


DROP TABLE IF EXISTS h3bnbbdd.Cities;

CREATE TABLE h3bnbbdd.Cities(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name TEXT(50) NOT NULL,
    latitude TEXT(50) NOT NULL,
    longitude TEXT(50) NOT NULL,
    idPays TINYINT NOT NULL,
    FOREIGN KEY (idPays) REFERENCES Countries(id)
);

DROP TABLE IF EXISTS h3bnbbdd.Biens;

CREATE TABLE h3bnbbdd.Biens(
    id INT AUTO_INCREMENT PRIMARY KEY,
    beds TINYINT NOT NULL,
    price INT NOT NULL,
    type TEXT(50) NOT NULL,
    latitude TEXT(50) NOT NULL,
    longitude TEXT(50) NOT NULL,
    availabilities TEXT(256) NOT NULL,
    idCity INT(11) NOT NULL,
    idOwner INT(11) NOT NULL,
    FOREIGN KEY(idCity) REFERENCES h3bnbbdd.Cities(id),
    FOREIGN KEY(idOwner) REFERENCES h3bnbbdd.User(id)
);

DROP TABLE IF EXISTS h3bnbbdd.Contrats;

CREATE TABLE h3bnbbdd.Contrats(
    id INT AUTO_INCREMENT PRIMARY KEY,
    Dates TEXT(256) NOT NULL,
    chat TEXT(1000) NOT NULL,   
    idBien INT(11) NOT NULL,
    idUser INT(11) NOT NULL,
    FOREIGN KEY(idBien) REFERENCES h3bnbbdd.Biens(id),
    FOREIGN KEY(idUser) REFERENCES h3bnbbdd.User(id)
);