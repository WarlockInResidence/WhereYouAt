-- drop all the tables while testing
DROP TABLE IF EXISTS containers;


CREATE TABLE containers
(
    id          serial,
    name        VARCHAR(32) NOT NULL,
    type        VARCHAR(32) NOT NULL,
    room        varchar(32) NOT NULL,
    description varchar(255),
    height      float,
    width       float,
    length      float,
    PRIMARY KEY (id)
);

INSERT INTO containers(name, type, room, description, height, width, length) VALUES ('mil stuff','box', 'closet','Items for turn in at ETS',14,17.5,37)