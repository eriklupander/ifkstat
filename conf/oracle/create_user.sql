DROP user ifkstat CASCADE;
CREATE USER ifkstat PROFILE DEFAULT IDENTIFIED BY ifkstat DEFAULT TABLESPACE USERS TEMPORARY TABLESPACE TEMP;
GRANT CONNECT, RESOURCE, CREATE VIEW TO ifkstat;
alter user ifkstat identified by ifkstat;