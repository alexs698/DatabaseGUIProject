Create database Music_Store;
use Music_Store;
create table Musicians(
	ssn char(10),
    name char(30) not null,
    primary key(ssn));
    
create table Instruments(
	instrId char(10),
    dname char(30),
    songKey char(5),
    primary key(instrId));
    
create table Plays( 
	ssn Char(10),
	instrId char(10),
    primary key(ssn, instrId),
    foreign key(ssn) references Musicians(ssn),
    foreign key(instrId) references Instruments(instrId));
    
CREATE TABLE Album_Producer (
    albumIdentifier INTEGER,
    ssn CHAR(10),
    copyrightDate DATE,
    speed INTEGER,
    title CHAR(30),
    PRIMARY KEY(albumIdentifier),
    FOREIGN KEY(ssn) REFERENCES Musicians(ssn)
);
    
create table Telephone_Home(
	phone char(11),
    address char(30) unique,
    primary key(phone),
    foreign key(address) references Places(address));

create table Lives(
	ssn char(10),
    phone char(11),
    address char(30),
    primary key(ssn, address),
    foreign key(phone) references Telephone_Home(phone),
    foreign key(ssn) references Musicians(ssn),
    foreign key(address) references Places(address));
 
 create table Places(address char(30) primary key);
 
 create table Perform(
	songId integer,
    ssn char(10),
    primary key(ssn, songId),
    foreign key(songId) references Song_Appears(songId),
    foreign key(ssn) references Musicians(ssn));
    
CREATE TABLE Song_Appears (
    songId INTEGER,
    author CHAR(30),
    title CHAR(30),
    albumIdentifier INTEGER NOT NULL,
    PRIMARY KEY(songId),
    FOREIGN KEY(albumIdentifier) REFERENCES Album_Producer(albumIdentifier)
);

insert into Musicians (name, ssn)
values ('Blink-182', '1111'), ('Metallica', '2222'), ('Kendrick Lamar', '3333'), ('Drake', '4444'), ('Taylor Swift', '5555');

insert into Album_Producer (albumIdentifier, title, copyrightDate, ssn)
values ('1', 'Cheshire Cat', '1995-2-17', '1111'), ('2', 'Dude Ranch', '1997-6-17', '1111'), ('3', 'Enema of the State', '1999-6-1', '1111'), ('4','Take Off Your Pants and Jacket', '2001-6-12', '1111'), ('5', 'Blink-182', '2003-11-18', '1111'),
	('6', 'Kill Em All', '1983-7-25', '2222'), ('7', 'Ride the Lightning', '1984-7-27', '2222'), ('8', 'Master of Puppets', '1986-3-3', '2222'), ('9', '...And Justice for All', '1988-9-7', '2222'), ('10', 'Metallica (The Black Album)', '1991-8-12', '2222'),
    ('11', 'Section .80', '2011-7-2', '3333'), ('12', 'Good Kid, M.A.A.D City', '2012-10-22', '3333'), ('13', 'To Pimp a Butterfly', '2015-3-16', '3333'), ('14', 'Damn', '2017-4-14', '3333'), ('15', 'Mr. Morale & the Big Steppers', '2022-5-13', '3333'),
    ('16', 'Thank Me Later', '2010-6-15', '4444'), ('17', 'Take Care', '2011-11-15', '4444'), ('18', 'Nothing Was the Same', '2013-9-24', '4444'), ('19', 'Views', '2016-4-29', '4444'), ('20', 'Scorpion', '2018-6-29', '4444'),
    ('21', 'Taylor Swift', '2006-10-24', '5555'), ('22', 'Fearless', '2008-11-11', '5555'), ('23', 'Speak Now', '2010-10-25', '5555'), ('24', 'Red', '2012-10-25', '5555'), ('25', '1989', '2014-10-27', '5555');
