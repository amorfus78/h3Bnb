DROP TABLE IF EXISTS h3bnbbdd.Contrats;

CREATE TABLE h3bnbbdd.Contrats(
    id INT AUTO_INCREMENT PRIMARY KEY,
    dates TEXT(256) NOT NULL,
    chat mediumtext NOT NULL,   
    idBien INT(11) NOT NULL,
    idUser INT(11) NOT NULL,
    FOREIGN KEY(idBien) REFERENCES h3bnbbdd.Biens(id),
    FOREIGN KEY(idUser) REFERENCES h3bnbbdd.User(id)
);