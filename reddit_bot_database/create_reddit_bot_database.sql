-- MySQL Script generated by MySQL Workbench
-- Thu Mar 31 22:13:57 2016
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`account` (
  `username` VARCHAR(500) NOT NULL,
  `password` VARCHAR(45) NULL,
  `linkKarm` INT NULL,
  `postKarm` INT NULL,
  `dateCreated` MEDIUMTEXT NULL,
  PRIMARY KEY (`username`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`post` (
  `postID` VARCHAR(45) NOT NULL,
  `username` VARCHAR(500) NULL,
  `subreddit` VARCHAR(45) NULL,
  `content` TEXT(40000) NULL,
  `title` VARCHAR(45) NULL,
  `time` MEDIUMTEXT NULL,
  PRIMARY KEY (`postID`),
  UNIQUE INDEX `postID_UNIQUE` (`postID` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`dataFeed`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`dataFeed` (
  `feedID` VARCHAR(45) NOT NULL,
  `website` VARCHAR(45) NULL,
  `dataFeed` VARCHAR(1000) NULL,
  `level` INT NULL,
  `content` TEXT(40000) NULL,
  `summary` TEXT(40000) NULL,
  `time` MEDIUMTEXT NULL,
  `lastUsed` MEDIUMTEXT NULL,
  PRIMARY KEY (`feedID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`comments` (
  `username` VARCHAR(500) NOT NULL,
  `postID` VARCHAR(45) NULL,
  `commentuser` VARCHAR(45) NULL,
  `time` MEDIUMTEXT NULL,
  `content` TEXT(1000) NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`messages` (
  `to` VARCHAR(500) NOT NULL,
  `from` VARCHAR(500) NULL,
  `content` TEXT(1000) NULL,
  `postID` VARCHAR(45) NULL,
  `time` MEDIUMTEXT NULL,
  PRIMARY KEY (`to`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`config`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`config` (
  `levels` INT NOT NULL,
  `minDelay` MEDIUMTEXT NULL,
  PRIMARY KEY (`levels`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`subreddits`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`subreddits` (
  `users` INT NOT NULL,
  `description` TEXT(1000) NULL,
  `ID` VARCHAR(500) NULL,
  PRIMARY KEY (`users`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
