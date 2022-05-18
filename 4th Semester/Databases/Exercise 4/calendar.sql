--UPDATE CALENDAR CONTENTS
UPDATE Calendar
SET price = CAST(REPLACE(REPLACE(price, '$', ''), ',', '') AS float), 
	adjusted_price = CAST(REPLACE(REPLACE(price, '$', ''), ',', '') AS float);

--CHANGE COLUMN TYPES
ALTER TABLE Calendar
ALTER COLUMN price type float USING price::double precision;

ALTER TABLE Calendar
ALTER COLUMN adjusted_price type float USING adjusted_price::double precision;

--CHANGE COLUMN NAMES TO MATCH CURRENCY
ALTER TABLE Calendar
RENAME COLUMN price TO price$;

ALTER TABLE Calendar
RENAME COLUMN adjusted_price TO adjusted_price$;