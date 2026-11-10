-- 새로운 오라클 유저 생성
CREATE USER c##aisoft IDENTIFIED BY 1234;

-- 새로운 유저에게 [권한] 부여
GRANT CONNECT, RESOURCE, DBA TO c##aisoft; 