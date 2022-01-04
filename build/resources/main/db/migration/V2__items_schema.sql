-- drop all the tables while testing
DROP TABLE IF EXISTS items;


CREATE TABLE items
(
    id            serial PRIMARY KEY,
    containers_id int not null default 1,
    category      VARCHAR(32) NOT NULL,
    type          VARCHAR(32),
    occasion      varchar(32),
    length        varchar(32),
    color         varchar(32),
    CONSTRAINT fk_containers FOREIGN KEY(containers_id) REFERENCES containers(id)
);

INSERT INTO items(containers_id, category, type, occasion, length, color)
VALUES (1,'clothing', 'shirt', 'dress', 'long-sleeve', 'blue')