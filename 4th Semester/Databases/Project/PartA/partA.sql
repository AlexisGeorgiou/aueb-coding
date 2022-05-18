--CREATE TABLES FOR THE DATABASE
CREATE TABLE Credits(
   cast text,
   crew text,
   id int
);

CREATE TABLE Keywords(
   id int,
   keywords text
);
CREATE TABLE Movies_Metadata(
   adult varchar(10),
   belongs_to_collection varchar(190),
   budget int,
   genres varchar(270),
   homepage varchar(250),
   id int,
   imdb_id varchar(10),
   original_language varchar(10),
   original_title varchar(110),
   overview varchar(1000),
   popularity decimal,
   poster_path varchar(40),
   production_companies varchar(1260),
   production_countries varchar(1040),
   release_date date,
   revenue text,
   runtime decimal,
   spoken_languages varchar(770),
   status varchar(20),
   tagline varchar(300),
   title varchar(110),
   video varchar(10),
   vote_average decimal,
   vote_count int
);

CREATE TABLE Ratings_Small(
   userId int,
   movieId int,
   rating varchar(10),
   timestamp int
);


--REMOVE DUPLICATES FROM TABLES THAT CONTAIN THEM
--Table to contain Credits without duplicates
CREATE TABLE Credits_clean(
   cast text,
   crew text,
   id int
);

--Table to contain Movies_Metadata without duplicates
CREATE TABLE Movies_Metadata_clean(
   adult varchar(10),
   belongs_to_collection varchar(190),
   budget int,
   genres varchar(270),
   homepage varchar(250),
   id int,
   imdb_id varchar(10),
   original_language varchar(10),
   original_title varchar(110),
   overview varchar(1000),
   popularity decimal,
   poster_path varchar(40),
   production_companies varchar(1260),
   production_countries varchar(1040),
   release_date date,
   revenue text,
   runtime decimal,
   spoken_languages varchar(770),
   status varchar(20),
   tagline varchar(300),
   title varchar(110),
   video varchar(10),
   vote_average decimal,
   vote_count int
);
 
--Table to contain Links without duplicates
 CREATE TABLE Links_clean AS
(SELECT DISTINCT *
 FROM Links); 
 
 --Table to contain Keywords without duplicates
 CREATE TABLE Keywords_clean AS
(SELECT DISTINCT *
 FROM Keywords);
 
--Insert to Movies_Metadata_clean only distinct IDs from Movies_Metadata
INSERT INTO Movies_Metadata_clean(id)
SELECT DISTINCT id FROM Movies_Metadata;

--Insert to Credits_clean only distinct IDs from Credits
INSERT INTO Credits_clean(id)
SELECT DISTINCT id FROM Credits;

--Isert values to those distinct IDs (No duplicates. The first one to be found will be added) 
UPDATE Credits_clean
SET movie_cast = Credits.movie_cast,
    crew = Credits.crew
    FROM Credits
    WHERE Credits_clean.id = Credits.id;
    
UPDATE Movies_Metadata_clean
SET adult = Movies_Metadata.adult,
    belongs_to_collection = Movies_Metadata.belongs_to_collection,
    budget = Movies_Metadata.budget,
    genres = Movies_Metadata.genres,
    homepage = Movies_Metadata.homepage,
    imdb_id = Movies_Metadata.imdb_id,
    original_language = Movies_Metadata.original_language,
    original_title = Movies_Metadata.original_title,
    overview = Movies_Metadata.overview,
    popularity = Movies_Metadata.popularity,
    poster_path = Movies_Metadata.poster_path,
    production_companies = Movies_Metadata.production_companies,
    production_countries = Movies_Metadata.production_countries,
    release_date = Movies_Metadata.release_date,
    revenue = Movies_Metadata.revenue,
    runtime = Movies_Metadata.runtime,
    spoken_languages = Movies_Metadata.spoken_languages,
    status = Movies_Metadata.status,
    tagline = Movies_Metadata.tagline,
    title = Movies_Metadata.title,
    video = Movies_Metadata.video,
    vote_average = Movies_Metadata.vote_average,
    vote_count = Movies_Metadata.vote_count
    FROM Movies_Metadata
    WHERE Movies_Metadata_clean.id = Movies_Metadata.id;
--Delete old tables
DROP TABLE Credits, Keywords, Links, Movies_Metadata;


--Rename new tables to old table names
ALTER TABLE credits_clean
RENAME TO credits;

