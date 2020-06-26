-- user part

CREATE TABLE db_test.user (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20) NOT NULL UNIQUE,
  password VARCHAR(70) NOT NULL,
  name varchar(40) NOT NULL,
  active BIT(1) DEFAULT 0,
  active_date DATETIME,
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
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_UNAUTH');

INSERT INTO user  VALUES
(1, 'john','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', "abc", 0, "2019-01-01 00:00:00" ,"2019-01-01","2019-01-01"),
(2, 'mary','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', "bcd", 0, "2019-01-01 00:00:00" ,"2019-01-01","2019-01-01"),
(3, 'susan','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', 'efg', 0, "2019-01-01 00:00:00" ,"2019-01-01","2019-01-01");
-- id : john, pw : fun123

-- 자유 게시판 table
CREATE TABLE free_board(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title varchar(40) NOT NULL,
    writer_id BIGINT,
    writer_name varchar(20) NOT NULL,
    create_date DATE DEFAULT (CURRENT_DATE),
    content TEXT NOT NULL,
    FOREIGN KEY(writer_id) REFERENCES user (id)
);
-- user 가 사라지더라도 게시글은 남도록 작성

CREATE TABLE free_board_file(
	store_filename VARCHAR(60) NOT NULL PRIMARY KEY,
    ordinary_filename varchar(60) NOT NULL,
    free_board_id BIGINT NOT NULL ,
    FOREIGN KEY(free_board_id) REFERENCES free_board(id) ON DELETE CASCADE
);
-- 자유 게시판 파일 table

-- test code
insert into free_board(id, title, writer_id, writer_name, content) values(1, 'test title 1', 1, 'mary', 'test content 1');
insert into free_board(id, title, writer_id, writer_name, content) values(2, 'test title 2', 1, 'mary', 'test content 2');
insert into free_board(id, title, writer_id, writer_name, content) values(3, 'test title 3', 1, 'efg', 'test content 3');