DROP TABLE IF EXISTS h3bnbbdd.User;

CREATE TABLE h3bnbbdd.User(
    id INT AUTO_INCREMENT PRIMARY KEY,
    fname TEXT(50) NOT NULL,
    lname TEXT(50) NOT NULL,
    email TEXT(50) NOT NULL,
    password TEXT(50) NOT NULL,
    phone TEXT(50) NOT NULL
);