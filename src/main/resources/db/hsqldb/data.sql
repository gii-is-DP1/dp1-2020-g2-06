-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');
-- More Owners
INSERT INTO users(username,password,enabled) VALUES ('jesapaort','passjesus',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'jesapaort','owner');

INSERT INTO users(username,password,enabled)VALUES('juanrauvu', 'passjuanra', TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'juanrauvu','owner');

INSERT INTO users(username,password,enabled)VALUES('alebarled', 'passalebarled', TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'alebarled','owner');

INSERT INTO users(username,password,enabled)VALUES('davbrican', 'jarvispass', TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'davbrican','owner');

INSERT INTO users(username,password,enabled) VALUES ('vicgragil','elp3p3',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'vicgragil','owner');

INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

INSERT INTO owners VALUES (11, 'Jesus', 'Aparicio', '41008 Arroyo', 'Sevilla', '601070925', 'jesapaort');
INSERT INTO owners VALUES (12, 'Alejandro', 'Barranco', '666 Reina Mercedes', 'Sevilla', '639058229', 'alebarled');
INSERT INTO owners VALUES (13, 'David', 'Brincau', '41016 Vedra', 'Sevilla', '678623620', 'davbrican');
INSERT INTO owners VALUES (14, 'Juan Ramón', 'Ostos', '25 Sepulveda', 'Sevilla', '636363663', 'juanrauvu');
INSERT INTO owners VALUES (15, 'Victor', 'Granero', 'Monza 56', 'Montequinto', '684017682', 'vicgragil');


INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (14, 'Luffy', '2012-01-01', 2, 11);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (15, 'Rarmon', '2000-09-11', 2, 12);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (16, 'Jarvis', '2018-08-13', 1, 13);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (17, 'BadJesus', '1990-08-30', 4, 14);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (18, 'Garfild', '2018-08-10', 1, 15);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');


INSERT INTO tutores(id,nombre,apellidos,email,pass,foto) VALUES (0,'Alejandro','Barranco Ledesma','alebarled@alum.us.es','r4rm0n','https://estaticos.elperiodico.com/resources/jpg/6/4/img-8878-1585501756946.jpg');
INSERT INTO tutores(id,nombre,apellidos,email,pass,foto) VALUES (1,'Juan Ramón','Ostos Rubio','juaostrub@alum.us.es','admin','https://png.pngtree.com/png-vector/20191110/ourlarge/pngtree-avatar-icon-profile-icon-member-login-vector-isolated-png-image_1978396.jpg');
INSERT INTO tutores(id,nombre,apellidos,email,pass,foto) VALUES (2,'David','Brincau Cano','davbrincan@alum.us.es','1234','https://yt3.ggpht.com/ytc/AAUvwnh3yE2BgOZmepJht3T-k6LRzPih9KwV4mbk1g4dYg=s88-c-k-c0x00ffffff-no-rj');
INSERT INTO tutores(id,nombre,apellidos,email,pass,foto) VALUES (3,'Jesús','Aparicio Ortiz','jesaport@alum.us.es','j3sus','https://televisa.brightspotcdn.com/dims4/default/6f0ceca/2147483647/strip/true/crop/1920x1079+0+0/resize/820x461!/quality/90/?url=https%3A%2F%2Fvideoassets.televisa.com%2F500981%2Fsnapshot1604531045644.jpg');

INSERT INTO noticias(id,name,autor_id,fecha_publicacion,texto,imagen) VALUES (0,'Noticias Frescas',0,DATE'2020-07-22','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum','https://upload.wikimedia.org/wikipedia/commons/4/44/Queso_fresco.JPG');
INSERT INTO noticias(id,name,autor_id,fecha_publicacion,texto,imagen) VALUES (1,'Noticias Falsas',0,DATE'2020-07-22','Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?','https://www.lavanguardia.com/r/GODO/LV/p7/WebSite/2020/11/22/Recortada/db4e945e797942e393f4272850199c09-k0U-U49610153696bB-992x558@LaVanguardia-Web.jpg');

INSERT INTO normas_web(id,name,descripcion) VALUES (0,'Respeta', 'Se debe respetar a todos los alumnos que participen en la página');

INSERT INTO normas_web(id,name,descripcion) VALUES (1,'Bien', 'Se deben tratar bien a los demás alumnos');
INSERT INTO normas_web(id,name,descripcion) VALUES (2,'Disfruta', 'Lo importante es pasarselo bien');

INSERT INTO problema(id,name,puntuacion,temporada,dificultad,descripcion,casos_prueba,salida_esperada,imagen,zip) VALUES (0,'La moneda de Paco', 2 , 'INVIERNO' , 'BAJA' , 'Debe encontrar la moneda falsa', '0 1 2' , '1','https://estaticos.elperiodico.com/resources/jpg/2/2/invierno-paisaje-1576018050722.jpg','pruebazip');
INSERT INTO problema(id,name,puntuacion,temporada,dificultad,descripcion,casos_prueba,salida_esperada,imagen,zip) VALUES (1,'La fuga de Rarmon', 5 , 'VERANO' , 'MEDIA' , 'Debe encontrar la manera de escapar de FuentePalmera', 'Derecha' , 'Izquierda','https://estaticos.elperiodico.com/resources/jpg/2/2/invierno-paisaje-1576018050722.jpg','pruebazip');

INSERT INTO articulos(id,name,autor_id,fecha_publicacion,texto) VALUES (0,'Articulo sobre DBGames',0,DATE'2020-07-22','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum');
INSERT INTO articulos(id,name,autor_id,fecha_publicacion,texto) VALUES (1,'No creerás este artículo',0,DATE'2020-07-22','Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?');
INSERT INTO articulos(id,name,autor_id,fecha_publicacion,texto) VALUES (2,'No creerás este artículo 2',0,DATE'2020-07-22','Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?');

INSERT INTO alumnos(id,nombre,apellidos,email,imagen,puntos_anual,puntos_temporada,puntos_totales,pass) VALUES (0,'Daniel','Montes','rarmon@alum.us.es','https://www.superprof.co/imagenes/anuncios/profesor-home-estudiante-universidad-pedagogica-nacional-licenciatura-matematicas-refuerzos-algebra-calculo-polinomios-ecuaciones.jpg',100,12,224,'octavel0ver');
INSERT INTO alumnos(id,nombre,apellidos,email,imagen,puntos_anual,puntos_temporada,puntos_totales,pass) VALUES (1,'Marina','Moya','marmozam@alum.us.es','https://pbs.twimg.com/profile_images/833645500003934208/A2AQpWnM_400x400.jpg',240,12,524,'coding4food');
INSERT INTO alumnos(id,nombre,apellidos,email,imagen,puntos_anual,puntos_temporada,puntos_totales,pass) VALUES (2,'Alexis','Balboa','alexisbalbo@alum.us.es','https://pbs.twimg.com/profile_images/2751359125/fde5c9fa3b796a33ed394c63a0c49af3.jpeg',0,0,0,'nothing2lose');


INSERT INTO creadores(id,nombre,apellidos,email,pass,foto) VALUES (0,'David','Brincau Cano','davbrican@us.es','dbgames55','https://yt3.ggpht.com/ytc/AAUvwnh3yE2BgOZmepJht3T-k6LRzPih9KwV4mbk1g4dYg=s88-c-k-c0x00ffffff-no-rj');


