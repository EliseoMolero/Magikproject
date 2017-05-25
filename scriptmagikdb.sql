-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema magikproyectBD
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema magikproyectBD
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `magikproyectBD` DEFAULT CHARACTER SET utf8 ;
USE `magikproyectBD` ;

-- -----------------------------------------------------
-- Table `magikproyectBD`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `magikproyectBD`.`Usuario` (
  `idUsuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `apellidos` VARCHAR(45) NOT NULL,
  `email` NVARCHAR(45) NOT NULL,
  `password` NVARCHAR(45) NOT NULL,
  `admin` TINYINT(1) NOT NULL,
  `imagenPerfil` LONGBLOB NULL,
  PRIMARY KEY (`email`),
KEY (`idUsuario`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `magikproyectBD`.`Incidencias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `magikproyectBD`.`Incidencias` (
  `idIncidencias` INT NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(200) NOT NULL,
  `direccion` VARCHAR(200) NOT NULL,
  `imagen` LONGBLOB NOT NULL,
  `latitud` DECIMAL(10,8) NOT NULL,
  `longitud` DECIMAL(11,8) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  `email` NVARCHAR(45) NOT NULL,
  PRIMARY KEY (`idIncidencias`),
  INDEX `email_idx` (`email` ASC),
  CONSTRAINT `email`
    FOREIGN KEY (`email`)
    REFERENCES `magikproyectBD`.`Usuario` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
