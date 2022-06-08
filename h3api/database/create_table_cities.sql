DROP TABLE IF EXISTS h3bnbbdd.Cities;

CREATE TABLE h3bnbbdd.Cities(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name TEXT(50) NOT NULL,
    latitude TEXT(50) NOT NULL,
    longitude TEXT(50) NOT NULL,
    idPays TINYINT NOT NULL,
    FOREIGN KEY (idPays) REFERENCES Countries(id)
);