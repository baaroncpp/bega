CREATE TABLE t_account(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    modified_on TIMESTAMP,
    modified_by_id BIGINT REFERENCES t_user(id),
    is_active BOOLEAN DEFAULT false,
    user_meta_id BIGINT REFERENCES t_user_meta(id),
    account_number VARCHAR(8) UNIQUE NOT NULL,
    account_status VARCHAR(32) NOT NULL,
    account_type VARCHAR(32) NOT NULL,
    available_balance MONEY,
    activated_on TIMESTAMP,
    activated_by_id BIGINT REFERENCES t_user(id),
    suspended_on TIMESTAMP,
    suspended_by_id BIGINT REFERENCES t_user(id),
    closed_on TIMESTAMP,
    closed_by_id BIGINT REFERENCES t_user(id)
);

CREATE TABLE t_rent_payment(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    modified_on TIMESTAMP,
    modified_by_id BIGINT REFERENCES t_user(id),
    is_active BOOLEAN DEFAULT false,
    amount MONEY,
    tenant_id BIGINT NOT NULL REFERENCES t_tenant(id),
    payment_type VARCHAR(32),
    house_id BIGINT NOT NULL REFERENCES t_house(id),
    note TEXT,
    payment_status VARCHAR(32)
 );

CREATE TABLE t_transaction(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    modified_on TIMESTAMP,
    modified_by_id BIGINT REFERENCES t_user(id),
    is_active BOOLEAN DEFAULT false,
    amount MONEY,
    account_id BIGINT NOT NULL REFERENCES t_account(id),
    external_transaction_id VARCHAR(60) NOT NULL,
    reference_number VARCHAR(60) NOT NULL,
    transaction_type VARCHAR(32) NOT NULL,
    amount_before MONEY NOT NULL,
    amount_after MONEY NOT NULL,
    transaction_status VARCHAR(32) NOT NULL
);

CREATE TABLE t_debt(
   id BIGSERIAL PRIMARY KEY,
   created_on TIMESTAMP DEFAULT now(),
   modified_on TIMESTAMP,
   debtor_account_id BIGINT REFERENCES t_account(id),
   payable_amount MONEY NOT NULL,
   creditor_account_id BIGINT REFERENCES t_account(id),
   debt_status VARCHAR(32)
);