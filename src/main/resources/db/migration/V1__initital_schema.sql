DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS containers;
DROP TABLE IF EXISTS items;

CREATE TABLE rooms
(
    id          serial primary key,
    name        varchar(32) NOT NULL UNIQUE,
    description varchar(140),
    height      integer,
    width       integer,
    length      integer
);

CREATE TABLE containers
(
    id          serial primary key,
    room_id     int         NOT NULL references rooms ("id") ON DELETE CASCADE,
    type        VARCHAR(32) NOT NULL,
    description varchar(140),
    height      integer,
    width       integer,
    length      integer

);

CREATE TABLE items
(
    id           serial PRIMARY KEY,
    container_id int         not null references containers (id),
    category     VARCHAR(32) NOT NULL,
    type         VARCHAR(32),
    occasion     varchar(32),
    length       varchar(32),
    color        varchar(32)
);

INSERT INTO rooms(id, name, description, height, width, length)
VALUES ('1', 'papas closet', 'a place where clothing is kept', 108, 96, 84),
       ('2', 'moms closet', 'All her stuff goes here', 108, 96, 84),
       ('3', 'kitchen', 'a place where clothing is kept', 108, 96, 84);

INSERT INTO containers(room_id, type, description)
VALUES ('1', 'dresser', 'the things go here'),
       ('1', 'shelf', 'the things go here'),
       ('1', 'bin', 'the things go here');

INSERT INTO items(container_id, category, type, occasion, length, color)
VALUES (1, 'clothing', 'shirt', 'dress', 'long-sleeve', 'blue'),
       (1, 'clothing', 'shirt', 'dress', 'long-sleeve', 'red'),
       (1, 'clothing', 'shirt', 'workout', 'long-sleeve', 'orange'),
       (1, 'clothing', 'shirt', 'workout', 'long-sleeve', 'green')