CREATE TABLE t_landlord(
     id BIGSERIAL PRIMARY KEY,
     created_on TIMESTAMP DEFAULT now(),
     created_by_id BIGINT NOT NULL REFERENCES t_user(id),
     modified_on TIMESTAMP,
     modified_by_id BIGINT REFERENCES t_user(id),
     is_active BOOLEAN DEFAULT false,
     first_name VARCHAR(120) NOT NULL,
     middle_name VARCHAR(120),
     last_name VARCHAR(120) NOT NULL,
     identification_type VARCHAR(60) NOT NULL,
     identification_number VARCHAR(100) NOT NULL UNIQUE,
     phone_number VARCHAR(15) NOT NULL,
     second_phone_number VARCHAR(15),
     physical_address TEXT NOT NULL,
     email VARCHAR(120) UNIQUE,
     login_password VARCHAR(500) NOT NULL
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
     management_fee NUMERIC NOT NULL,
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
    note TEXT,
    is_occupied BOOLEAN DEFAULT false
);