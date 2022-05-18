--CREATE ROOM TABLE
CREATE TABLE Room
	AS (SELECT DISTINCT id, accommodates, bathrooms, bedrooms,
	   		   beds, bed_type, amenities, square_feet, price
	   		   price, weekly_price, monthly_price,
	   		   security_deposit FROM Listings);
			
--ADD FOREIGN/PRIMARY KEYS
ALTER TABLE Room
ADD CONSTRAINT pk_room_id
PRIMARY KEY(id);

ALTER TABLE Room
ADD CONSTRAINT fk_room_id
FOREIGN KEY(id) REFERENCES New_Listings(id);

--RENAME ID TO LISTING_ID
ALTER TABLE Room
RENAME COLUMN id TO listing_id;

--DELETE OLD ROOM COLUMNS FROM LISTINGS
ALTER TABLE New_Listings
	DROP accommodates, DROP bathrooms,DROP  bedrooms, DROP beds, DROP bed_type,DROP amenities,
	DROP square_feet, DROP  price, DROP weekly_price,DROP  monthly_price, DROP security_deposit;
	
