DROP DATABASE if exists NotesDB;

CREATE DATABASE NotesDB;

USE NotesDB;

DROP TABLE Notes;

CREATE TABLE Notes(
    noteId INT NOT NULL AUTO_INCREMENT,
    dateCreated DATETIME NOT NULL,
    contents VARCHAR(10000) character set utf8 NOT NULL,
    PRIMARY KEY (noteId)
);

DROP TABLE User;

CREATE TABLE User( 
    username VARCHAR(10) NOT NULL,
    password VARCHAR(10) NOT NULL,
    email VARCHAR(30) NOT NULL,
    active BIT NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    PRIMARY KEY (username)
);

ALTER TABLE Notes AUTO_INCREMENT=1;

INSERT INTO User values('admin', 'password', 'test@test.com', 1, 'Bob', 'Bobberson');
INSERT INTO Notes (dateCreated, contents) values(CURDATE(), 'note1 content');
INSERT INTO Notes (dateCreated, contents) values(CURDATE(), 'note2 content');