--EXERCISE TRIGGER FUNCTION
CREATE OR REPLACE FUNCTION listingHostCountUpdate()
	RETURNS trigger AS
$$
BEGIN
IF (TG_OP = 'INSERT') THEN
	UPDATE Host
	SET listings_count = listings_count + 1
	WHERE NEW.host_id = id;
ELSIF (TG_OP = 'DELETE') THEN
	UPDATE Host
	SET listings_count = listings_count - 1
	WHERE OLD.host_id = id;
END IF;
RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER listingHostCountChange
AFTER INSERT OR DELETE ON New_Listing
FOR EACH ROW
EXECUTE PROCEDURE listingHostCountUpdate();

--OUR TRIGGER FUNCTION
/*
This Trigger function acts like the previous one, but with a few tweaks. Each time a review for a specific listing is 
inserted or deleted, then the number_of_reviews is incremented or decreased by 1. In addition, it also updates the
first_review and last_review fields accordingly. If a listing gets its first review, then first_review will contain
the date of the recently added review, instead of the previous NULL value. If a review is deleted, it checks if that review's date is the same as last_review
and if it is, it is removed and is then replaced with the most recent review date (MAX(date)). If number_of_reviews reaches 0 for a specific listing, then
first_review and last_review values become equal to NULL for that listing. This trigger expects that if multiple reviews are inserted for one specific listing
with a single INSERT statement, then the most recent be inserted last. This trigger works well with SQL's now() function.
*/
CREATE OR REPLACE FUNCTION updateListingReviews()
	RETURNS TRIGGER AS 
$$
BEGIN
IF (TG_OP = 'INSERT') THEN
	UPDATE New_Listing
	SET number_of_reviews = number_of_reviews + 1,
		last_review = NEW.date
	WHERE NEW.listing_id = id;
	UPDATE New_Listing
	SET first_review = NEW.date
	WHERE NEW.listing_id = id AND first_review is NULL;
	
ELSIF (TG_OP = 'DELETE') THEN
	UPDATE New_Listing
	SET number_of_reviews = number_of_reviews - 1,
		last_review = (SELECT MAX(date) FROM Review, New_Listing WHERE Review.listing_id = New_Listing.id)
	WHERE OLD.listing_id = id;
	UPDATE New_Listing
	SET	first_review = NULL,
		last_review = NULL
	WHERE OLD.listing_id = id AND number_of_reviews = 0;
	
END IF;
RETURN NEW;
END
$$
LANGUAGE plpgsql;

CREATE TRIGGER reviewListingUpdater
AFTER INSERT OR DELETE ON Review
FOR EACH ROW
EXECUTE PROCEDURE updateListingReviews();







