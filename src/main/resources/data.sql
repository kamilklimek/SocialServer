INSERT INTO users(id, name, last_name, email, password) values(1, 'ASD', 'QWE', 'admin1@gmail.com', '$2a$12$dkhW8b6sjDazrmXtmuG2Ae9nCdjrru9AfKBNT.po3/qIV1Sd2KAsa');
INSERT INTO users(id, name, last_name, email, password) values(2, 'ASD2', 'QWE2', 'admin2@gmail.com', '$2a$12$dkhW8b6sjDazrmXtmuG2Ae9nCdjrru9AfKBNT.po3/qIV1Sd2KAsa');
INSERT INTO users(id, name, last_name, email, password) values(3, 'ASD3', 'QWE3', 'admin3@gmail.com', '$2a$12$dkhW8b6sjDazrmXtmuG2Ae9nCdjrru9AfKBNT.po3/qIV1Sd2KAsa');

INSERT INTO events(id, name, location, date, description, type, max_participants) values(1, 'Lets walk together', 'Antwerp/Belgium', '2019-03-14 08:00:00', 'Event for everyone', 'WALK', 10);
INSERT INTO events(id, name, location, date, description, type, max_participants) values(2, 'Lets run together', 'Antwerp/Belgium', '2019-03-15 08:00:00', 'Event for everyone', 'RUN', 3);
INSERT INTO events(id, name, location, date, description, type, max_participants) values(3, 'Lets spend time together', 'Antwerp/Belgium', '2019-03-12 06:00:00', 'Event for everyone', 'BIKE', 5);
INSERT INTO events(id, name, location, date, description, type, max_participants) values(4, 'Lets play together', 'Antwerp/Belgium', '2019-03-13 09:00:00', 'Event for everyone', 'FOOTBALL', 22);

INSERT INTO events_invite_links(id, owner, event, unique_link) values(1, 2, 1, 'gh7rkzlq');
INSERT INTO events_invite_links(id, owner, event, unique_link) values(2, 2, 2, 'vq123h');
INSERT INTO events_invite_links(id, owner, event, unique_link) values(3, 3, 3, 'ga123dzq');
INSERT INTO events_invite_links(id, owner, event, unique_link) values(4, 1, 2, 'gqwe3dzq');

INSERT INTO followers_relations(id, follower, followed) values(1, 1, 2);
INSERT INTO followers_relations(id, follower, followed) values(2, 2, 3);
INSERT INTO followers_relations(id, follower, followed) values(3, 1, 3);
INSERT INTO followers_relations(id, follower, followed) values(4, 3, 1);
