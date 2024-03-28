CREATE EXTENSION pg_trgm;

CREATE TABLE rarity (
    id serial PRIMARY KEY,
    name varchar UNIQUE NOT NULL
);

CREATE TABLE type (
    id serial PRIMARY KEY,
    name varchar UNIQUE NOT NULL
);

CREATE TABLE legality (
    id serial PRIMARY KEY,
    name varchar UNIQUE NOT NULL
);

CREATE TABLE subtype (
    id serial PRIMARY KEY,
    name varchar NOT NULL,
    type_id serial NOT NULL,
        CONSTRAINT fk_type FOREIGN KEY (type_id)
            REFERENCES type(id),
     UNIQUE (name, type_id)
);

CREATE TABLE keyword (
    id serial PRIMARY KEY,
    name varchar UNIQUE NOT NULL,
    description varchar
);


CREATE TABLE card (
    id serial PRIMARY KEY,
    name varchar NOT NULL,
    text varchar,
    card_mana_cost smallint,
    power smallint,
    toughness smallint,
    digital_only boolean NOT NULL,
    rarity_id serial NOT NULL,
    is_legendary boolean NOT NULL,
    CONSTRAINT fk_rarity FOREIGN KEY (rarity_id)
        REFERENCES rarity(id)
);

CREATE TABLE person (
    id serial PRIMARY KEY,
    email varchar UNIQUE NOT NULL,
    password varchar NOT NULL
);

CREATE TABLE card_type (
    id serial PRIMARY KEY,
    card_id serial NOT NULL,
    type_id serial NOT NULL,
    CONSTRAINT fk_card FOREIGN KEY (card_id)
        REFERENCES card(id) ON DELETE CASCADE,
    CONSTRAINT fk_type FOREIGN KEY (type_id)
        REFERENCES type(id) ON DELETE CASCADE,
    UNIQUE (card_id, type_id)
);

CREATE TABLE card_subtype (
    id serial PRIMARY KEY,
    card_id serial NOT NULL,
    subtype_id serial NOT NULL,
    CONSTRAINT fk_card FOREIGN KEY (card_id)
        REFERENCES card(id) ON DELETE CASCADE,
    CONSTRAINT fk_subtype FOREIGN KEY (subtype_id)
        REFERENCES subtype(id) ON DELETE CASCADE,
    UNIQUE (card_id, subtype_id)
);

CREATE TABLE card_legality (
    id serial PRIMARY KEY,
    card_id serial NOT NULL,
    legality_id serial NOT NULL,
    CONSTRAINT fk_card FOREIGN KEY (card_id)
        REFERENCES card(id) ON DELETE CASCADE,
    CONSTRAINT fk_legality FOREIGN KEY (legality_id)
        REFERENCES legality(id) ON DELETE CASCADE,
    UNIQUE (card_id, legality_id)
);

CREATE TABLE card_ruling (
    id serial PRIMARY KEY,
    card_id serial NOT NULL,
    ruling_text varchar NOT NULL,
    CONSTRAINT fk_card FOREIGN KEY (card_id)
        REFERENCES card(id) ON DELETE CASCADE
);

CREATE TABLE card_keyword (
    id serial PRIMARY KEY,
    card_id serial NOT NULL,
    keyword_id serial NOT NULL,
    CONSTRAINT fk_card FOREIGN KEY (card_id)
        REFERENCES card(id) ON DELETE CASCADE,
    CONSTRAINT fk_keyword FOREIGN KEY (keyword_id)
        REFERENCES keyword(id) ON DELETE CASCADE,
    UNIQUE (card_id, keyword_id)
);

CREATE TABLE list (
    id serial PRIMARY KEY,
    name varchar NOT NULL,
    person_id serial NOT NULL,
    CONSTRAINT fk_person FOREIGN KEY (person_id)
    REFERENCES person(id),
    UNIQUE (name, person_id)
);

CREATE TABLE list_card (
    id serial PRIMARY KEY,
    card_id serial NOT NULL,
    list_id serial NOT NULL,
    CONSTRAINT fk_card FOREIGN KEY (card_id)
        REFERENCES card(id) ON DELETE CASCADE,
    CONSTRAINT fk_list FOREIGN KEY (list_id)
        REFERENCES list(id) ON DELETE CASCADE,
    UNIQUE (card_id, list_id)
);
