
Create table main
(storecode   numeric, 
 customer_id numeric,  
 receipt_no int,
 quantity  int,  
 manufacturer_code int, 
 brand_code numeric, 
 cat2 int, 
 cat4 int, 
 cat6 int,
 dob  datetime, 
 sex  int, 
 cost numeric, 
 discount numeric,
 profit numeric, 
 product_id numeric, 
 location_code int, 
 dtm datetime
);

BULK INSERT main
 FROM 'C:\DATAWAREHOUSE\OLTP.TXT'
WITH (DATAFILETYPE = 'widechar', FIRSTROW =2, FIELDTERMINATOR= '|', ROWTERMINATOR = '\n');