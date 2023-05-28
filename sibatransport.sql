CREATE DATABASE sibatransport;
USE sibatransport;

CREATE TABLE admins (
  admin_id BIGINT PRIMARY KEY,
  password VARCHAR(20)  
  );

 select * from admins;
  
CREATE TABLE users (
  user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(20) unique,
  password VARCHAR(20), 
  isBooked boolean default 0
  );
  
  
select * from users;
  
  
CREATE TABLE points (
  p_id BIGINT unique PRIMARY KEY,
  capacity INT,
  booked_seats INT,
  available_seats INT,
  origin VARCHAR(20),
  destination VARCHAR(300),
  a_time VARCHAR(8),
  d_time VARCHAR(80)
  
  );
  
select * from p	oints;
  