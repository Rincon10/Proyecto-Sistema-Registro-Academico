--SUBJECTS 
INSERT INTO SUBJECTS (acronym, name) VALUES ('INSI', 'Introduccion a la ingenieria de sistemas');
INSERT INTO SUBJECTS (acronym, name) VALUES ('MMIN', 'Modelos matematicos para informatica');
INSERT INTO SUBJECTS (acronym, name) VALUES ('AYPR', 'Algoritmos y programacion');
INSERT INTO SUBJECTS (acronym, name) VALUES ('LCAT', 'Logica calculatoria');
INSERT INTO SUBJECTS (acronym, name) VALUES ('AYED', 'Algoritmos y estructuras de datos');
INSERT INTO SUBJECTS (acronym, name) VALUES ('MATD', 'Matematicas discretas');
INSERT INTO SUBJECTS (acronym, name) VALUES ('CNYT', 'Ciencias naturales y tecnologia');
INSERT INTO SUBJECTS (acronym, name) VALUES ('MBDA', 'Modelos y bases de datos');
INSERT INTO SUBJECTS (acronym, name) VALUES ('TPRO', 'Teoria de la programacion');
INSERT INTO SUBJECTS (acronym, name) VALUES ('POOB', 'Programacion orientada a objetos');
INSERT INTO SUBJECTS (acronym, name) VALUES ('ACSO', 'Arquitectura computacional');
INSERT INTO SUBJECTS (acronym, name) VALUES ('TSOR', 'Teoria de sistemas y organizaciones');
INSERT INTO SUBJECTS (acronym, name) VALUES ('RECO', 'Redes de computadores');
INSERT INTO SUBJECTS (acronym, name) VALUES ('CVDS', 'Ciclos de vida de desarrollo de softw');
INSERT INTO SUBJECTS (acronym, name) VALUES ('AUPN', 'Automatizacion de procesos de negocio');
INSERT INTO SUBJECTS (acronym, name) VALUES ('SPTI', 'Seguridad y privacidad de TI');
INSERT INTO SUBJECTS (acronym, name) VALUES ('ARSW', 'Arquitecturas de software');
INSERT INTO SUBJECTS (acronym, name) VALUES ('SIML', 'Seminario de insercion laboral');
INSERT INTO SUBJECTS (acronym, name) VALUES ('IETI', 'Innovacion y emprendimiento');

--Users

INSERT INTO USERS (email, first_name, last_name, password ) VALUES('ivan.rincon-s@mail.escuelaing.edu.co','Ivan Camilo','Rincon Saavedra','$2a$10$8v6UIThCb9JdpuoIxZybSOY4ezqaEVhn7Z0zujaQUpbZ9B3FQsatm');
INSERT INTO USERS (email, first_name, last_name, password ) VALUES('leidy.rincon-s@mail.escuelaing.edu.co','Leidy Alejandra','Rincon Saavedra','$2a$10$8v6UIThCb9JdpuoIxZybSOY4ezqaEVhn7Z0zujaQUpbZ9B3FQsatm');

-- Students
INSERT INTO STUDENTS (id, semester) VALUES(1,10);

-- Professors

INSERT INTO Professors (id, Department) VALUES(2,'MATH_DEPARTMENT');

-- Groups

INSERT INTO Groups(number, professor_id, subject_acronym, start_time, end_time ) VALUES (1,2,'MATD','13:00:00','16:00:59');

-- Student_group

INSERT INTO student_group (group_id, student_id) VALUES(1,1);

-- Prerrequisites

INSERT INTO prerequisites values('MATD', 'LCAT');
INSERT INTO prerequisites values('MATD', 'MMIN');



