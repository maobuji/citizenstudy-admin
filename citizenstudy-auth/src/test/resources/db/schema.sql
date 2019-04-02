SET MODE MySQL;

CREATE TABLE IF NOT EXISTS t_user(
  id bigint(20) NOT NULL,
  username varchar(50)  NOT NULL,
  PRIMARY KEY (id)
);