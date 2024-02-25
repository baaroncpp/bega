CREATE TABLE t_landlord(
     id BIGSERIAL PRIMARY KEY,
     created_on TIMESTAMP DEFAULT now(),
     created_by_id BIGINT NOT NULL REFERENCES t_user(id),
     modified_on TIMESTAMP,
     modified_by_id BIGINT REFERENCES t_user(id),
     is_active BOOLEAN DEFAULT false,
     username VARCHAR(50) NOT NULL UNIQUE,
     physical_address TEXT NOT NULL,
     district_id BIGINT REFERENCES t_district(id),
     login_password VARCHAR(500) NOT NULL,
     user_meta_id BIGINT REFERENCES t_user_meta(id),
     tin VARCHAR(10),
     owner_ship_lc_letter_url_path TEXT,
     agreed_payment_date TIMESTAMP,
     business_agreement_path TEXT
);

CREATE TABLE t_apartment(
     id BIGSERIAL PRIMARY KEY,
     created_on TIMESTAMP DEFAULT now(),
     created_by_id BIGINT NOT NULL REFERENCES t_user(id),
     modified_on TIMESTAMP,
     modified_by_id BIGINT REFERENCES t_user(id),
     is_active BOOLEAN DEFAULT false,
     apartment_name VARCHAR(120) NOT NULL,
     apartment_type VARCHAR(60) NOT NULL,
     location TEXT NOT NULL,
     landlord_id BIGINT NOT NULL REFERENCES t_landlord(id),
     apartment_description TEXT,
     user_meta_id BIGINT REFERENCES t_user_meta(id),
     management_fee_type VARCHAR(60) NOT NULL
);

CREATE TABLE t_house_type(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    name VARCHAR(120) NOT NULL UNIQUE,
    note TEXT
);

CREATE TABLE t_house(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    modified_on TIMESTAMP,
    modified_by_id BIGINT REFERENCES t_user(id),
    is_active BOOLEAN DEFAULT false,
    house_number VARCHAR(10) UNIQUE NOT NULL,
    house_type_id BIGINT NOT NULL REFERENCES t_house_type(id),
    apartment_id BIGINT NOT NULL REFERENCES t_apartment(id),
    rent_fee NUMERIC NOT NULL,
    rent_period VARCHAR(50) NOT NULL,
    initial_rent_payment_period INTEGER NOT NULL DEFAULT 3,
    occupied_until TIMESTAMP,
    current_predefined_rent_fee NUMERIC,
    management_percentage FLOAT NOT NULL DEFAULT 0.0,
    note TEXT,
    reference_number VARCHAR(15) NOT NULL UNIQUE,
    is_renovation_charge_billed BOOLEAN NOT NULL DEFAULT FALSE,
    is_occupied BOOLEAN DEFAULT false
);

CREATE TABLE t_assign_house(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    modified_on TIMESTAMP,
    modified_by_id BIGINT REFERENCES t_user(id),
    is_active BOOLEAN DEFAULT false,
    house_number BIGINT REFERENCES t_house(id),
    predefined_rent NUMERIC NOT NULL,
    billing_duration VARCHAR(50) NOT NULL,
    tenant_id BIGINT REFERENCES t_tenant(id),
    deposit_amount NUMERIC NOT NULL,
    rent_amount_paid NUMERIC NOT NULL,
    initial_rent_payment NUMERIC NOT NULL,
    placement_date TIMESTAMP,
    note TEXT,
    is_approved BOOLEAN DEFAULT FALSE
);

CREATE TABLE t_landlord_next_of_kin(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    modified_on TIMESTAMP,
    modified_by_id BIGINT REFERENCES t_user(id),
    is_active BOOLEAN DEFAULT false,
    landlord_id BIGINT REFERENCES t_landlord(id),
    next_of_kin_id BIGINT REFERENCES t_next_of_kin(id),
    UNIQUE (landlord_id, next_of_kin_id)
);

CREATE TABLE t_tenant_next_of_kin(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    modified_on TIMESTAMP,
    modified_by_id BIGINT REFERENCES t_user(id),
    is_active BOOLEAN DEFAULT false,
    tenant_id BIGINT REFERENCES t_tenant(id),
    next_of_kin_id BIGINT REFERENCES t_next_of_kin(id),
    UNIQUE (tenant_id, next_of_kin_id)
);

CREATE TABLE t_landlord_bank_details(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    modified_on TIMESTAMP,
    modified_by_id BIGINT REFERENCES t_user(id),
    is_active BOOLEAN DEFAULT false,
    bank_detail_id BIGINT NOT NULL REFERENCES t_bank_detail(id),
    landlord_id BIGINT REFERENCES t_landlord(id),
    UNIQUE (landlord_id, bank_detail_id)
);