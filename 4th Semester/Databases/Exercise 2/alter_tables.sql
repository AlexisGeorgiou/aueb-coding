ALTER TABLE Listings
ALTER COLUMN license type varchar(1000);
	
	
ALTER TABLE Listings
ALTER COLUMN neighbourhood_cleansed type varchar(40);
	
ALTER TABLE Listings
ADD CONSTRAINT pk_id
PRIMARY KEY (id);
	
ALTER TABLE Listings
ADD CONSTRAINT fk_neighbourhood_cleansed 
FOREIGN KEY (neighbourhood_cleansed) REFERENCES Neighbourhoods(neighbourhood);

ALTER TABLE Listings_summary
ADD CONSTRAINT pk_summary_id
PRIMARY KEY (id);

ALTER TABLE Listings
ADD CONSTRAINT fk_id
FOREIGN KEY (id) REFERENCES Listings_summary(id);

ALTER TABLE Listings_summary
ADD CONSTRAINT fk_summary_id
FOREIGN KEY (id) REFERENCES Listings(id);

ALTER TABLE Reviews
ADD CONSTRAINT pk_id
PRIMARY KEY (id);

ALTER TABLE Reviews
ADD CONSTRAINT fk_listing_id
FOREIGN KEY (listing_id) REFERENCES Listings(id);

ALTER TABLE Geolocation
ADD CONSTRAINT pk_properties_neighbourhood
PRIMARY KEY (properties_neighbourhood);


ALTER TABLE Neighbourhoods
ADD CONSTRAINT pk_neighbourhood
PRIMARY KEY (neighbourhood);

ALTER TABLE Geolocation
ADD CONSTRAINT fk_properties_neighbourhood
FOREIGN KEY (properties_neighbourhood) REFERENCES Neighbourhoods(neighbourhood);

ALTER TABLE Neighbourhoods
ADD CONSTRAINT fk_neighbourhood
FOREIGN KEY (neighbourhood) REFERENCES Geolocation(properties_neighbourhood);

ALTER TABLE Calendar
ADD CONSTRAINT pk_listing_id__date
PRIMARY KEY (listing_id, date);

ALTER TABLE Calendar
ADD CONSTRAINT fk_listing_id
FOREIGN KEY (listing_id) REFERENCES Listings(id);

ALTER TABLE Reviews_summary
ADD CONSTRAINT fk_summary_listing_id
FOREIGN KEY (listing_id) REFERENCES Listings(id);






