-- user part

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
-- id : john, pw : fun123

-- test code (user_roles)
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 3);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 1);

-- 자유 게시판 table
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
-- user 가 사라지더라도 게시글은 남도록 작성

CREATE TABLE free_board_file(
	store_filename VARCHAR(60) NOT NULL PRIMARY KEY,
    ordinary_filename varchar(60) NOT NULL,
    free_board_id BIGINT NOT NULL ,
    FOREIGN KEY(free_board_id) REFERENCES free_board(id) ON DELETE CASCADE
);
-- 자유 게시판 파일 table

CREATE TABLE free_board_comment (
   id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(20),
    content TEXT NOT NULL,
    create_date DATE DEFAULT (CURRENT_DATE),
    content_id BIGINT,
    FOREIGN KEY(content_id) REFERENCES free_board (id) ON DELETE CASCADE
);
-- 자유 게시판 댓글 table

-- test code (free_board)
insert into free_board(title, writer_id, writer_name, number_of_hit, content) values('시원스쿨, 과연 신동환은 어떻게될것인가', 1, 'mary', 30, 'test content 1');
insert into free_board(title, writer_id, writer_name, number_of_hit, content) values('test title 2', 1, '권오범', 100, 'test content 212');
insert into free_board(title, writer_id, writer_name, number_of_hit, content) values('test title 3', 1, '이규철', 72, 'test content 166');
insert into free_board(title, writer_id, writer_name, content) values('test title 4', 1, '공은배', 'test content 165');
insert into free_board(title, writer_id, writer_name, content) values('test title 5', 1, '오해인', 'test content 16');
insert into free_board(title, writer_id, writer_name, content) values('test title 6', 1, '신동택', 'test content 154');
insert into free_board(title, writer_id, writer_name, content) values('test title 7', 1, '김기환', 'test content 1654');
insert into free_board(title, writer_id, writer_name, content) values('test title 8', 1, '기기괴괴', 'test content 19');
insert into free_board(title, writer_id, writer_name, content) values('test title 9', 1, '슈렉', 'test content 9');
insert into free_board(title, writer_id, writer_name, content) values('test title 10', 1, '페이커', 'test content 10');
insert into free_board(title, writer_id, writer_name, content) values('test title 11', 1, '안성진', 'test content 11');
insert into free_board(title, writer_id, writer_name, content) values('test title 12', 1, '안생진', 'test content 12');
insert into free_board(title, writer_id, writer_name, content) values('test title 13', 1, '안뚱', 'test content 13');
insert into free_board(title, writer_id, writer_name, content) values('test title 14', 1, '배수민', 'test content 14');
insert into free_board(title, writer_id, writer_name, content) values('test title 15', 1, '배짱', 'test content 15');
insert into free_board(title, writer_id, writer_name, content) values('test title 16', 1, '김지허', 'test content 16');
insert into free_board(title, writer_id, writer_name, content) values('test title 17', 1, '김성한', 'test content 17');
insert into free_board(title, writer_id, writer_name, content) values('test title 18', 1, '김지호동거인', 'test content 18');
insert into free_board(title, writer_id, writer_name, content) values('test title 19', 1, '맥주', 'test content 19');
insert into free_board(title, writer_id, writer_name, content) values('test title 20', 1, '소주', 'test content 20');
insert into free_board(title, writer_id, writer_name, content) values('test title 21', 1, 'mary', 'test content 21');