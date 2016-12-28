create table if not Exists Pets(	
id int not null  auto_increment primary key,
petName varchar(20),
masterName varchar(32),
birthday date,
price int,
weight double,
filename varchar(60),
picture longblob ,
comment longblob
)  ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;