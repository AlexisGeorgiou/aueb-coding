--1) Αριθμός ταινιών ανά χρόνο (UnReleased and Released), can show only Released by running the command in the code too.

SELECT date_part('year', release_date) as year, COUNT(*) as number_of_movies FROM Movies_metadata
--WHERE status = 'Released' --ΑΝ ΘΕΛΟΥΜΕ ΜΟΝΟ ΤΙΣ ΤΑΙΝΙΕΣ ΠΟΥ ΕΧΟΥΝ ΒΓΕΙ
GROUP BY year
ORDER BY year desc;


--2) Αριθμός ταινιών ανά είδος(genre)

SELECT genre_id, COUNT(movie_id) as number_of_movies FROM Movie_genre
GROUP BY genre_id
ORDER BY genre_id;

--3) Αριθμός ταινιών ανά είδος(genre) και ανά χρόνο

SELECT genre_id, 
date_part('year', Movies_metadata.release_date) as year,
COUNT(movie_id) as number_of_movies
FROM Movie_genre, Movies_metadata
WHERE Movie_genre.movie_id = Movies_metadata.id
GROUP BY genre_id, year
ORDER BY genre_id;

--4) Μέση βαθμολογία (rating) ανά είδος (ταινίας)
SELECT genre_id, AVG(vote_average) as average_rating
FROM Movie_genre, Movies_metadata
WHERE Movie_genre.movie_id = Movies_metadata.id
GROUP BY genre_id
ORDER BY average_rating DESC;

--5) Αριθμός από ratings ανά χρήστη
SELECT userid, COUNT(rating) as number_of_ratings FROM Ratings_small
GROUP BY userid
ORDER BY userid;

--ALTER TABLE ΓΙΑ ΠΡΑΞΕΙΣ
--character varying rating changed to decimal

ALTER TABLE Ratings_small
ALTER COLUMN rating TYPE decimal USING rating::decimal

--6) Μέση βαθμολογία (rating) ανά χρήστη

SELECT userid, AVG(rating) as average_of_ratings
FROM Ratings_small
GROUP BY userid
ORDER BY userid;


--VIEWTABLE
/* Απο αυτό το VIEWTABLE μπορούμε να καταλάβουμε στο περίπου το κατα πόσο αυστηρός
είναι με τις κριτικές του ένας χρήστης. Βέβαια, το πόσο αυστηρός είναι μπορούμε να το 
κρίνουμε μόνο αν ο αριθμός των ratings του είναι αρκετα μεγάλος, ώστε το αποτέλεσμα
της μέσης του βαθμολογίας να είναι πιο αξιόπιστο (π.χ πάνω απο 50 ratings).
	
Πέρα απο αυτο, σε πιο μακροσκοπικό επίπεδο, μπορούμε να αντλήσουμε και πόσοι χρήστες έχουν ένα συγκεκριμένο rating.
Δηλαδή, Μπορούμε να μάθουμε ποια είναι η "δημοφιλέστερη" βαθμολογία (ή πιο σωστα διάστημα βαθμολογίας,
όπως έχουμε φτιάξει στην οπτικοποίηση) στην IMDΒ. Αυτό μας δείχνει αν οι χρήστες της IMDB τείνουν να
επιλέγουν βαθμολογίες πάνω ή κατω απο τον μέσο όρο, υψηλές ή χαμηλές κ.τ.λ. Οι περισσότεροι χρήστες εχουν μέσο όρο ratings
στο 3.5 και 3.6)

Τέλος, μπορούμε να βρούμε πως οι περισσότεροι χρήστες στην βάση της IMDB κανουν σχετικά λίγα ratings. Οσο πιο
πολύ αυξάνεται ο αριθμός των ratings, ο αριθμός των χρηστών της IMDB πέφτει εκθετικά (εκθετική μείωση καμπύλης)
*/

CREATE VIEW AVG_Ratings_By_User AS
SELECT userid, COUNT(*) as number_of_ratings, CAST(AVG(rating) as decimal (3, 1) as average_of_ratings
FROM Ratings_small
GROUP BY userid
ORDER BY userid;