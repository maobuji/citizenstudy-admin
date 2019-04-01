CREATE DATABASE IF NOT EXISTS mybatis_gen_test DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;
use mybatis_gen_test;

CREATE TABLE IF NOT EXISTS t_user (
  id bigint(20) NOT NULL,
  username varchar(50)  NOT NULL,
  password varchar(500)  NOT NULL,
  enabled tinyint(1) NOT NULL,
  PRIMARY KEY (id)
);