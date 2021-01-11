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



INSERT INTO tutores(id,enabled,nombre,apellidos,email,pass,imagen) VALUES (0,true,'Alejandro','Barranco Ledesma','alebarled@alum.us.es','r4rm0nAAAaf1sf@','resources/images/tutores/20201223174110190000000.jpg');
INSERT INTO auths(id,id_alumno,id_tutor,id_creador,authority) VALUES (4,null,0,null,'tutor');
INSERT INTO tutores(id,enabled,nombre,apellidos,email,pass,imagen) VALUES (1,true,'Juan Ramón','Ostos Rubio','juaostrub@alum.us.es','adminaklsf@gEf1','resources/images/tutores/20201223174011976000000.jpg');
INSERT INTO auths(id,id_alumno,id_tutor,id_creador,authority) VALUES (5,null,1,null,'tutor');
INSERT INTO tutores(id,enabled,nombre,apellidos,email,pass,imagen) VALUES (2,true,'David','Brincau Cano','davbrincan@alum.us.es','1234Haksdjfab@','resources/images/tutores/2020122317244979000000.jpg');
INSERT INTO auths(id,id_alumno,id_tutor,id_creador,authority) VALUES (6,null,2,null,'tutor');
INSERT INTO tutores(id,enabled,nombre,apellidos,email,pass,imagen) VALUES (3,true,'Jesús','Aparicio Ortiz','jesaport@alum.us.es','j3s u1saskdgfD','resources/images/tutores/20201223173921376000000.jpg');
INSERT INTO auths(id,id_alumno,id_tutor,id_creador,authority) VALUES (7,null,3,null,'tutor');

INSERT INTO noticias(id,name,autor_id,fecha_publicacion,texto,imagen) VALUES (0,'Noticias Frescas',0,DATE'2020-07-22','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum','resources/images/noticias/2020122317827911000000.jpg');
INSERT INTO noticias(id,name,autor_id,fecha_publicacion,texto,imagen) VALUES (1,'Noticias Falsas',0,DATE'2020-07-22','Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?','resources/images/noticias/2020122317841918000000.jpg');

INSERT INTO normas_web(id,name,descripcion) VALUES (0,'Respeta', 'Se debe respetar a todos los alumnos que participen en la página');
INSERT INTO normas_web(id,name,descripcion) VALUES (1,'Bien', 'Se deben tratar bien a los demás alumnos');
INSERT INTO normas_web(id,name,descripcion) VALUES (2,'Disfruta', 'Lo importante es pasarselo bien');


INSERT INTO temporada(id,nombre) VALUES (0,'PRIMAVERA');
INSERT INTO temporada(id,nombre) VALUES (1,'VERANO');
INSERT INTO temporada(id,nombre) VALUES (2,'OTOÑO');
INSERT INTO temporada(id,nombre) VALUES (3,'INVIERNO');


