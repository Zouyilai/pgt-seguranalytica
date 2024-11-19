CREATE SCHEMA IF NOT EXISTS account;

CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE EXTENSION IF NOT EXISTS btree_gin;

CREATE TABLE account
(
    id_account character varying(36) NOT NULL,
    tx_name character varying(256) NOT NULL,
    tx_email character varying(256) NOT NULL,
    tx_sha256 character varying(64) NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (id_account)
);

CREATE INDEX IF NOT EXISTS idx_account_email
    ON account USING btree
    (tx_email ASC NULLS LAST);
