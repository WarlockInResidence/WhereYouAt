CREATE TABLE containers
(
    id   serial primary key ,
    type VARCHAR(32) NOT NULL,
    room varchar(32) NOT NULL,
    description varchar(255),
    height integer,
    width integer,
    length integer
);

INSERT INTO containers(type, room, description)
VALUES ('dresser','closet','the things go here')