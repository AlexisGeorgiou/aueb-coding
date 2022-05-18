/* 
Shows the average price of their houses and the information of the superhosts.
Output: 2350 rows
*/

SELECT Host.name, Host.id, ROUND(AVG(Price.price$)) as average_price$ 
FROM Host
INNER JOIN New_Listing
ON Host.id = New_listing.host_id
INNER JOIN Price
ON Price.listing_id = New_listing.id
WHERE Host.is_superhost = 'true'
GROUP BY Host.id
ORDER BY average_price$;


/* 
Returns the count of the rooms without declared amenities.
Output: 1 row (9)
*/

SELECT DISTINCT COUNT(Room.listing_id) as Number_of_rooms_without_declared_amenities
FROM Room
FULL OUTER JOIN Room_Amenities
ON Room.listing_id = Room_Amenities.listing_id
WHERE Room_amenities.amenity_id is NULL;


/*
Shows the maximum price of the listings and the count of houses
in all neighbourhoods that have less angles than a 60-gon.
Output: 29 rows
*/
   
SELECT Location.neighbourhood_cleansed, MAX(Price.price$) as max_price, COUNT(Price.price$) as number_of_houses
FROM Location
JOIN Price
ON Price.listing_id = Location.listing_id
JOIN Geolocation
ON Location.neighbourhood_cleansed = Geolocation.properties_neighbourhood
WHERE geolocation.geometry_coordinates_0_0_60_0 is NULL
GROUP BY Location.neighbourhood_cleansed
ORDER BY COUNT(Price.price$) DESC;


/*
Shows the theoretical montly income which is the income of the host if he rented all his properties 
for every day with the price he wants. We can also see the number of houses he rents and his id and name.
Output: 48 rows
*/
  
SELECT Host.name, Host.id, SUM(Price.monthly_price$) as theoretical_monthly_income, COUNT(Price.monthly_price$) as number_of_houses
FROM Host
JOIN New_Listing
ON Host.id = New_Listing.host_id
JOIN Price
ON New_Listing.id = Price.listing_id
GROUP BY Host.id
HAVING SUM(Price.monthly_price$) > AVG(Price.monthly_price$)
ORDER BY theoretical_monthly_income DESC;


/*
Shows hosts that own 10 or more rooms that have not yet been reviewed and
also displays that number.

Output: 24 rows
*/

SELECT Host.name, Host.id, COUNT(New_Listing.id) as listings_not_yet_reviewed
FROM Host
INNER JOIN New_Listing
ON Host.id = New_Listing.host_id
FULL OUTER JOIN Review
ON New_Listing.id = Review.listing_id
WHERE Review.listing_id is NULL or Review.comments = ''
GROUP BY Host.id
HAVING COUNT(New_Listing.id) >= 10
ORDER BY COUNT(New_Listing.id) DESC;
  





