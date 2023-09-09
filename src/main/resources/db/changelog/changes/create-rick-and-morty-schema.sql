--liquibase formatted sql
--changeset <postgres>:<create-rick-and-morty-schema.sql>

CREATE SCHEMA IF NOT EXISTS rick_and_morty_schema;

--rollback DROP SCHEMA rick_and_morty_schema;
