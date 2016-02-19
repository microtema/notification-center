CREATE TABLE hibernate_sequence
(
  next_val NUMERIC(19)
);
CREATE TABLE Message
(
  id          NUMERIC(19) PRIMARY KEY NOT NULL,
  description TEXT                    NOT NULL,
  image       VARCHAR(255)            NOT NULL,
  messageType VARCHAR(255)            NOT NULL,
  pubDate     DATETIME,
  title       VARCHAR(255),
  person_id   NUMERIC(19)             NOT NULL
);
CREATE TABLE Person
(
  id     NUMERIC(19) PRIMARY KEY NOT NULL,
  name   VARCHAR(255),
  ldapId VARCHAR(255)            NOT NULL
);
ALTER TABLE Message ADD FOREIGN KEY (person_id) REFERENCES Person (id);