--liquibase formatted sql
--changeset <postgres>:<create-movie-character-table>

CREATE TABLE IF NOT EXISTS movie_character
(
    id BIGINT NOT NULL,
    NAME CHARACTER VARYING(255) NOT NULL,
    STATUS CHARACTER VARYING(255) NOT NULL,
    GENDER CHARACTER VARYING(255) NOT NULL,
    CONSTRAINT movie_character_pk PRIMARY KEY (id)
);

--rollback DROP TABLE movie_character;