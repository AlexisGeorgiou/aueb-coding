create table actors
(aid int primary key, 
 firstName varchar(100),
 lastname varchar(100),
 gender char(1)
 );
 
 create table directors
(did int primary key, 
 firstName varchar(100),
 lastname varchar(100)
 );

 
 create table movies 
 (mid int primary key,
  title varchar(200),
  pyear int,
  mrank decimal(2,1)
  );

 create table movie_directors
 ( did int foreign key references directors(did),
   mid int foreign key references movies(mid) 
   primary key(mid, did), 
 );
 
 create table roles
 ( aid int foreign key references actors(aid),
   mid int foreign key references movies(mid), 
   a_role varchar(100), 
   primary key (mid,aid)
  );

  create table movies_genre
  (mid int foreign key references movies(mid),
   genre varchar(100),
   
   );


create table users 
(userid int primary key, 
 uname varchar(50), 
 gender char(1), 
 age int
 ); 


 create table user_movies
 (userid int foreign key references users(userid),  
  mid int foreign key references movies(mid),
  rating int, 
  primary key (mid,userid)
  ); 