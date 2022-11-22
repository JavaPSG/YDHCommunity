CREATE TABLE member(
	name VARCHAR2(200),
	introduce VARCHAR2(200),
	email VARCHAR2(200),
	password VARCHAR2(200),
	white_theme NUMBER,
	last_connect_time NUMBER,
	point NUMBER,
	posts VARCHAR2(200)
); 

select * from member
