
create table customers
(customer_id numeric primary key,  
 sex  int, 
 dob  datetime, 
); 

insert into customers select distinct customer_id, max(sex), max(dob) from main group by customer_id 


create table products
(product_id numeric primary key, 
 manufacturer_code int, 
 brand_code numeric, 
 cat2 int, 
 cat4 int, 
 cat6 int
 );

 
 insert into products select distinct product_id, manufacturer_code, brand_code, cat2, cat4,cat6 from main


create table stores
(storecode   numeric primary key,
 location_code int
 );

 insert into stores select distinct storecode, location_code from main


create table timeinfo
(time_key  datetime primary key,
 t_year int,
 t_month int,
 t_dayofmonth int, 
 t_hour int,
 t_quarter int, 
 t_week int,
 t_dayofyear int,
 t_dayofweek int
 ); 
 
set datefirst 1;
insert into timeinfo select distinct dtm, datepart(year, dtm), datepart(month, dtm), datepart(day,dtm),datepart(hour, dtm), 
                datepart(quarter,dtm), datepart(week,dtm),datepart(dayofyear,dtm),datepart(dw,dtm)
 from main order by dtm

 
  

create table sales
(   receipt_no int,
    customer_id numeric ,
	time_key datetime,
	product_id numeric,
    storecode   numeric,    
    quantity  int,
    cost numeric, 
    discount numeric,
    profit numeric, 
    
 primary key(receipt_no,customer_id,time_key,product_id,storecode),
 foreign key (customer_id) references customers(customer_id),
 foreign key (time_key) references timeinfo(time_key), 
 foreign key (product_id) references products(product_id),
 foreign key (storecode) references stores(storecode), 
 );


INSERT INTO Sales
SELECT  receipt_no, customer_id, dtm,product_id, storecode, sum(quantity),  sum(cost),  sum(discount), sum(profit) 
   from main  
GROUP BY  receipt_no,customer_id,dtm,  product_id, storecode


