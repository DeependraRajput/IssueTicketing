CREATE SCHEMA TICKETING;

CREATE TABLE TICKETING.USER (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY,
  login_id VARCHAR(9) NOT NULL,
  email VARCHAR(25) NOT NULL,
  first_name VARCHAR(15) NOT NULL,
  middle_name VARCHAR(15),
  last_name VARCHAR(15) NOT NULL,
  type VARCHAR(5) DEFAULT 'USER' NOT NULL,
  updated_at TIMESTAMP NOT NULL
);

CREATE TABLE TICKETING.USER_SECURITY (
  id BIGINT,
  password VARCHAR(100),
  created_at TIMESTAMP NOT NULL,
  last_login_at TIMESTAMP
);

CREATE TABLE TICKETING.ISSUE (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY,
  title VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  status VARCHAR(20) NOT NULL,
  reporter_id BIGINT NOT NULL,
  assignee_id BIGINT,
  created_at TIMESTAMP NOT NULL,
  completed_at TIMESTAMP
);

CREATE TABLE TICKETING.COMMENT (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY,
  issue_id BIGINT NOT NULL,
  text VARCHAR(255) NOT NULL,
  commentator_id BIGINT NOT NULL,
  created_at TIMESTAMP NOT NULL,
  last_modified_at TIMESTAMP NOT NULL
);

ALTER TABLE TICKETING.USER_SECURITY
ALTER COLUMN created_at
SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE TICKETING.USER
ALTER COLUMN updated_at
SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE TICKETING.COMMENT
ALTER COLUMN created_at
SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE TICKETING.COMMENT
ALTER COLUMN last_modified_at
SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE TICKETING.USER_SECURITY
ADD FOREIGN KEY (id)
REFERENCES TICKETING.USER(id);

ALTER TABLE TICKETING.COMMENT
ADD FOREIGN KEY (issue_id)
REFERENCES TICKETING.ISSUE(id);

ALTER TABLE TICKETING.ISSUE
ADD FOREIGN KEY (reporter_id)
REFERENCES TICKETING.USER(id);

ALTER TABLE TICKETING.ISSUE
ADD FOREIGN KEY (assignee_id)
REFERENCES TICKETING.USER(id);

INSERT INTO TICKETING.USER (login_id, first_name, last_name, email) VALUES ('prajput', 'Prishi', 'Rajput', 'prishi@rajput.me');
INSERT INTO TICKETING.USER (login_id, first_name, last_name, email) VALUES ('ssingh', 'Shivangi', 'Singh', 'shivanig@singh.me');
INSERT INTO TICKETING.USER (login_id, first_name, last_name, email, type) VALUES ('drajput', 'Deependra', 'Rajput', 'deependra@rajput.me', 'ADMIN');


INSERT INTO TICKETING.ISSUE (title, description, status, reporter_id, created_at)
VALUES (
    'Issue number one',
    'This is issue number one',
    'IN_PROGRESS',
    (select id from TICKETING.USER where USER.login_id = 'prajput'),
    '2017-08-01'
);

INSERT INTO TICKETING.ISSUE (title, description, status, reporter_id, created_at)
VALUES (
  'Issue number two',
  'This is<Script> issue </Script>number two',
  'IN_PROGRESS',
  (select id from TICKETING.USER where USER.login_id = 'ssingh'),
  '2017-08-01'
);

INSERT INTO TICKETING.ISSUE (title, description, status, reporter_id, assignee_id, created_at, completed_at)
VALUES (
  'Issue number three',
  'This is issue number three',
  'COMPLETED',
  (select id from TICKETING.USER where USER.login_id = 'ssingh'),
  (select id from TICKETING.USER where USER.login_id = 'ssingh'),
  '2017-07-22',
  '2017-08-02'
);

INSERT INTO TICKETING.ISSUE (title, description, status, reporter_id, assignee_id, created_at, completed_at)
VALUES (
  'Issue number four',
  'This is issue number four',
  'COMPLETED',
  (select id from TICKETING.USER where USER.login_id = 'prajput'),
  (select id from TICKETING.USER where USER.login_id = 'ssingh'),
  '2017-08-02',
  '2017-08-02'
);

INSERT INTO TICKETING.ISSUE (title, description, status, reporter_id, assignee_id, created_at)
VALUES (
  'Issue number five',
  'This is issue number five',
  'IN_PROGRESS',
  (select id from TICKETING.USER where USER.login_id = 'ssingh'),
  (select id from TICKETING.USER where USER.login_id = 'drajput'),
  '2017-08-02'
);

INSERT INTO TICKETING.COMMENT (issue_id, text, commentator_id)
VALUES (
  1,
  'This is first comment',
  1
);

INSERT INTO TICKETING.COMMENT (issue_id, text, commentator_id)
VALUES (
  1,
  'This is second comment',
  1
);

INSERT INTO TICKETING.COMMENT (issue_id, text, commentator_id)
VALUES (
  1,
  'This is third comment',
  1
);

INSERT INTO TICKETING.COMMENT (issue_id, text, commentator_id)
VALUES (
  1,
  'This is fourth comment',
  1
);
