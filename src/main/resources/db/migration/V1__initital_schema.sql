CREATE TABLE containers
(
    id   serial,
    type VARCHAR(32) NOT NULL,
    room varchar(32) NOT NULL,
    description varchar(255),
    height integer,
    width integer,
    length integer,
    PRIMARY KEY (id)
);