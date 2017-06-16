INSERT INTO role (name, description) VALUES 
("operator", "operator"),
("user", "user");

INSERT INTO user (username, password, id_role) VALUES 
("operator1", "123", 1),
("user1", "123", 2);

INSERT INTO profile (fullname, id_user) VALUES 
("Operator 1", 1),
("User 1", 2);

INSERT INTO event (title, description, lat, lng, reference_url, id_user) VALUES
("Kebakaran", "Telah terjadi kebarakan bla bla bla", "-6.249019232564748", "106.90832376480103", "http://localhost/phpmyadmin", 1), 
("Banjir", "Telah terjadi banjir bla bla bla", "-6.249328520010227", "106.90892457962036", "http://localhost/phpmyadmin", 1); 