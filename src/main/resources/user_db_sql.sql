CREATE TABLE user (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20) NOT NULL UNIQUE,
  password VARCHAR(70) NOT NULL,
  name varchar(40) NOT NULL,
  active_date DATETIME,
  create_date DATETIME DEFAULT NULL,
  update_date DATETIME DEFAULT NULL
);

CREATE TABLE roles (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE DEFAULT NULL
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

INSERT INTO roles(name) VALUES('ROLE_STUDENT');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_UNAUTH');

INSERT INTO user  VALUES
(1, 'john','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', "abc", null, "2019-01-01","2019-01-01"),
(2, 'mary','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', "bcd", null, "2019-01-01","2019-01-01"),
(3, 'susan','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'efg', null, "2019-01-01","2019-01-01");

INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 3);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 1);

CREATE TABLE free_board(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title varchar(40) NOT NULL,
    writer_id BIGINT,
    writer_name varchar(20) NOT NULL,
    create_date DATE DEFAULT (CURRENT_DATE),
    number_of_hit SMALLINT DEFAULT 0,
    content TEXT NOT NULL,
    FOREIGN KEY(writer_id) REFERENCES user (id) ON DELETE SET NULL
);

CREATE TABLE free_board_file(
	store_filename VARCHAR(60) NOT NULL PRIMARY KEY,
    ordinary_filename varchar(60) NOT NULL,
    free_board_id BIGINT NOT NULL ,
    FOREIGN KEY(free_board_id) REFERENCES free_board(id) ON DELETE CASCADE
);

CREATE TABLE free_board_comment (
   id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(20),
    content TEXT NOT NULL,
    create_date DATE DEFAULT (CURRENT_DATE),
    content_id BIGINT,
    FOREIGN KEY(content_id) REFERENCES free_board (id) ON DELETE CASCADE
);
CREATE TABLE notice_board(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title varchar(40) NOT NULL,
    writer_id BIGINT,
    writer_name varchar(20) NOT NULL,
    create_date DATE DEFAULT (CURRENT_DATE),
    number_of_hit SMALLINT DEFAULT 0,
    content TEXT NOT NULL,
    FOREIGN KEY(writer_id) REFERENCES user (id) ON DELETE SET NULL
);

CREATE TABLE notice_board_file(
	store_filename VARCHAR(60) NOT NULL PRIMARY KEY,
    ordinary_filename varchar(60) NOT NULL,
    notice_board_id BIGINT NOT NULL ,
    FOREIGN KEY(notice_board_id) REFERENCES notice_board(id) ON DELETE CASCADE
);

CREATE TABLE notice_board_comment (
   id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(20),
    content TEXT NOT NULL,
    create_date DATE DEFAULT (CURRENT_DATE),
    content_id BIGINT,
    FOREIGN KEY(content_id) REFERENCES notice_board (id) ON DELETE CASCADE
);

CREATE TABLE edu_board(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title varchar(40) NOT NULL,
    writer_id BIGINT,
    writer_name varchar(20) NOT NULL,
    create_date DATE DEFAULT (CURRENT_DATE),
    number_of_hit SMALLINT DEFAULT 0,
    content TEXT NOT NULL,
    FOREIGN KEY(writer_id) REFERENCES user (id) ON DELETE SET NULL
);

CREATE TABLE edu_board_file(
	store_filename VARCHAR(60) NOT NULL PRIMARY KEY,
    ordinary_filename varchar(60) NOT NULL,
    edu_board_id BIGINT NOT NULL ,
    FOREIGN KEY(edu_board_id) REFERENCES edu_board(id) ON DELETE CASCADE
);

CREATE TABLE edu_board_comment (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(20),
    content TEXT NOT NULL,
    create_date DATE DEFAULT (CURRENT_DATE),
    content_id BIGINT,
    FOREIGN KEY(content_id) REFERENCES edu_board (id) ON DELETE CASCADE
);