insert into Song_Appears (songId, author, title, albumIdentifier)
values ('1', 'Blink-182', 'Carousel', '1'), ('2', 'Blink-182', 'Dammit', '2'), ('3', 'Blink-182', 'All The Small Things', '3'), ('4', 'Blink-182', 'First Date', '4'), ('5', 'Blink-182', 'I Miss You', '5'),
	('6', 'Metallica', 'Seek and Destroy', '6'), ('7', 'Metallica', 'Creeping Death', '7'), ('8', 'Metallica', 'Battery', '8'), ('9', 'Metallica', 'One', '9'), ('10', 'Metallica', 'Enter Sandman', '10'),
    ('11', 'Kendrick Lamar', 'A.D.H.D', '11'), ('12', 'Kendrick Lamar', 'Swimming Pools(Drank)', '12'), ('13', 'Kendrick Lamar', 'Alright', '13'), ('14', 'Kendrick Lamar', 'DNA', '14'), ('15', 'Kendrick Lamar', 'United in Grief', '15'),
    ('16', 'Drake', 'Fancy', '16'), ('17', 'Drake', 'Marvins Room', '17'), ('18', 'Drake', 'Started From The Bottom', '18'), ('19', 'Drake', 'Hotline Bling', '19'), ('20', 'Drake', 'Gods Plan', '20'),
    ('21', 'Taylor Swift', 'Teardrops on my Guitar', '21'), ('22', 'Taylor Swift', 'Love Story', '22'), ('23', 'Taylor Swift', 'Mean', '23'), ('24', 'Taylor Swift', '22', '24'), ('25', 'Taylor Swift', 'Blank Space', '25');
insert into Instruments (instrId, dname, songKey)
values ('1', 'Guitar', 'D'), ('2', 'Guitar', 'C'), ('3', 'Guitar', 'C'), ('4', 'Guitar', 'C'), ('5', 'Guitar', 'B'),
	('6', 'Guitar', 'Em'), ('7', 'Guitar', 'Em'), ('8', 'Guitar', 'Em'), ('9', 'Guitar', 'Bm'), ('10', 'Guitar', 'Em'),
    ('11', 'Vocals', 'A'), ('12', 'Vocals', 'C#'), ('13', 'Vocals', 'G'), ('14', 'Vocals', 'C#'), ('15', 'Vocals', 'C#'),
    ('16', 'Vocals', 'Cm'), ('17', 'Vocals', 'C'), ('18', 'Vocals', 'G#'), ('19', 'Vocals', 'Cm'), ('20', 'Vocals', 'G'),
    ('21', 'Vocals', 'A#'), ('22', 'Vocals', 'D'), ('23', 'Vocals', 'E'), ('24', 'Vocals', 'G'), ('25', 'Vocals', 'F');
insert into Plays (ssn, instrId)
values ('1111', '1'), ('1111', '2'), ('1111', '3'), ('1111', '4'), ('1111', '5'),
	('2222', '6'), ('2222', '7'), ('2222', '8'), ('2222', '9'), ('2222', '10'),
    ('3333', '11'), ('3333', '12'), ('3333', '13'), ('3333', '14'), ('3333', '15'),
    ('4444', '16'), ('4444', '17'), ('4444', '18'), ('4444', '19'), ('4444', '20'),
    ('5555', '21'), ('5555', '22'), ('5555', '23'), ('5555', '24'), ('5555', '25');
insert into Places (address) values ('157 Yonder Ave'), ('257 Amazon Road'), ('777 Lucky Blvd'), ('888 Orange Road'), ('369 Infinite Lane');
insert into Telephone_Home (phone, address)
values ('7795371453', '157 Yonder Ave'), ('5486347766', '257 Amazon Road'), ('2135678136', '777 Lucky Blvd'), ('2224446666', '888 Orange Road'), ('1346798521', '369 Infinite Lane');
insert into Lives (ssn, phone, address)
values ('1111', '7795371453', '157 Yonder Ave'), ('2222', '5486347766', '257 Amazon Road'), ('3333', '2135678136', '777 Lucky Blvd'), ('4444', '2224446666', '888 Orange Road'), ('5555', '1346798521', '369 Infinite Lane');
insert into Perform (songId, ssn)
values ('1', '1111'), ('2', '1111'), ('3', '1111'), ('4', '1111'), ('5', '1111'),
	('6', '2222'), ('7', '2222'), ('8', '2222'), ('9', '2222'), ('10', '2222'),
    ('11', '3333'), ('12', '3333'), ('13', '3333'), ('14', '3333'), ('15', '3333'),
    ('16', '4444'), ('17', '4444'), ('18', '4444'), ('19', '4444'), ('20', '4444'),
    ('21', '5555'), ('22', '5555'), ('23', '5555'), ('24', '5555'), ('25', '5555');
select * from Musicians; 
select * from Album_Producer;
select * from Song_Appears;
select * from Places;
select * from Telephone_Home;
select * from Instruments;
select * from Plays;
select * from Lives;
select * from Perform;

    