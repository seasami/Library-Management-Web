CREATE SCHEMA IF NOT EXISTS `quan_ly_thu_vien` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `quan_ly_thu_vien` ;

CREATE TABLE IF NOT EXISTS `quan_ly_thu_vien`.`roles` (
  `role_id` VARCHAR(30) NOT NULL,
  `role_name` VARCHAR(30) NOT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE INDEX `role_name` (`role_name`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `quan_ly_thu_vien`.`users` (
  `user_id` VARCHAR(30) NOT NULL,
  `role_id` VARCHAR(30) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username` VARCHAR(50) NOT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `password_hash` VARCHAR(255) NOT NULL,
  `full_name` VARCHAR(150) NOT NULL,
  `phone` VARCHAR(20) NULL DEFAULT NULL,
  `status` ENUM('active', 'restricted', 'locked') NOT NULL DEFAULT 'active',
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `username` (`username`),
  UNIQUE INDEX `email` (`email`),
  INDEX `fk_users_role` (`role_id`),
  CONSTRAINT `fk_users_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `quan_ly_thu_vien`.`roles` (`role_id`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `quan_ly_thu_vien`.`password_reset_tokens` (
  `reset_token_id` VARCHAR(30) NOT NULL,
  `user_id` VARCHAR(30) NOT NULL,
  `token_hash` CHAR(64) NOT NULL,
  `expires_at` DATETIME NOT NULL,
  `used_at` DATETIME NULL DEFAULT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`reset_token_id`),
  UNIQUE INDEX `token_hash` (`token_hash`),
  INDEX `idx_reset_tokens_user` (`user_id`),
  CONSTRAINT `fk_reset_tokens_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `quan_ly_thu_vien`.`users` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `quan_ly_thu_vien`.`managers` (
  `user_id` VARCHAR(30) NOT NULL,
  `employee_code` VARCHAR(30) NULL DEFAULT NULL,
  `office_location` VARCHAR(100) NULL DEFAULT NULL,
  `appointed_at` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_managers_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `quan_ly_thu_vien`.`users` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `quan_ly_thu_vien`.`librarians` (
  `user_id` VARCHAR(30) NOT NULL,
  `warehouse_code` VARCHAR(30) NULL DEFAULT NULL,
  `work_shift` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_librarians_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `quan_ly_thu_vien`.`users` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `quan_ly_thu_vien`.`readers` (
  `user_id` VARCHAR(30) NOT NULL,
  `subscription_id` VARCHAR(30) NULL DEFAULT NULL,
  `date_of_birth` DATE NULL DEFAULT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `citizen_id` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `citizen_id` (`citizen_id`),
  CONSTRAINT `fk_readers_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `quan_ly_thu_vien`.`users` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `quan_ly_thu_vien`.`audit_logs` (
  `log_id` VARCHAR(10) NOT NULL,
  `actor_user_id` VARCHAR(30) NULL DEFAULT NULL,
  `action` VARCHAR(100) NOT NULL,
  `entity_type` VARCHAR(50) NOT NULL,
  `entity_id` VARCHAR(64) NULL DEFAULT NULL,
  `reason` TEXT NULL DEFAULT NULL,
  `old_value` JSON NULL DEFAULT NULL,
  `new_value` JSON NULL DEFAULT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`log_id`),
  INDEX `idx_audit_entity` (`entity_type`, `entity_id`),
  INDEX `idx_audit_actor_date` (`actor_user_id`, `created_at`),
  CONSTRAINT `fk_audit_actor`
    FOREIGN KEY (`actor_user_id`)
    REFERENCES `quan_ly_thu_vien`.`users` (`user_id`)
    ON DELETE SET NULL)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `quan_ly_thu_vien`.`authors` (
  `author_id` VARCHAR(10) NOT NULL,
  `author_name` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`author_id`),
  UNIQUE INDEX `author_name` (`author_name`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `quan_ly_thu_vien`.`categories` (
  `category_id` VARCHAR(10) NOT NULL,
  `category_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE INDEX `category_name` (`category_name`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `quan_ly_thu_vien`.`publishers` (
  `publisher_id` VARCHAR(50) NOT NULL,
  `publisher_name` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`publisher_id`),
  UNIQUE INDEX `publisher_name` (`publisher_name`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `quan_ly_thu_vien`.`books` (
  `book_id` VARCHAR(20) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `category_id` VARCHAR(10) NULL DEFAULT NULL,
  `publisher_id` VARCHAR(50) NULL DEFAULT NULL,
  `publication_year` SMALLINT UNSIGNED NULL DEFAULT NULL,
  `min_reader_age` TINYINT UNSIGNED NOT NULL DEFAULT '0',
  `status` ENUM('active', 'inactive') NOT NULL DEFAULT 'active',
  `description` TEXT NULL DEFAULT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`book_id`),
  INDEX `fk_books_category` (`category_id`),
  INDEX `fk_books_publisher` (`publisher_id`),
  CONSTRAINT `fk_books_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `quan_ly_thu_vien`.`categories` (`category_id`)
    ON DELETE SET NULL,
  CONSTRAINT `fk_books_publisher`
    FOREIGN KEY (`publisher_id`)
    REFERENCES `quan_ly_thu_vien`.`publishers` (`publisher_id`)
    ON DELETE SET NULL)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `quan_ly_thu_vien`.`book_authors` (
  `book_id` VARCHAR(20) NOT NULL,
  `author_id` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`book_id`, `author_id`),
  INDEX `fk_book_authors_author` (`author_id`),
  CONSTRAINT `fk_book_authors_author`
    FOREIGN KEY (`author_id`)
    REFERENCES `quan_ly_thu_vien`.`authors` (`author_id`)
    ON DELETE RESTRICT,
  CONSTRAINT `fk_book_authors_book`
    FOREIGN KEY (`book_id`)
    REFERENCES `quan_ly_thu_vien`.`books` (`book_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `quan_ly_thu_vien`.`book_copies` (
  `copy_id` VARCHAR(5) NOT NULL,
  `book_id` VARCHAR(20) NOT NULL,
  `copy_code` VARCHAR(50) NOT NULL,
  `copy_status` ENUM('available', 'maintenance', 'retired') NOT NULL DEFAULT 'available',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`copy_id`),
  UNIQUE INDEX `copy_code` (`copy_code`),
  INDEX `idx_copies_book_status` (`book_id`, `copy_status`),
  CONSTRAINT `fk_copies_book`
    FOREIGN KEY (`book_id`)
    REFERENCES `quan_ly_thu_vien`.`books` (`book_id`)
    ON DELETE RESTRICT)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `quan_ly_thu_vien`.`borrow_slips` (
  `slip_id` VARCHAR(30) NOT NULL,
  `reader_id` VARCHAR(30) NOT NULL,
  `created_by` VARCHAR(30) NULL DEFAULT NULL,
  `borrowed_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `slip_status` ENUM('open', 'completed', 'cancelled') NOT NULL DEFAULT 'open',
  `note` VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (`slip_id`),
  INDEX `fk_slips_creator` (`created_by`),
  INDEX `idx_slips_reader_date` (`reader_id`, `borrowed_at`),
  INDEX `idx_slips_status` (`slip_status`),
  CONSTRAINT `fk_slips_creator`
    FOREIGN KEY (`created_by`)
    REFERENCES `quan_ly_thu_vien`.`librarians` (`user_id`)
    ON DELETE SET NULL,
  CONSTRAINT `fk_slips_reader`
    FOREIGN KEY (`reader_id`)
    REFERENCES `quan_ly_thu_vien`.`readers` (`user_id`)
    ON DELETE RESTRICT)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `quan_ly_thu_vien`.`borrow_details` (
  `detail_id` VARCHAR(30) NOT NULL,
  `slip_id` VARCHAR(30) NOT NULL,
  `copy_id` VARCHAR(5) NOT NULL,
  `due_at` DATETIME NOT NULL,
  `returned_at` DATETIME NULL DEFAULT NULL,
  `return_condition` ENUM('good', 'damaged', 'lost') NULL DEFAULT NULL,
  `return_confirmed_by` VARCHAR(30) NULL DEFAULT NULL,
  `note` VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (`detail_id`),
  INDEX `fk_details_returner` (`return_confirmed_by`),
  INDEX `idx_details_copy_returned` (`copy_id`, `returned_at`),
  INDEX `idx_details_due_returned` (`due_at`, `returned_at`),
  INDEX `idx_details_slip` (`slip_id`),
  CONSTRAINT `fk_details_copy`
    FOREIGN KEY (`copy_id`)
    REFERENCES `quan_ly_thu_vien`.`book_copies` (`copy_id`)
    ON DELETE RESTRICT,
  CONSTRAINT `fk_details_returner`
    FOREIGN KEY (`return_confirmed_by`)
    REFERENCES `quan_ly_thu_vien`.`users` (`user_id`)
    ON DELETE SET NULL,
  CONSTRAINT `fk_details_slip`
    FOREIGN KEY (`slip_id`)
    REFERENCES `quan_ly_thu_vien`.`borrow_slips` (`slip_id`)
    ON DELETE RESTRICT)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `quan_ly_thu_vien`.`borrowing_rules` (
  `rule_id` VARCHAR(5) NOT NULL,
  `rule_name` VARCHAR(100) NOT NULL,
  `max_books` INT UNSIGNED NOT NULL DEFAULT '5',
  `loan_days` INT UNSIGNED NOT NULL DEFAULT '14',
  `min_age_for_restricted_books` TINYINT UNSIGNED NOT NULL DEFAULT '0',
  `is_active` TINYINT(1) NOT NULL DEFAULT '1',
  `updated_by` VARCHAR(30) NULL DEFAULT NULL,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`rule_id`),
  INDEX `fk_rules_librarian` (`updated_by`),
  CONSTRAINT `fk_rules_librarian`
    FOREIGN KEY (`updated_by`)
    REFERENCES `quan_ly_thu_vien`.`librarians` (`user_id`)
    ON DELETE SET NULL)
ENGINE = InnoDB;