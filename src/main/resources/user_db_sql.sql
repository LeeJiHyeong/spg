-- user part

CREATE TABLE db_test.user (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20) NOT NULL UNIQUE,
  password VARCHAR(70) NOT NULL,
  name varchar(40) NOT NULL,
  active TINYINT(1) DEFAULT 0,
  active_date DATETIME DEFAULT NULL,
  create_date DATETIME DEFAULT NULL,
  update_date DATETIME DEFAULT NULL
);

CREATE TABLE db_test.roles (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE DEFAULT NULL
);

CREATE TABLE db_test.user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT IGNORE INTO roles(name) VALUES('ROLE_USER');
INSERT IGNORE INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO user  VALUES
(1, 'john','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', "abc", 0, "2019-01-01 00:00:00" ,"2019-01-01","2019-01-01"),
(2, 'mary','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', "bcd", 0, "2019-01-01 00:00:00" ,"2019-01-01","2019-01-01"),
(3, 'susan','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'efg', 0, "2019-01-01 00:00:00" ,"2019-01-01","2019-01-01")
-- id : john, pw : fun123