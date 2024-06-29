CREATE TABLE IF NOT EXISTS hui_yuan(
id INT PRIMARY KEY auto_increment,
phone VARCHAR(50),
name VARCHAR(50),
amount NUMERIC,
charge_date VARCHAR(50),
left_time VARCHAR(10),
score VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS audit_log(
id INT PRIMARY KEY auto_increment,
phone VARCHAR(50),
name VARCHAR(50),
amount NUMERIC,
charge_date VARCHAR(50),
left_time VARCHAR(10),
score VARCHAR(10),
audit_date VARCHAR(50)
);