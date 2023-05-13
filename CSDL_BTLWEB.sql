-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema btlweb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema btlweb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `btlweb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `btlweb` ;

-- -----------------------------------------------------
-- Table `btlweb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `btlweb`.`user` (
  `user_id` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `join_date` DATETIME(6) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `position` VARCHAR(255) NOT NULL,
  `username` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `UK_ob8kqyqqgmefl0aco34akdtpe` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `btlweb`.`question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `btlweb`.`question` (
  `question_id` VARCHAR(255) NOT NULL,
  `content` TEXT NOT NULL,
  `created_at` DATETIME(6) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `views_no` INT NOT NULL,
  `user_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`question_id`),
  INDEX `FK4ekrlbqiybwk8abhgclfjwnmc` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK4ekrlbqiybwk8abhgclfjwnmc`
    FOREIGN KEY (`user_id`)
    REFERENCES `btlweb`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `btlweb`.`answer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `btlweb`.`answer` (
  `answer_id` VARCHAR(255) NOT NULL,
  `content` TINYTEXT NULL DEFAULT NULL,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `is_correct` INT NOT NULL,
  `question_id` VARCHAR(255) NOT NULL,
  `user_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`answer_id`),
  INDEX `FK8frr4bcabmmeyyu60qt7iiblo` (`question_id` ASC) VISIBLE,
  INDEX `FK68tbcw6bunvfjaoscaj851xpb` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK68tbcw6bunvfjaoscaj851xpb`
    FOREIGN KEY (`user_id`)
    REFERENCES `btlweb`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK8frr4bcabmmeyyu60qt7iiblo`
    FOREIGN KEY (`question_id`)
    REFERENCES `btlweb`.`question` (`question_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `btlweb`.`answer_downvote`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `btlweb`.`answer_downvote` (
  `answer_id` VARCHAR(255) NOT NULL,
  `user_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`answer_id`, `user_id`),
  INDEX `FK4jr37778kj5qo4c0yvwr8dq7o` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK4jr37778kj5qo4c0yvwr8dq7o`
    FOREIGN KEY (`user_id`)
    REFERENCES `btlweb`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FKlcri6qu8ux4xwpxhg26veevb3`
    FOREIGN KEY (`answer_id`)
    REFERENCES `btlweb`.`answer` (`answer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `btlweb`.`answer_pic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `btlweb`.`answer_pic` (
  `answer_id` VARCHAR(255) NOT NULL,
  `answer_pic` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`answer_id`, `answer_pic`),
  UNIQUE INDEX `UKq81mrcqp7cr3v04jd4v59886w` (`answer_id` ASC, `answer_pic` ASC) VISIBLE,
  CONSTRAINT `FKpnu9ca3ct6bfuosjkchrfu8sw`
    FOREIGN KEY (`answer_id`)
    REFERENCES `btlweb`.`answer` (`answer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `btlweb`.`answer_upvote`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `btlweb`.`answer_upvote` (
  `answer_id` VARCHAR(255) NOT NULL,
  `user_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`answer_id`, `user_id`),
  INDEX `FK442hcp3l2twi9s7a7mhral7e5` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK442hcp3l2twi9s7a7mhral7e5`
    FOREIGN KEY (`user_id`)
    REFERENCES `btlweb`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FKrtv91bv7j9h1nu3k0ty6u44rf`
    FOREIGN KEY (`answer_id`)
    REFERENCES `btlweb`.`answer` (`answer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `btlweb`.`question_downvote`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `btlweb`.`question_downvote` (
  `question_id` VARCHAR(255) NOT NULL,
  `user_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`question_id`, `user_id`),
  INDEX `FK4sy4mw5tusbw0xushhgtw869p` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK4sy4mw5tusbw0xushhgtw869p`
    FOREIGN KEY (`user_id`)
    REFERENCES `btlweb`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FKf8lqm64k39yx59y05t46yav1m`
    FOREIGN KEY (`question_id`)
    REFERENCES `btlweb`.`question` (`question_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `btlweb`.`question_pic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `btlweb`.`question_pic` (
  `question_id` VARCHAR(255) NOT NULL,
  `question_pic` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`question_id`, `question_pic`),
  INDEX `FK4qf8wg49oewkbdcmtwyq3jd2g` (`question_id` ASC) VISIBLE,
  CONSTRAINT `FK4qf8wg49oewkbdcmtwyq3jd2g`
    FOREIGN KEY (`question_id`)
    REFERENCES `btlweb`.`question` (`question_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `btlweb`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `btlweb`.`tag` (
  `tag_id` VARCHAR(255) NOT NULL,
  `tag_description` VARCHAR(255) NULL DEFAULT NULL,
  `tag_name` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`tag_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `btlweb`.`question_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `btlweb`.`question_tag` (
  `tag_id` VARCHAR(255) NOT NULL,
  `question_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`tag_id`, `question_id`),
  INDEX `FK44ydihbi2qk8k96175quf5q63` (`question_id` ASC) VISIBLE,
  CONSTRAINT `FK44ydihbi2qk8k96175quf5q63`
    FOREIGN KEY (`question_id`)
    REFERENCES `btlweb`.`question` (`question_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FKnacet7y1n8llxvrbmm3xdq13j`
    FOREIGN KEY (`tag_id`)
    REFERENCES `btlweb`.`tag` (`tag_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `btlweb`.`question_upvote`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `btlweb`.`question_upvote` (
  `question_id` VARCHAR(255) NOT NULL,
  `user_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`question_id`, `user_id`),
  INDEX `FKkyfkl3env3rak670otdpyr602` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FKe4syiqvubipsolynjurktj1og`
    FOREIGN KEY (`question_id`)
    REFERENCES `btlweb`.`question` (`question_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FKkyfkl3env3rak670otdpyr602`
    FOREIGN KEY (`user_id`)
    REFERENCES `btlweb`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


