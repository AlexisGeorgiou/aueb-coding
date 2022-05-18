-- EXERCISE INDEX 1

CREATE INDEX By_host_id_Index ON New_Listing(id, host_id);


-- EXERCISE INDEX 2

CREATE INDEX By_price_Index ON Price(listing_id, price$);
CREATE INDEX By_price_partialIndex ON Price(listing_id, price$) WHERE price$ > 40;
CREATE INDEX By_price_guests_Index ON Price(listing_id, price$, guests_included);


--Our INDEX 1

CREATE INDEX By_is_superhost_Index ON Host(id, is_superhost);
CREATE INDEX By_is_superhost_partialIndex ON Host(id, is_superhost) WHERE Host.is_superhost = 'true';


--Our INDEX 2

CREATE INDEX By_listing_id_Index ON Room(listing_id);
CREATE INDEX By_amenity_id_Index ON Room_amenities(listing_id, amenity_id);


--Our INDEX 3
CREATE INDEX By_coordinates_Index ON Geolocation(properties_neighbourhood, geometry_coordinates_0_0_60_0);
CREATE INDEX By_neighbourhood_Index ON Location(listing_id, neighbourhood_cleansed);



--Our INDEX 4

Δεν υπάρχουν indexes που βοηθούν στην βελτιστοποίηση της επερωτησης


--Our INDEX 5

CREATE INDEX By_comments_Index ON Review USING GIN(to_tsvector('english', comments));



