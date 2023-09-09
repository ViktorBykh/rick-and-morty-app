--liquibase formatted sql
--changeset <postgres>:<add-external-id-column-to-movie-character-table>

ALTER TABLE movie_character ADD external_id BIGINT;

--rollback ALTER TABLE DROP COLUMN external_id;