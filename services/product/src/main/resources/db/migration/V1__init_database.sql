CREATE TABLE IF NOT EXISTS category
(
    id          INTEGER NOT NULL PRIMARY KEY,
    description VARCHAR(255),
    name        VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS product
(
    id                 INTEGER          NOT NULL PRIMARY KEY,
    description        VARCHAR(255),
    name               VARCHAR(255),
    available_quantity DOUBLE PRECISION NOT NULL,
    price              NUMERIC(38, 2),
    category_id        INTEGER,
    CONSTRAINT fk1_category FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE SEQUENCE IF NOT EXISTS category_seq INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS product_seq INCREMENT BY 50;
