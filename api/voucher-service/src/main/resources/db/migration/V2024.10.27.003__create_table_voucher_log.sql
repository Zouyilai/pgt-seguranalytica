CREATE TABLE voucher_log
(
    id_voucher_log character varying(36) NOT NULL,
    id_voucher character varying(36) NOT NULL,
    dt_used TIMESTAMP,
    CONSTRAINT voucher_log_pkey PRIMARY KEY (id_voucher_log)
);

CREATE INDEX IF NOT EXISTS idx_voucher_log_id_voucher
    ON voucher_log USING btree
    (id_voucher ASC NULLS LAST);
