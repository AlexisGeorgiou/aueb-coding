--CREATE LOCATION TABLE
CREATE TABLE Location AS(
	SELECT DISTINCT id, street, neighbourhood, neighbourhood_cleansed, city,
					state, zipcode, market, smart_location, country_code,
					country, latitude, longitude, is_location_exact FROM listings);

--ADD FOREIGN/PRIMARY KEYS
ALTER TABLE Location
ADD CONSTRAINT pk_lid
PRIMARY KEY (id);

ALTER TABLE Location
ADD CONSTRAINT fk_lid
FOREIGN KEY (id) REFERENCES Listings(id);

ALTER TABLE LOCATION
ADD CONSTRAINT fk_lneighbourhood
FOREIGN KEY (neighbourhood_cleansed) REFERENCES Neighbourhoods(neighbourhood);

--RENAME ID TO LISTING ID
ALTER TABLE Location
RENAME COLUMN id TO listing_id;

--DELETE OLD LOCATION COLUMNS FROM LISTINGS
ALTER TABLE New_Listings
	DROP street, DROP neighbourhood, DROP neighbourhood_cleansed, DROP city, DROP state,
	DROP zipcode, DROP market, DROP smart_location, DROP country_code, DROP country, DROP latitude,
	DROP longitude, DROP is_location_exact;

--DELETE FOREIGN KEY CONSTRAINT IN LISTINGS
ALTER TABLE New_Listings
		DROP CONSTRAINT fk_new_neighbourhood;
