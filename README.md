CREATE SCHEMA IF NOT EXISTS hora
USE hora
CREATE USER IF NOT EXISTS 'aluno_cd'@'localhost' IDENTIFIED BY 'aluno_pw';
GRANT ALL PRIVILEGES ON hora.* TO 'aluno_cd'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE `horarios` (
  idhorarios int NOT NULL AUTO_INCREMENT,
  nome varchar(45) NOT NULL,
  hora_inicio int NOT NULL,
  hora_fim int NOT NULL,
  tag int NOT NULL,
  Data` int NOT NULL,
  PRIMARY KEY (idhorarios)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
