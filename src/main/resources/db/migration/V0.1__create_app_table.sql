CREATE TABLE app
(
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    state VARCHAR,
    errormessage VARCHAR,
    retries int
);
