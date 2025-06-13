-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema MovieTickets1
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema MovieTickets1
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `MovieTickets1` DEFAULT CHARACTER SET utf8mb4 ;
USE `MovieTickets1` ;

-- -----------------------------------------------------
-- Table `MovieTickets1`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MovieTickets1`.`customer` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `age` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `MovieTickets1`.`hall`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MovieTickets1`.`hall` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `number_of_cols` INT(11) NOT NULL,
  `number_of_rows` INT(11) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `MovieTickets1`.`movie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MovieTickets1`.`movie` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `duration` FLOAT NOT NULL,
  `genre` ENUM('ACTION', 'ROMANTIC', 'COMEDY') NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `MovieTickets1`.`screen_time`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MovieTickets1`.`screen_time` (
  `screen_time_id` INT(11) NOT NULL AUTO_INCREMENT,
  `end_date` DATETIME NOT NULL,
  `start_date` DATETIME NOT NULL,
  `hall_id` INT(11) NOT NULL,
  `movie_id` INT(11) NOT NULL,
  PRIMARY KEY (`screen_time_id`),
  UNIQUE INDEX `UK_screen_time` (`end_date` ASC, `start_date` ASC, `hall_id` ASC, `movie_id` ASC) VISIBLE,
  INDEX `FK_screen_time_hall` (`hall_id` ASC) VISIBLE,
  INDEX `FK_screen_time_movie` (`movie_id` ASC) VISIBLE,
  CONSTRAINT `FK_screen_time_hall`
    FOREIGN KEY (`hall_id`)
    REFERENCES `MovieTickets1`.`hall` (`id`),
  CONSTRAINT `FK_screen_time_movie`
    FOREIGN KEY (`movie_id`)
    REFERENCES `MovieTickets1`.`movie` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `MovieTickets1`.`receipt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MovieTickets1`.`receipt` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `total_price` DECIMAL(10,2) NOT NULL,
  `customer_id` INT(11) NOT NULL,
  `screen_time_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_receipt_customer` (`customer_id` ASC) VISIBLE,
  INDEX `FK_receipt_screen_time` (`screen_time_id` ASC) VISIBLE,
  CONSTRAINT `FK_receipt_customer`
    FOREIGN KEY (`customer_id`)
    REFERENCES `MovieTickets1`.`customer` (`id`),
  CONSTRAINT `FK_receipt_screen_time`
    FOREIGN KEY (`screen_time_id`)
    REFERENCES `MovieTickets1`.`screen_time` (`screen_time_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `MovieTickets1`.`seat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MovieTickets1`.`seat` (
  `id` VARCHAR(10) NOT NULL,
  `class` LONGTEXT NOT NULL,
  `default_price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `MovieTickets1`.`screen_time_seat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MovieTickets1`.`screen_time_seat` (
  `screen_time_id` INT(11) NOT NULL,
  `seat_id` VARCHAR(10) NOT NULL,
  `is_available` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`screen_time_id`, `seat_id`),
  INDEX `FK_sts_seat` (`seat_id` ASC) VISIBLE,
  CONSTRAINT `FK_sts_screen_time`
    FOREIGN KEY (`screen_time_id`)
    REFERENCES `MovieTickets1`.`screen_time` (`screen_time_id`),
  CONSTRAINT `FK_sts_seat`
    FOREIGN KEY (`seat_id`)
    REFERENCES `MovieTickets1`.`seat` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `MovieTickets1`.`seat_has_receipt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MovieTickets1`.`seat_has_receipt` (
  `receipt_id` INT(11) NOT NULL,
  `screen_time_id` INT(11) NOT NULL,
  `seat_id` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`receipt_id`, `screen_time_id`, `seat_id`),
  INDEX `FK_shr_screen_time` (`screen_time_id` ASC) VISIBLE,
  INDEX `FK_shr_seat` (`seat_id` ASC) VISIBLE,
  CONSTRAINT `FK_shr_receipt`
    FOREIGN KEY (`receipt_id`)
    REFERENCES `MovieTickets1`.`receipt` (`id`),
  CONSTRAINT `FK_shr_screen_time`
    FOREIGN KEY (`screen_time_id`)
    REFERENCES `MovieTickets1`.`screen_time` (`screen_time_id`),
  CONSTRAINT `FK_shr_seat`
    FOREIGN KEY (`seat_id`)
    REFERENCES `MovieTickets1`.`seat` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
