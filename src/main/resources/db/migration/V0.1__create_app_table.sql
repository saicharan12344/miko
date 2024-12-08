CREATE TABLE app
(
    id SERIAL PRIMARY KEY,
    title VARCHAR,
    errormessage VARCHAR,
    state VARCHAR,
    retries int,
    last_updated timestamp
);
