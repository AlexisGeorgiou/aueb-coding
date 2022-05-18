/* Find all rentings from all houses that take place in
2020-05-15 and have accommodates between 2 and 5
Output: 9139 rows
*/

SELECT Listings.id, Listings.listing_url, Listings.accommodates
FROM Listings
INNER JOIN Calendar
ON Listings.id = Calendar.listing_id
WHERE Calendar.date = '2020-05-15' AND Listings.accommodates BETWEEN 2 AND 5;


/*
Returns the point coordinates of neighbourhoods starting with letter 'A'
Output: 8 row
*/

SELECT * FROM Geolocation
WHERE properties_neighbourhood LIKE 'Α%';


/*
Returns all the comments(reviews) for all the houses of a specific host (host's id 37177)
Output: 161 rows
*/

SELECT Listings.id, Listings.listing_url, Reviews.comments
FROM Listings
INNER JOIN Reviews
ON Listings.id = Reviews.listing_id
WHERE host_id = 37177
ORDER BY Listings.id DESC;


/* 
Returns the average character length of every review for each house 
with the house's link url taken from listings data.
Output: 8982 rows
*/

SELECT Reviews.listing_id, Listings.listing_url, ROUND(AVG(LENGTH(Reviews.comments))) as average_length_of_comments
FROM Reviews
INNER JOIN Listings
ON Listings.id = Reviews.listing_id
GROUP BY Reviews.listing_id, Listings.listing_url
ORDER BY Reviews.listing_id;


/*
Shows for every house the minimum and maximum price that it was ever rented.
OUTPUT: 11541 rows

[*]Prices from text is formatted and casted to float
*/

SELECT Calendar.listing_id, Listings.listing_url,
		MIN(CAST(REPLACE(REPLACE(Calendar.price, '$', ''), ',', '') AS float)) as minprice_in$,
		MAX(CAST(REPLACE(REPLACE(Calendar.price, '$', ''), ',', '') AS float)) as maxprice_in$
FROM Calendar
INNER JOIN Listings
ON Listings.id = Calendar.listing_id
GROUP BY Calendar.listing_id, Listings.listing_url
ORDER BY Calendar.listing_id;



/*
Shows id, url, coords and rating of the best 100 available houses inside the square specified
by (23,37) and (24,38) and at 2020-04-02
OUTPUT: 100 rows (5674 without limits)
*/

SELECT Listings.id, Listings.listing_url, Listings.latitude, 
	   Listings.longitude, Listings.review_scores_rating
FROM Listings
INNER JOIN Calendar
ON Listings.id = Calendar.listing_id
WHERE 
	Listings.latitude <= '38' AND Listings.latitude >= '37'
	AND Listings.longitude <= '24' AND Listings.longitude >= '23' 
	AND Listings.review_scores_rating is not NULL
	AND Calendar.date = '2020-04-02'
	AND Calendar.available = 't'
	
ORDER BY Listings.review_scores_rating DESC
LIMIT 100;


/*
Shows all available listings (id, url) in "ΑΜΠΕΛΟΚΗΠΟΙ" for the date 2020-08-01, sorted in
descending rating order, with review rating over 95.
OUTPUT: 102 rows
*/

SELECT DISTINCT Listings.id, Listings.listing_url, Listings.review_scores_rating
FROM Listings
INNER JOIN Calendar
ON Listings.id = Calendar.listing_id
WHERE Calendar.date >= '2020-08-01' AND Calendar.date <= '2020-08-06'
      AND Calendar.available = true
      AND Listings.review_scores_rating >= '95'
      AND Listings.neighbourhood_cleansed = 'ΑΜΠΕΛΟΚΗΠΟΙ'
ORDER BY review_scores_rating DESC;


/*
Counts the numbers of neighbourhoods
OUTPUT: 1 row
*/

SELECT COUNT(neighbourhood) as total_neighbourhoods
FROM Neighbourhoods;


/*
Shows listing_id, listing_url, current price and the average price that 
the house was rented in its history at a certain neighboorhood (ΑΝΩ ΠΑΤΗΣΙΑ)
OUTPUT: 79 rows
*/

SELECT Calendar.listing_id, Listings.listing_url, Listings.price as currentprice,
		ROUND(AVG(CAST(REPLACE(REPLACE(Calendar.price, '$', ''), ',', '') AS float))) as averageprice_in$

FROM Calendar
INNER JOIN Listings
ON Listings.id = Calendar.listing_id
WHERE Listings.neighbourhood_cleansed = 'ΑΝΩ ΠΑΤΗΣΙΑ'
GROUP BY Calendar.listing_id, Listings.listing_url, Listings.price
ORDER BY Calendar.listing_id;


/*
Shows the id, url, and host id of the top 20 houses with highest score rating in 2019.
[*]If two houses have same rating they will be ordered by their total reviews.
[*]Houses with lower than 100 total reviews are not being displayed 
due to their rating being inaccurate.
OUTPUT: 20 rows (86 without limit)
*/

SELECT Listings.id, Listings.listing_url, Listings.host_id, 
		CAST(Listings.review_scores_rating as int),
		COUNT(Reviews.comments) as total_reviews
FROM Listings
INNER JOIN Reviews
ON Listings.id = Reviews.listing_id
WHERE Reviews.date >= '2019-01-01' AND Reviews.date < '2020-01-01'
GROUP BY Listings.id
HAVING COUNT(Reviews.comments) > 100
ORDER BY CAST(Listings.review_scores_rating as int) DESC, COUNT(Reviews.comments) DESC
LIMIT 20;

/*
Shows the id, url and host_id of the listings that don't have any reviews
OUTPUT: 2778 rows
*/

SELECT DISTINCT Listings.id, Listings.listing_url, Listings.host_id
FROM Listings
LEFT OUTER JOIN Reviews
ON Listings.id = Reviews.listing_id
WHERE Reviews.comments is NULL or Reviews.comments = '';

/*
Shows the total number of Listings that have never been reviewed 
("Comments" is either '' or NULL).
OUTPUT: 1 row
*/

SELECT COUNT(*) as listings_without_reviews
FROM Reviews
RIGHT OUTER JOIN Listings
ON  Listings.id = Reviews.listing_id
WHERE Reviews.comments IS NULL OR Reviews.comments = '';


