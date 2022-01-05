-- drop all the tables while testing
DROP TABLE IF EXISTS items;


CREATE TABLE items
(
    id            serial PRIMARY KEY,
    container_id int not null references containers(id),
    category      VARCHAR(32) NOT NULL,
    type          VARCHAR(32),
    occasion      varchar(32),
    length        varchar(32),
    color         varchar(32)
);

INSERT INTO items(container_id, category, type, occasion, length, color)
VALUES (1,'clothing', 'shirt', 'dress', 'long-sleeve', 'blue')