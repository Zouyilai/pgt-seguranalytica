CREATE TABLE voucher
(
    id_voucher character varying(36) NOT NULL,
    id_account character varying(36) NOT NULL,
    tx_alias character varying(256) NOT NULL,
    dt_revoked TIMESTAMP,
    dt_not_before TIMESTAMP,
    dt_not_after TIMESTAMP,
    dt_creation TIMESTAMP,
    CONSTRAINT voucher_pkey PRIMARY KEY (id_voucher)
);