ALTER TABLE keywords_clean
RENAME TO keywords;

ALTER TABLE Links_clean
RENAME TO Links;

ALTER TABLE Movies_Metadata_clean
RENAME TO Movies_Metadata;

--Look if there exist IDs in tables that do not exist in movies metadata
SELECT Links.movieid FROM Links
WHERE Links.movieid not in (SELECT Movies_metadata.id from Movies_metadata);



SELECT Credits.id FROM Credits
WHERE Credits.id not in (SELECT Movies_metadata.id from Movies_metadata);



SELECT Keywords.id FROM Keywords
WHERE Keywords.id not in (SELECT Movies_metadata.id from Movies_metadata);



SELECT Ratings_small.movieid FROM Ratings_small
WHERE Ratings_small.movieid not in (SELECT Movies_metadata.id from Movies_metadata);



/*Delete those movie ids that don't exist in Movies_Metadata. In this Database, 
only Links and Ratings_small contain such ids, as found from the above SELECT queries*/
DELETE FROM Links
WHERE Links.movieid not in (SELECT Movies_metadata.id from Movies_metadata);


DELETE FROM Ratings_small
WHERE Ratings_small.movieid not in (SELECT Movies_metadata.id from Movies_metadata);
 
--CONVERT COLUMNS TO JSON COMPATIBLE
--For Movies_Metadata
UPDATE Movies_Metadata
SET genres = REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE( genres, E'{\'', E'{\"'), E', \'', E', \"' ), E'\': \'', E'\": \"'),
		E'\'}', E'\"}'), E'\':', E'\":'), E'\', ', '", '),
	production_countries = REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE( production_countries, E'{\'', E'{\"'), E', \'', E', \"' ), E'\': \'', E'\": \"'),
		E'\'}', E'\"}'), E'\':', E'\":'), E'\', ', '", '),
	spoken_languages = REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE( spoken_languages, E'{\'', E'{\"'), E', \'', E', \"' ), E'\': \'', E'\": \"'),
		E'\'}', E'\"}'), E'\':', E'\":'), E'\', ', '", '),
	belongs_to_collection = REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE( belongs_to_collection, E'{\'', E'{\"'), E', \'', E', \"' ), E'\': \'', E'\": \"'),
		E'\'}', E'\"}'), E'\':', E'\":'), E'\', ', '", '),
	production_companies= REPLACE(REPLACE(REPLACE(REPLACE(production_companies, '"', E'\'')
								  ,E'\'name\': \'', '"name": "')
								  ,E'\', \'id\':', '", "id":')
								  ,'\x', '');	
UPDATE Movies_Metadata
SET belongs_to_collection = REPLACE(belongs_to_collection, 'None', '"None"');
	

UPDATE Movies_Metadata
SET spoken_languages = REPLACE(spoken_languages, '\x', '');

--For Keywords
UPDATE Keywords
SET keywords = REPLACE(REPLACE(REPLACE(REPLACE(REPLACE( genres, E'{\'', E'{\"'), E', \'', E', \"' ), E'\': \'', E'\": \"'),
		E'\'}', E'\"}'), E'\':', E'\":');	
		
UPDATE Keywords
SET keywords = REPLACE(REPLACE(keywords, '"\', '"'), E'\"trudy jackson\"', '\"trudy jackson\"');


--For Credits
UPDATE Credits
SET crew = REPLACE(crew, '"profile_path": None', '"profile_path": "None"');
	movie_cast = REPLACE(crew, '"profile_path": None', '"profile_path": "None"');

UPDATE credits
SET crew = REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE( crew, E'{\'', E'{\"'), E', \'', E', \"' ), E'\': \'', E'\": \"'),
		E'\'}', E'\"}'), E'\':', E'\":'), E'\',', E'\",'), E': \'', E': "');
	movie_cast = REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(
							  movie_cast, '"', E'\'')
							,E'\'character\': \', \'credit_id\'', E'\'character\': \'\', \'credit_id\'')																			 
							,E'\'cast_id\':', '"cast_id":')
							,E'\'character\': \'', '"character": "')
							,E'\', \'credit_id\': \'', '", "credit_id": "')
							,E'\'gender\':', '"gender":')
							,E'\'id\':', '"id":')							 
							,E'\'name\': \'', '"name": "')
							,E'\', \'order\':', '", "order":')
							,E'\', \'profile_path\': \'', '", "profile_path": "')
						 	,E'\'}', '"}')
						 	,'\', '')';--this last single quote is not taken into consideration. It was added to retain .sql consistency. Please remove it if you're going to run this query


