
INSERT INTO TICKETING.USER (loginId, firstName, lastName) VALUES ('prajput', 'Prishi', 'Rajput');
INSERT INTO TICKETING.USER (loginId, firstName, lastName) VALUES ('ssingh', 'Shivangi', 'Singh');
INSERT INTO TICKETING.USER (loginId, firstName, lastName) VALUES ('drajput', 'Deependra', 'Rajput');


INSERT INTO TICKETING.ISSUE (title, description, status, reporter, created)
VALUES (
    'Issue number one',
    'This is issue number one',
    'backlog',
    (select id from TICKETING.USER where USER.loginId = 'prajput'),
    '2017-08-01'
);

INSERT INTO TICKETING.ISSUE (title, description, status, reporter, created)
VALUES (
  'Issue number two',
  'This is issue number two',
  'backlog',
  (select id from TICKETING.USER where USER.loginId = 'ssingh'),
  '2017-08-01'
);

INSERT INTO TICKETING.ISSUE (title, description, status, reporter, assignee, created, completed)
VALUES (
  'Issue number three',
  'This is issue number three',
  'done',
  (select id from TICKETING.USER where USER.loginId = 'ssingh'),
  (select id from TICKETING.USER where USER.loginId = 'ssingh'),
  '2017-07-22',
  '2017-08-02'
);

INSERT INTO TICKETING.ISSUE (title, description, status, reporter, assignee, created, completed)
VALUES (
  'Issue number four',
  'This is issue number four',
  'done',
  (select id from TICKETING.USER where USER.loginId = 'prajput'),
  (select id from TICKETING.USER where USER.loginId = 'ssingh'),
  '2017-08-02',
  '2017-08-02'
);

INSERT INTO TICKETING.ISSUE (title, description, status, reporter, assignee, created)
VALUES (
  'Issue number five',
  'This is issue number five',
  'in_progress',
  (select id from TICKETING.USER where USER.loginId = 'ssingh'),
  (select id from TICKETING.USER where USER.loginId = 'drajput'),
  '2017-08-02'
);
