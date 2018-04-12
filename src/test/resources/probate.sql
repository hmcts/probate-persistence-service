CREATE DATABASE probate;
CREATE USER 'probate'@'localhost' IDENTIFIED BY 'password123';
GRANT ALL PRIVILEGES ON probate . * TO 'probate'@'localhost';
