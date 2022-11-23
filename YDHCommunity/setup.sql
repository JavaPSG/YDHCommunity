CREATE TABLE member(name VARCHAR2(200),introduce VARCHAR2(200),email VARCHAR2(200),password VARCHAR2(200),white_theme NUMBER,last_connect_time NUMBER,point NUMBER,posts VARCHAR2(200)); 

select * from member

--drop 명령어 실행 후 서버 재시작 필요합니다!
drop table member

--DB 연결 정보
--Host: database-1.cqwvi7bi8mih.ap-northeast-1.rds.amazonaws.com
--Port: 1521
--SID: ORCL
--User name: admin_ys
--Password: m31j1gnmd0210n3ef2

CREATE TABLE post(writer VARCHAR2(200), title VARCHAR2(200), content VARCHAR2(200), recommanders VARCHAR2(200), write_time NUMBER, uuid VARCHAR2(200)); 

select * from  post
