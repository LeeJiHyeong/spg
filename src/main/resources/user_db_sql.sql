-- user part

CREATE TABLE db_test.user (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(10) NOT NULL UNIQUE,
  password VARCHAR(50) NOT NULL,
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