--UPDATE AMENITIES COLUMN IN ROOM TABLE
UPDATE Room
SET amenities = REPLACE(REPLACE(REPLACE(amenities, '{', ''), '}', ''), '"', '');

--CREATE AMENITIES TABLE WITH AUTO INCREMENT (SERIAL)
CREATE TABLE Amenities(
	amenity_id SERIAL,
	amenity_name text
);

--INSERT AMENITY NAMES INTO AMENITY_NAME COLUMN IN AMENITIES TABLE FROM ROOM TABLE
INSERT INTO Amenities(amenity_name)
SELECT DISTINCT unnest(string_to_array(amenities, ',')) as amenity_name FROM Room;

--ADD A SPACE AFTER THE COMA IN ROOM AMENITIES 
UPDATE ROOM
SET amenities = REPLACE(amenities, ',', ', ');

--LINK ROOM WITH AMENITIES
	--CREATE INTERMEDIARY TABLE
	CREATE TABLE Room_amenities AS (
	SELECT  listing_id, amenity_id FROM Room, Amenities
	WHERE Room.amenities LIKE '%' || Amenities.amenity_name || '%');
	
	--CREATE PRIMARY/FOREIGN KEYS
	ALTER TABLE Amenities
	ADD CONSTRAINT pk_amenity_id
	PRIMARY KEY(amenity_id);

	ALTER TABLE Room_Amenities
	ADD CONSTRAINT fk_amenity_id
	FOREIGN KEY(amenity_id) REFERENCES Amenities(amenity_id);

	ALTER TABLE Room_Amenities
	ADD CONSTRAINT fk_room_id
	FOREIGN KEY(listing_id) REFERENCES Room(listing_id);

--DELETE AMENITIES COLUMN FROM ROOM TABLE
ALTER TABLE Room
DROP COLUMN amenities;
