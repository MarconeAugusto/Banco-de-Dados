-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ControleDeEmprestimos
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ControleDeEmprestimos
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ControleDeEmprestimos` ;
USE `ControleDeEmprestimos` ;

-- -----------------------------------------------------
-- Table `ControleDeEmprestimos`.`Aluno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ControleDeEmprestimos`.`Aluno` (
  `matricula` INT NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `estadoMatricula` TINYINT(1) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `telefone` VARCHAR(45) NULL,
  `sobrenome` VARCHAR(45) NOT NULL,
  `penalidade` DATETIME NULL,
  PRIMARY KEY (`matricula`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ControleDeEmprestimos`.`Atividade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ControleDeEmprestimos`.`Atividade` (
  `idAtividade` INT NOT NULL AUTO_INCREMENT,
  ` nomeAtividade` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idAtividade`))
ENGINE = InnoDB
COMMENT = '\n';


-- -----------------------------------------------------
-- Table `ControleDeEmprestimos`.`Reserva`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ControleDeEmprestimos`.`Reserva` (
  `idReserva` INT NOT NULL,
  `Aluno_matricula` INT NOT NULL,
  PRIMARY KEY (`idReserva`, `Aluno_matricula`),
  INDEX `fk_Reserva_Aluno1_idx` (`Aluno_matricula` ASC),
  CONSTRAINT `fk_Reserva_Aluno1`
    FOREIGN KEY (`Aluno_matricula`)
    REFERENCES `ControleDeEmprestimos`.`Aluno` (`matricula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ControleDeEmprestimos`.`Equipamentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ControleDeEmprestimos`.`Equipamentos` (
  `patrimonio` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  `Reserva_idReserva` INT NULL,
  `Reserva_Aluno_matricula` INT NULL,
  `situacao` TINYINT(1) NOT NULL,
  PRIMARY KEY (`patrimonio`),
  INDEX `fk_Equipamentos_Reserva1_idx` (`Reserva_idReserva` ASC, `Reserva_Aluno_matricula` ASC),
  CONSTRAINT `fk_Equipamentos_Reserva1`
    FOREIGN KEY (`Reserva_idReserva` , `Reserva_Aluno_matricula`)
    REFERENCES `ControleDeEmprestimos`.`Reserva` (`idReserva` , `Aluno_matricula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ControleDeEmprestimos`.`Componentes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ControleDeEmprestimos`.`Componentes` (
  `idComponentes` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `modelo` VARCHAR(45) NULL,
  `Equipamentos_patrimonio` INT NOT NULL,
  PRIMARY KEY (`idComponentes`, `Equipamentos_patrimonio`),
  INDEX `fk_Componentes_Equipamentos1_idx` (`Equipamentos_patrimonio` ASC),
  CONSTRAINT `fk_Componentes_Equipamentos1`
    FOREIGN KEY (`Equipamentos_patrimonio`)
    REFERENCES `ControleDeEmprestimos`.`Equipamentos` (`patrimonio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ControleDeEmprestimos`.`Emprestimo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ControleDeEmprestimos`.`Emprestimo` (
  `idEmprestimo` INT NOT NULL AUTO_INCREMENT,
  `dataEmprestimo` DATETIME NOT NULL,
  `previsaoDevolucao` DATETIME NOT NULL,
  `devolucao` DATETIME NULL,
  `renovacao` INT NOT NULL,
  `penalidade` INT NOT NULL,
  `Aluno_matricula` INT NOT NULL,
  `situacao` TINYINT(1) NOT NULL,
  `finalidade` INT NOT NULL,
  PRIMARY KEY (`idEmprestimo`, `Aluno_matricula`),
  INDEX `fk_Emprestimo_Aluno1_idx` (`Aluno_matricula` ASC),
  CONSTRAINT `fk_Emprestimo_Aluno1`
    FOREIGN KEY (`Aluno_matricula`)
    REFERENCES `ControleDeEmprestimos`.`Aluno` (`matricula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ControleDeEmprestimos`.`Atividade_has_Aluno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ControleDeEmprestimos`.`Atividade_has_Aluno` (
  `Atividade_idAtividade` INT NOT NULL,
  `Aluno_matricula` INT NOT NULL,
  PRIMARY KEY (`Atividade_idAtividade`, `Aluno_matricula`),
  INDEX `fk_Atividade_has_Aluno_Aluno1_idx` (`Aluno_matricula` ASC),
  INDEX `fk_Atividade_has_Aluno_Atividade_idx` (`Atividade_idAtividade` ASC),
  CONSTRAINT `fk_Atividade_has_Aluno_Atividade`
    FOREIGN KEY (`Atividade_idAtividade`)
    REFERENCES `ControleDeEmprestimos`.`Atividade` (`idAtividade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Atividade_has_Aluno_Aluno1`
    FOREIGN KEY (`Aluno_matricula`)
    REFERENCES `ControleDeEmprestimos`.`Aluno` (`matricula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ControleDeEmprestimos`.`Emprestimo_has_Equipamentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ControleDeEmprestimos`.`Emprestimo_has_Equipamentos` (
  `Emprestimo_idEmprestimo` INT NOT NULL,
  `Equipamentos_patrimonio` INT NOT NULL,
  PRIMARY KEY (`Emprestimo_idEmprestimo`, `Equipamentos_patrimonio`),
  INDEX `fk_Emprestimo_has_Equipamentos_Equipamentos1_idx` (`Equipamentos_patrimonio` ASC),
  INDEX `fk_Emprestimo_has_Equipamentos_Emprestimo1_idx` (`Emprestimo_idEmprestimo` ASC),
  CONSTRAINT `fk_Emprestimo_has_Equipamentos_Emprestimo1`
    FOREIGN KEY (`Emprestimo_idEmprestimo`)
    REFERENCES `ControleDeEmprestimos`.`Emprestimo` (`idEmprestimo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Emprestimo_has_Equipamentos_Equipamentos1`
    FOREIGN KEY (`Equipamentos_patrimonio`)
    REFERENCES `ControleDeEmprestimos`.`Equipamentos` (`patrimonio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
