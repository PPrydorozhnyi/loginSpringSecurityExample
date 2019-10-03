CREATE TABLE uuser (
  user_id SERIAL PRIMARY KEY,
  username VARCHAR(100),
  password VARCHAR(100)
);

CREATE TABLE rrole (
  role_id SERIAL PRIMARY KEY,
  name VARCHAR(100)
);

CREATE TABLE uuser_rrole (
  user_id INTEGER,
  role_id INTEGER
);

ALTER TABLE ONLY uuser_rrole ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES uuser(user_id);
ALTER TABLE ONLY uuser_rrole ADD CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES rrole(role_id);

INSERT INTO rrole (name) VALUES ('guest');
INSERT INTO rrole (name) VALUES ('admin');
