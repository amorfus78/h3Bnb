DROP TABLE IF EXISTS h3bnbbdd.Biens;

CREATE TABLE h3bnbbdd.Biens(
    id INT AUTO_INCREMENT PRIMARY KEY,
    beds TINYINT NOT NULL,
    price INT NOT NULL,
    type TEXT(50) NOT NULL,
    latitude TEXT(50) NOT NULL,
    longitude TEXT(50) NOT NULL,
    availabilities TEXT(256) NOT NULL,
    description TEXT(256) NOT NULL,
    idCity INT(11) NOT NULL,
    idOwner INT(11) NOT NULL,
    images mediumtext NOT NULL,
    FOREIGN KEY(idCity) REFERENCES h3bnbbdd.Cities(id),
    FOREIGN KEY(idOwner) REFERENCES h3bnbbdd.User(id)
);