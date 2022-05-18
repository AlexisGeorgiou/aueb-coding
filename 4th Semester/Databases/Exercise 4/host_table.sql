--CREATE HOST TABLE
CREATE TABLE Host 
	AS (SELECT DISTINCT id, host_url, host_name, host_since, host_location, host_about,
	   		   host_response_time, host_response_rate, host_acceptance_rate, host_is_superhost,
	   		   host_thumbnail_url, host_picture_url, host_neighbourhood, host_listings_count,
	   		   host_total_listings_count, host_verifications, host_has_profile_pic,
	   		   host_identity_verified FROM Listings);
			   
--REMOVE host_ PREFIX
		   
ALTER TABLE Host
RENAME COLUMN host_id TO id;

ALTER TABLE Host
RENAME COLUMN host_url TO url;

ALTER TABLE Host
RENAME COLUMN host_name TO name;

ALTER TABLE Host
RENAME COLUMN host_since TO since;

ALTER TABLE Host
RENAME COLUMN host_location TO location;

ALTER TABLE Host
RENAME COLUMN host_about TO about;

ALTER TABLE Host
RENAME COLUMN host_response_time TO response_time;

ALTER TABLE Host
RENAME COLUMN host_response_rate TO response_rate;

ALTER TABLE Host
RENAME COLUMN host_acceptance_rate TO acceptance_rate;

ALTER TABLE Host
RENAME COLUMN host_is_superhost TO is_superhost;

ALTER TABLE Host
RENAME COLUMN host_thumbnail_url TO thumbnail_url;

ALTER TABLE Host
RENAME COLUMN host_picture_url TO picture_url;

ALTER TABLE Host
RENAME COLUMN host_neighbourhood TO neighbourhood;

ALTER TABLE Host
RENAME COLUMN host_listings_count TO listings_count;

ALTER TABLE Host
RENAME COLUMN host_total_listings_count TO total_listings_count;

ALTER TABLE Host
RENAME COLUMN host_verifications TO verifications;

ALTER TABLE Host
RENAME COLUMN host_has_profile_pic TO has_profile_pice;

ALTER TABLE Host
RENAME COLUMN host_identity_verified TO identity_verified;

ALTER TABLE Host
RENAME COLUMN calculated_host_listings_count TO calculated_listings_count;

--ADD FOREIGN/PRIMARY KEYS
ALTER TABLE Host
ADD CONSTRAINT pk_host_id
PRIMARY KEY (id);

ALTER TABLE New_Listings
ADD CONSTRAINT fk_host_id
FOREIGN KEY (host_id) REFERENCES Host(id);

--DELETE OLD HOST COLUMNS FROM LISTINGS
ALTER TABLE New_Listings
DROP host_id, DROP host_url, DROP host_name, DROP host_since, DROP host_location,
			DROP host_about, DROP host_response_time, DROP host_response_rate,  DROP host_acceptance_rate,
			DROP host_is_superhost, DROP host_thumbnail_url, DROP host_picture_url, DROP host_neighbourhood,
			DROP host_listings_count, DROP host_total_listings_count, DROP host_verifications, DROP host_has_profile_pic,
			DROP host_identity_verified, DROP calculated_host_listings_count;