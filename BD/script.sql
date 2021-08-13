-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema encinal_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema encinal_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `encinal_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish2_ci ;
USE `encinal_db` ;

-- -----------------------------------------------------
-- Table `encinal_db`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `encinal_db`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(60) NULL,
  `password` VARCHAR(200) NULL,
  `first_name` VARCHAR(45) NULL,
  `middle_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(100) NULL,
  `enabled` TINYINT NULL,
  `created_at` TIMESTAMP NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `encinal_db`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `encinal_db`.`roles` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `encinal_db`.`users_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `encinal_db`.`users_roles` (
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  CONSTRAINT `fk_users_roles_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `encinal_db`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_roles_role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `encinal_db`.`roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_users_roles_role_id_idx` ON `encinal_db`.`users_roles` (`role_id` ASC);


-- -----------------------------------------------------
-- Table `encinal_db`.`identification_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `encinal_db`.`identification_types` (
  `identification_type_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`identification_type_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `encinal_db`.`status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `encinal_db`.`status` (
  `status_id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NULL,
  PRIMARY KEY (`status_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `encinal_db`.`clients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `encinal_db`.`clients` (
  `client_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `middle_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(100) NOT NULL,
  `nit` VARCHAR(15) NOT NULL,
  `id` VARCHAR(25) NOT NULL,
  `identification_type_id` INT NULL,
  `tel_number` VARCHAR(20) NULL,
  `email` VARCHAR(45) NULL,
  `birth_date` DATE NOT NULL,
  `created_at` TIMESTAMP NULL,
  `address` VARCHAR(200) NOT NULL,
  `status_id` INT NULL,
  PRIMARY KEY (`client_id`),
  CONSTRAINT `fk_clients_identification_type_id`
    FOREIGN KEY (`identification_type_id`)
    REFERENCES `encinal_db`.`identification_types` (`identification_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_clients_status_id`
    FOREIGN KEY (`status_id`)
    REFERENCES `encinal_db`.`status` (`status_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_clients_identification_type_id_idx` ON `encinal_db`.`clients` (`identification_type_id` ASC);

CREATE INDEX `fk_clients_status_id_idx` ON `encinal_db`.`clients` (`status_id` ASC);


-- -----------------------------------------------------
-- Table `encinal_db`.`sellers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `encinal_db`.`sellers` (
  `seller_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `middle_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `contract_date` DATE NOT NULL,
  `base_salary` DECIMAL(10,2) NOT NULL,
  `sells_quantity` INT NULL,
  `status_id` INT NULL,
  PRIMARY KEY (`seller_id`),
  CONSTRAINT `fk_sellers_status_id`
    FOREIGN KEY (`status_id`)
    REFERENCES `encinal_db`.`status` (`status_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_sellers_status_id_idx` ON `encinal_db`.`sellers` (`status_id` ASC);


-- -----------------------------------------------------
-- Table `encinal_db`.`blocks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `encinal_db`.`blocks` (
  `block_id` INT NOT NULL AUTO_INCREMENT,
  `block_number` VARCHAR(45) NULL,
  `capacity` INT NULL,
  PRIMARY KEY (`block_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `encinal_db`.`terrains`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `encinal_db`.`terrains` (
  `terrain_id` INT NOT NULL AUTO_INCREMENT,
  `block_id` INT NULL,
  `terrain_number` VARCHAR(45) NULL,
  `status_id` INT NULL,
  `price` DECIMAL(10,2) NULL,
  `height` DOUBLE NULL,
  `weight` DOUBLE NULL,
  PRIMARY KEY (`terrain_id`),
  CONSTRAINT `fk_terrains_block_id`
    FOREIGN KEY (`block_id`)
    REFERENCES `encinal_db`.`blocks` (`block_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_terrains_status_id`
    FOREIGN KEY (`status_id`)
    REFERENCES `encinal_db`.`status` (`status_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_terrains_block_id_idx` ON `encinal_db`.`terrains` (`block_id` ASC);

CREATE INDEX `fk_terrains_status_id_idx` ON `encinal_db`.`terrains` (`status_id` ASC);


-- -----------------------------------------------------
-- Table `encinal_db`.`sales_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `encinal_db`.`sales_types` (
  `sale_type_id` INT NOT NULL AUTO_INCREMENT,
  `sale_type` VARCHAR(45) NULL,
  `description` VARCHAR(50) NULL,
  PRIMARY KEY (`sale_type_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `encinal_db`.`sales`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `encinal_db`.`sales` (
  `sale_id` INT NOT NULL AUTO_INCREMENT,
  `sale_date` TIMESTAMP NULL,
  `seller_id` INT NULL,
  `client_id` INT NULL,
  `total` DECIMAL(10,2) NULL,
  `sale_type_id` INT NULL,
  `status_id` INT NULL,
  PRIMARY KEY (`sale_id`),
  CONSTRAINT `fk_sales_sale_type_id`
    FOREIGN KEY (`sale_type_id`)
    REFERENCES `encinal_db`.`sales_types` (`sale_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sales_client_id`
    FOREIGN KEY (`client_id`)
    REFERENCES `encinal_db`.`clients` (`client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sales_seller_id`
    FOREIGN KEY (`seller_id`)
    REFERENCES `encinal_db`.`sellers` (`seller_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sales_status_id`
    FOREIGN KEY (`status_id`)
    REFERENCES `encinal_db`.`status` (`status_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_sales_sale_type_id_idx` ON `encinal_db`.`sales` (`sale_type_id` ASC);

CREATE INDEX `fk_sales_client_id_idx` ON `encinal_db`.`sales` (`client_id` ASC);

CREATE INDEX `fk_sales_seller_id_idx` ON `encinal_db`.`sales` (`seller_id` ASC);

CREATE INDEX `fk_sales_status_id_idx` ON `encinal_db`.`sales` (`status_id` ASC);


-- -----------------------------------------------------
-- Table `encinal_db`.`payments_agreements`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `encinal_db`.`payments_agreements` (
  `payment_agreement_id` INT NOT NULL AUTO_INCREMENT,
  `interest_rate` DECIMAL(10,2) NULL,
  `terrain_id` INT NULL,
  `total_agreement` DECIMAL(10,2) NULL,
  `total_payments` INT NULL,
  `status_id` INT NULL,
  `sale_id` INT NULL,
  PRIMARY KEY (`payment_agreement_id`),
  CONSTRAINT `fk_payments_agreements_terrain_id`
    FOREIGN KEY (`terrain_id`)
    REFERENCES `encinal_db`.`terrains` (`terrain_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_payments_agreements_sale_id`
    FOREIGN KEY (`sale_id`)
    REFERENCES `encinal_db`.`sales` (`sale_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_payments_agreements_status_id`
    FOREIGN KEY (`status_id`)
    REFERENCES `encinal_db`.`status` (`status_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_payments_agreements_terrain_id_idx` ON `encinal_db`.`payments_agreements` (`terrain_id` ASC);

CREATE INDEX `fk_payments_agreements_sale_id_idx` ON `encinal_db`.`payments_agreements` (`sale_id` ASC);

CREATE INDEX `fk_payments_agreements_status_id_idx` ON `encinal_db`.`payments_agreements` (`status_id` ASC);


-- -----------------------------------------------------
-- Table `encinal_db`.`receipts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `encinal_db`.`receipts` (
  `receipt_id` INT NOT NULL AUTO_INCREMENT,
  `receipt_number` VARCHAR(20) NULL,
  `created_at` TIMESTAMP NULL,
  `seller_id` INT NULL,
  PRIMARY KEY (`receipt_id`),
  CONSTRAINT `fk_receipts_seller_id`
    FOREIGN KEY (`seller_id`)
    REFERENCES `encinal_db`.`sellers` (`seller_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_receipts_seller_id_idx` ON `encinal_db`.`receipts` (`seller_id` ASC);


-- -----------------------------------------------------
-- Table `encinal_db`.`payments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `encinal_db`.`payments` (
  `payment_id` INT NOT NULL AUTO_INCREMENT,
  `payment_number` INT NULL,
  `payment_agreement_id` INT NULL,
  `principal_value` DECIMAL(10,2) NULL,
  `interest_rate_generated` DECIMAL(10,2) NULL,
  `status_id` INT NULL,
  PRIMARY KEY (`payment_id`),
  CONSTRAINT `fk_payments_payment_agreement_id`
    FOREIGN KEY (`payment_agreement_id`)
    REFERENCES `encinal_db`.`payments_agreements` (`payment_agreement_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_payments_status_id`
    FOREIGN KEY (`status_id`)
    REFERENCES `encinal_db`.`status` (`status_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_payments_payment_agreement_id_idx` ON `encinal_db`.`payments` (`payment_agreement_id` ASC);

CREATE INDEX `fk_payments_status_id_idx` ON `encinal_db`.`payments` (`status_id` ASC);


-- -----------------------------------------------------
-- Table `encinal_db`.`receipts_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `encinal_db`.`receipts_details` (
  `receipt_detail_id` INT NOT NULL AUTO_INCREMENT,
  `payment_id` INT NULL,
  `receipt_id` INT NULL,
  `subtotal` DECIMAL(10,2) NULL,
  PRIMARY KEY (`receipt_detail_id`),
  CONSTRAINT `fk_receipts_details_payment_id`
    FOREIGN KEY (`payment_id`)
    REFERENCES `encinal_db`.`payments` (`payment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_receipts_details_receipt_id`
    FOREIGN KEY (`receipt_id`)
    REFERENCES `encinal_db`.`receipts` (`receipt_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_receipts_details_peyment_id_idx` ON `encinal_db`.`receipts_details` (`payment_id` ASC);

CREATE INDEX `fk_receipts_details_receipt_id_idx` ON `encinal_db`.`receipts_details` (`receipt_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
