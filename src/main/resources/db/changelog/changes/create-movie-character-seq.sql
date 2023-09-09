--liquibase formatted sql
--changeset <postgres>:<create-movie-character-sequence-id>

CREATE SEQUENCE IF NOT EXISTS movie_character_id_seq INCREMENT 1 START 1 MINVALUE 1;

--rollback DROP SEQUENCE movie_character_id_seq;