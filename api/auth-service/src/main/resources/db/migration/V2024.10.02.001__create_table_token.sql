CREATE SCHEMA IF NOT EXISTS auth;

CREATE TABLE token
(
    id_token character varying(36) NOT NULL,
    dt_issue_at timestamp with time zone,
    dt_not_before timestamp with time zone,
    dt_not_after timestamp with time zone,
    dt_revoked timestamp with time zone,
    id_account character varying(36),
    CONSTRAINT token_pkey PRIMARY KEY (id_token)
);