CREATE TABLE Price
	AS (SELECT DISTINCT id, price, weekly_price, monthly_price, security_deposit, cleaning_fee,  guests_included, extra_people,
						minimum_nights, maximum_nights, minimum_minimum_nights, maximum_minimum_nights,
	   		   			minimum_maximum_nights, maximum_maximum_nights, minimum_nights_avg_ntm, maximum_nights_avg_ntm FROM Listings);
						
--UPDATE PRICE CONTENTS
UPDATE PRICE
SET price = CAST(REPLACE(REPLACE(price, '$', ''), ',', '') AS float), 
	weekly_price = CAST(REPLACE(REPLACE(weekly_price, '$', ''), ',', '') AS float),
	monthly_price = CAST(REPLACE(REPLACE(monthly_price, '$', ''), ',', '') AS float), 
	security_deposit = CAST(REPLACE(REPLACE(security_deposit, '$', ''), ',', '') AS float), 
	cleaning_fee = CAST(REPLACE(REPLACE(cleaning_fee, '$', ''), ',', '') AS float), 
	extra_people = CAST(REPLACE(REPLACE(extra_people, '$', ''), ',', '') AS float);


	
--CHANGE COLUMN TYPES

	
ALTER TABLE Price
ALTER COLUMN price type float USING price::double precision;

ALTER TABLE Price
ALTER COLUMN weekly_price type float USING weekly_price::double precision;

ALTER TABLE Price
ALTER COLUMN monthly_price type float USING monthly_price::double precision;

ALTER TABLE Price
ALTER COLUMN security_deposit type float USING security_deposit::double precision;

ALTER TABLE Price
ALTER COLUMN cleaning_fee type float USING cleaning_fee::double precision;

ALTER TABLE Price
ALTER COLUMN extra_people type float USING extra_people::double precision;

--CHANGE COLUMN NAMES TO MATCH CURRENCY

ALTER TABLE Price
RENAME COLUMN price TO price$;

ALTER TABLE Price
RENAME COLUMN weekly_price TO weekly_price$;

ALTER TABLE Price
RENAME COLUMN monthly_price TO monthly_price$;

ALTER TABLE Price
RENAME COLUMN security_deposit TO security_deposit$;

ALTER TABLE Price
RENAME COLUMN cleaning_fee TO cleaning_fee$;

ALTER TABLE Price
RENAME COLUMN extra_people TO extra_people$;
						
--ADD FOREIGN/PRIMARY KEYS
ALTER TABLE Price
ADD CONSTRAINT pk_pid
PRIMARY KEY (id);

ALTER TABLE Price
ADD CONSTRAINT fk_fid
FOREIGN KEY (id) REFERENCES New_Listings(id);

--RENAME ID TO LISTING ID
ALTER TABLE Price
RENAME COLUMN id TO listing_id;

--DELETE OLD PRICE COLUMNS FROM LISTINGS
ALTER TABLE New_Listings
	DROP cleaning_fee, DROP guests_included, DROP extra_people,
	DROP minimum_nights, DROP maximum_nights, DROP minimum_minimum_nights,
	DROP maximum_minimum_nights, DROP minimum_maximum_nights, DROP maximum_maximum_nights,
	DROP minimum_nights_avg_ntm, DROP maximum_nights_avg_ntm;