CREATE TABLE `db_test`.`user` (
  `id` INT NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `active` TINYINT(1) NOT NULL,
  `roles` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));