INSERT INTO problema(id,id_judge,name,dificultad,puntuacion,descripcion,casos_prueba,salida_esperada,fecha_publicacion,id_season,season_year,imagen) VALUES (0,1,'¡Hola mundo!', 5 , 2 , '
Debes escribir el programa más básico; solo debería mostrar "Hello World!" en un
línea única, no importa cuál sea la entrada.', '1' , 'Hello World!','2000-04-30',2,2020,'resources/images/problemas/2020122317127570000000.jpg');
INSERT INTO problema(id,id_judge,name,dificultad,puntuacion,descripcion,casos_prueba,salida_esperada,fecha_publicacion,id_season,season_year,imagen) VALUES (1,2,'Comparación Flotante', 6, 5 , 'Este programa prueba el script de comparación de coma flotante especial que acompaña a DOMjudge.
La entrada del problema consiste primero en una línea con un solo entero, luego en tantas líneas,
con en cada línea un número de coma flotante (posiblemente también ± inf o nan). Por cada flotante
número de punto, se debe escribir una línea que contenga el recíproco del número, dentro
Precisión 10-6.', '8</br>
+0</br>
1.0</br>
2E0</br>
3</br>
4.0000000000000</br>
5.0000000000001</br>
Inf</br>
nan' , 'inf</br>
1.0</br>
0.50000000001</br>
3.333333333E-1</br>
0.25</br>
2E-1</br>
0</br>
NaN
','2020-11-28',1,2020,'resources/images/problemas/20201223171314927000000.jpg');
INSERT INTO problema(id,id_judge,name,dificultad,puntuacion,descripcion,casos_prueba,salida_esperada,fecha_publicacion,id_season,season_year,imagen) VALUES (2,3,'Boolfind', 3, 2 , 'Tenemos un array muy largo de valores booleanos y se sabe que el primer valor es verdadero.
y el último valor falso. Se le pide que busque una posición en la matriz donde el valor verdadero es reemplazado por falso.
</br>La entrada es la siguiente:</br>
La primera línea contiene un entero: el número de casos de prueba.
Luego sigue para cada caso de prueba un número 2 ≤ n ≤ 109.</br>
Después de eso, la entrada depende de forma interactiva de la salida de su programa. Cuando usted
generar una sola línea "READ x" con x un número entero entre 0 y n - 1, una línea que contiene
se devolverá verdadero o falso, especificando el valor en la posición x en la matriz.
Cuando genera una sola línea "OUTPUT x", esto se toma como la respuesta de la posición
que tiene valor verdadero y va seguido de falso y se dará el siguiente caso de prueba.', '1
5
</br>1 1 0 1 0
</br>false
true' , 'READ 2
</br>READ 1
</br>OUTPUT 1
','2000-04-30',2,2020,'resources/images/problemas/20201223171314927000000.jpg');
INSERT INTO problema(id,id_judge,name,dificultad,puntuacion,descripcion,casos_prueba,salida_esperada,fecha_publicacion,id_season,season_year,imagen) VALUES (3,3,'La fuga de Rarmon v.2', 3, 5 , 'Debe encontrar la manera de escapar de FuentePalmera', '0 1 2' , '1','2000-04-30',3,2020,'resources/images/problemas/2020122317810299000000.jpg');
INSERT INTO problema(id,id_judge,name,dificultad,puntuacion,descripcion,casos_prueba,salida_esperada,fecha_publicacion,id_season,season_year,imagen) VALUES (4,3,'Hello World!', 1 , 1 , 'Imprimir n veces "Hello World!"', 'Hello World! \nHello World! \nHello World!' , '1','2000-04-30',2,2019,'resources/images/problemas/2020122317827911000000.jpg');


INSERT INTO articulos(id,name,fecha_publicacion,texto,imagen_articulo) VALUES (0,'Articulo sobre DBGames',DATE'2020-07-22','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', 'resources/images/articulos/2020122317810299000000.jpg');
INSERT INTO articulos(id,name,fecha_publicacion,texto,imagen_articulo) VALUES (1,'No creerás este artículo',DATE'2020-07-22','Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?','resources/images/articulos/2020122317827911000000.jpg');
INSERT INTO articulos(id,name,fecha_publicacion,texto,imagen_articulo) VALUES (2,'No creerás este artículo 2',DATE'2020-07-22','Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?','resources/images/articulos/2020122317841918000000.jpg');
INSERT INTO articulos(id,name,fecha_publicacion,texto,imagen_articulo) VALUES (3,'Articulo sobre JesusApa',DATE'2020-07-22','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', 'resources/images/articulos/2020122317127570000000.jpg');
INSERT INTO articulos(id,name,fecha_publicacion,texto,imagen_articulo) VALUES (4,'No creerás este artículo sobre la programación',DATE'2020-07-22','Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?','resources/images/articulos/20201223171314927000000.jpg');
INSERT INTO articulos(id,name,fecha_publicacion,texto,imagen_articulo) VALUES (5,'No creerás este artículo frio',DATE'2020-07-22','Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?','resources/images/articulos/20201223171351187000000.jpg');

INSERT INTO NOTICIAS_AUTORES(NOTICIA_ID,AUTORES_ID) VALUES (0,1);
INSERT INTO NOTICIAS_AUTORES(NOTICIA_ID,AUTORES_ID) VALUES (0,2);
INSERT INTO NOTICIAS_AUTORES(NOTICIA_ID,AUTORES_ID) VALUES (0,3);
INSERT INTO NOTICIAS_AUTORES(NOTICIA_ID,AUTORES_ID) VALUES (1,1);
INSERT INTO NOTICIAS_AUTORES(NOTICIA_ID,AUTORES_ID) VALUES (1,3);



INSERT INTO ARTICULOS_AUTORES(ARTICULO_ID,AUTORES_ID) VALUES (0,1);
INSERT INTO ARTICULOS_AUTORES(ARTICULO_ID,AUTORES_ID) VALUES (0,2);
INSERT INTO ARTICULOS_AUTORES(ARTICULO_ID,AUTORES_ID) VALUES (0,3);
INSERT INTO ARTICULOS_AUTORES(ARTICULO_ID,AUTORES_ID) VALUES (1,1);
INSERT INTO ARTICULOS_AUTORES(ARTICULO_ID,AUTORES_ID) VALUES (2,2);
INSERT INTO ARTICULOS_AUTORES(ARTICULO_ID,AUTORES_ID) VALUES (0,0);
INSERT INTO ARTICULOS_AUTORES(ARTICULO_ID,AUTORES_ID) VALUES (1,0);
INSERT INTO ARTICULOS_AUTORES(ARTICULO_ID,AUTORES_ID) VALUES (2,0);
INSERT INTO ARTICULOS_AUTORES(ARTICULO_ID,AUTORES_ID) VALUES (3,0);
INSERT INTO ARTICULOS_AUTORES(ARTICULO_ID,AUTORES_ID) VALUES (4,0);
INSERT INTO ARTICULOS_AUTORES(ARTICULO_ID,AUTORES_ID) VALUES (5,0);


INSERT INTO alumnos(id,enabled,nombre,apellidos,email,imagen,pass,compartir) VALUES (0,true,'Daniel','Montes','rarmon@alum.us.es','resources/images/alumnos/20201223154714879157200.jpg','octave¬11El0ver',true);
INSERT INTO auths(id,id_alumno,id_tutor,id_creador,authority) VALUES (0,0,null,null,'alumno');

INSERT INTO alumnos(id,enabled,nombre,apellidos,email,imagen,pass,compartir) VALUES (1,true,'Marina','Moya','marmozam@alum.us.es','resources/images/alumnos/20201223155839395152900.jpg','coding@4food',false);
INSERT INTO auths(id,id_alumno,id_tutor,id_creador,authority) VALUES (1,1,null,null,'alumno');

INSERT INTO alumnos(id,enabled,nombre,apellidos,email,imagen,pass,compartir) VALUES (2,true,'Alexis','Balboa','alexisbalbo@alum.us.es','resources/images/alumnos/20201223155630996653000.jpeg','nothiWW#ng2lose',true);
INSERT INTO auths(id,id_alumno,id_tutor,id_creador,authority) VALUES (2,2,null,null,'alumno');


INSERT INTO creadores(id,enabled,nombre,apellidos,email,pass,imagen) VALUES (0,true,'David','Brincau Cano','davbrican@us.es','dbgames5DD@f5','resources/images/creadores/2020122317244979000000.jpg');
INSERT INTO auths(id,id_alumno,id_tutor,id_creador,authority) VALUES (3,null,null,0,'creador');

INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (0,0,'2020-08-21T11:13:13.274','codes/prueba.java','AC',0,1,0,2020);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (1,1,'2010-11-21T11:13:13.274','codes/prueba.c','AC',0,1,2,2010);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (2,2,'2020-11-21T11:13:13.274','codes/prueba.java','AC',0,0,2,2020);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (3,3,'2018-12-31T11:13:13.274','codes/prueba.c','AC',0,0,3,2018);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (4,4,'2018-11-21T11:13:13.274','codes/prueba.java','AC',1,1,2,2018);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (5,5,'2018-11-21T11:13:13.274','codes/prueba.c','AC',2,1,2,2018);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (6,6,'2018-11-21T11:13:13.274','codes/prueba.java','TLE',0,0,2,2018);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (7,7,'2018-11-21T11:13:13.274','codes/prueba.c','WA',0,1,2,2018);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (8,8,'2018-11-22T11:13:13.274','codes/prueba.java','TLE',0,1,2,2018);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (9,9,'2018-11-21T11:13:13.274','codes/prueba.c','AC',0,0,2,2018);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (10,10,'2019-05-22T11:13:13.274','codes/prueba.java','AC',1,3,1,2019);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (11,11,'2020-08-21T11:13:13.274','codes/prueba.java','AC',1,1,0,2020);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (12,12,'2020-08-21T11:13:13.274','codes/prueba.java','AC',1,1,0,2020);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (13,13,'2020-08-21T11:13:13.274','codes/prueba.java','AC',1,1,0,2020);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (14,14,'2020-08-21T11:13:13.274','codes/prueba.java','AC',1,1,0,2020);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (15,15,'2020-08-21T11:13:13.274','codes/prueba.java','AC',1,1,0,2020);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (16,16,'2020-08-21T11:13:13.274','codes/prueba.java','AC',1,1,0,2020);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (17,17,'2020-08-21T11:13:13.274','codes/prueba.java','AC',1,1,0,2020);
INSERT INTO envios(id,id_judge,fecha,codigo_path,resolucion,id_alumno,id_problema,id_season,season_year) VALUES (18,18,'2020-08-21T11:13:13.274','codes/prueba.java','AC',1,1,0,2020);

INSERT INTO comentarios(id,id_envio,id_alumno,texto) VALUES (0,10,0,'Muy buena resolución del ejercicio, muy simple y muy claro.');
INSERT INTO comentarios(id,id_envio,id_alumno,texto) VALUES (1,10,2,'Estoy deseando ver como lo has conseguido!!');
INSERT INTO comentarios(id,id_envio,id_alumno,texto) VALUES (2,3,2,'Como???');

INSERT INTO aclaraciones(id,id_tutor,id_problema,texto) VALUES (0,2,2,'Encontrar la moneda consiste en hacer un bucle infinito con el que reviente el PC');

INSERT INTO administradores(id,email,pass) VALUES (0, 'administrador@us.es', 'adm1asdfWW%n1234');

INSERT INTO publicaciones(id,id_alumno,fecha,texto) VALUES (0,0,'2018-11-21T11:13:13.274','Estais listos para el Ada Byron?! Nos van a machacar!!!');
INSERT INTO publicaciones(id,id_alumno,fecha,texto) VALUES (1,1,'2018-11-22T11:13:13.274','A alguien le da TLE también el de Rarmon? No veo cómo hacerlo.');

INSERT INTO pregunta(id,id_tutor,id_alumno,id_problema, pregunta, respuesta) VALUES (0,null,0,0,'¿La moneda es de euro?', null);
INSERT INTO pregunta(id,id_tutor,id_alumno,id_problema, pregunta, respuesta) VALUES (1,null,1,0,'¿La moneda es de 2 euros?', null);
INSERT INTO pregunta(id,id_tutor,id_alumno,id_problema, pregunta, respuesta) VALUES (2,1,1,1,'¿Dónde está Fuente Palmera?', 'Eso no es relevante para la resolución del problema.');

INSERT INTO logros(id,nombre_logro,imagen) VALUES (0,'10 envios realizados','resources/images/logros/logroEnvio.jpg'); --envio
INSERT INTO logros(id,nombre_logro,imagen) VALUES (1,'50 envios realizados','resources/images/logros/logroEnvio.jpg'); --envio
INSERT INTO logros(id,nombre_logro,imagen) VALUES (2,'100 envios realizados','resources/images/logros/logroEnvio.jpg'); --envio
INSERT INTO logros(id,nombre_logro,imagen) VALUES (3,'500 envios realizados','resources/images/logros/logroEnvio.jpg'); --envio
INSERT INTO logros(id,nombre_logro,imagen) VALUES (4,'1000 envios realizados','resources/images/logros/logroEnvio.jpg'); --envio
INSERT INTO logros(id,nombre_logro,imagen) VALUES (5,'10 envios con accepted','resources/images/logros/logroAccept.jpg');  --accepted
INSERT INTO logros(id,nombre_logro,imagen) VALUES (6,'25 envios con accepted','resources/images/logros/logroAccept.jpg'); --accepted
INSERT INTO logros(id,nombre_logro,imagen) VALUES (7,'50 envios con accepted','resources/images/logros/logroAccept.jpg'); --accepted
INSERT INTO logros(id,nombre_logro,imagen) VALUES (8,'10 envios con wrong answer','resources/images/logros/logroWrong.jpg'); --wrong
INSERT INTO logros(id,nombre_logro,imagen) VALUES (9,'25 envios con wrong answer','resources/images/logros/logroWrong.jpg'); --wrong
INSERT INTO logros(id,nombre_logro,imagen) VALUES (10,'50 envios con wrong answer','resources/images/logros/logroWrong.jpg'); --wrong