--TURN COLUMNS INTO JSON TYPE
ALTER TABLE Movies_Metadata
ALTER COLUMN production_countries TYPE jsonb USING production_countries::jsonb;

ALTER TABLE Movies_Metadata
ALTER COLUMN spoken_languages TYPE jsonb USING spoken_languages::jsonb;

ALTER TABLE Movies_Metadata
ALTER COLUMN production_companies TYPE jsonb USING production_companies::jsonb;

ALTER TABLE Movies_Metadata
ALTER COLUMN belongs_to_collection TYPE jsonb USING belongs_to_collection::jsonb;

ALTER TABLE Movies_Metadata
ALTER COLUMN genres TYPE jsonb USING genres::jsonb;

ALTER TABLE Keywords
ALTER COLUMN keywords TYPE jsonb USING keywords::jsonb;

ALTER TABLE Credits
ALTER COLUMN crew TYPE jsonb USING crew::jsonb;

ALTER TABLE Credits
ALTER COLUMN movie_cast TYPE jsonb USING movie_cast::jsonb;



--Create keywords_ids table for normalization

CREATE TABLE Keyword_IDs AS(
    select distinct CAST(jsonb_array_elements(keywords) ->> 'id' as int) as id, 
    jsonb_array_elements(keywords) ->> 'name' as name
    from Keywords
)

--Create intermediate table to corelate movies and keywords
CREATE TABLE Keywords_inter AS(
    select Keywords.id as movie_id, Keyword_IDs.id as keyword_id
	FROM Keywords, Keyword_IDs
    WHERE Keywords.keywords LIKE '%"' || Keyword_IDs.name || '"%');

--Delete old keywords table
DROP TABLE keywords;

--Rename intermediate table to Keywords (New Keywords table, no need for intermediate table)
ALTER TABLE Keywords_inter
RENAME TO Keywords;

--Create Genre Table
CREATE TABLE Genre AS(
    select distinct CAST(jsonb_array_elements(genres) ->> 'id' as int) as id, 
    jsonb_array_elements(genres) ->> 'name' as name
    from Movies_Metadata
);

--Create Movie_Genres relation
CREATE TABLE Movie_Genres AS(
	SELECT Movies_Metadata.id as movie_id, Genre.id as genre_id
	FROM Movies_Metadata, Genre
	WHERE Movies_Metadata.genres LIKE '%"' || Genre.name || '"%'); 
	
--Drop Genres COLUMN
ALTER TABLE Movies_Metadata
DROP COLUMN genres;
 
--Add suitable Primary and Foreign keys to the database tables
ALTER TABLE Credits
ADD CONSTRAINT pk_Credits_id
PRIMARY KEY (id);

ALTER TABLE Links
ADD CONSTRAINT pk_Links_id
PRIMARY KEY (movieid);

ALTER TABLE Movies_metadata
ADD CONSTRAINT pk_Movies_metadata_id
PRIMARY KEY (id);

ALTER TABLE Ratings_small
ADD CONSTRAINT pk_Ratings_Small_id
PRIMARY KEY (userid, movieid);

ALTER TABLE Genre
ADD CONSTRAINT pk_Genre_id
PRIMARY KEY (id);

ALTER TABLE Keyword_IDs
ADD CONSTRAINT pk_Keyword_IDs_id
PRIMARY KEY (id);
	
	
ALTER TABLE Credits
ADD CONSTRAINT fk_Credits_movie_id
FOREIGN KEY (id) REFERENCES Movies_Metadata(id);

ALTER TABLE Links
ADD CONSTRAINT fk_Links_id
FOREIGN KEY (movieid) REFERENCES Movies_Metadata(id);

ALTER TABLE Ratings_small
ADD CONSTRAINT fk_Ratings_small_id
FOREIGN KEY (movieid) REFERENCES Movies_Metadata(id);

ALTER TABLE Movie_Genre
ADD CONSTRAINT fk_Movie_Genre_movie_id
FOREIGN KEY (movie_id) REFERENCES Movies_Metadata(id);


ALTER TABLE Keywords
ADD CONSTRAINT fk_Keywords_movie_id
FOREIGN KEY (movie_id) REFERENCES Movies_Metadata(id);

