create table t_user_group(
     id BIGSERIAL PRIMARY KEY,
     user_group_name VARCHAR(32) UNIQUE,
     group_note TEXT,
     created_on TIMESTAMP NOT NULL DEFAULT now(),
     modified_on TIMESTAMP
);

create table t_user(
   id BIGSERIAL PRIMARY KEY,
   created_on TIMESTAMP DEFAULT now(),
   modified_on TIMESTAMP,
   username VARCHAR(32) UNIQUE,
   password TEXT,
   account_locked BOOLEAN NOT NULL DEFAULT TRUE,
   account_expired BOOLEAN NOT NULL DEFAULT TRUE,
   cred_expired BOOLEAN NOT NULL DEFAULT TRUE,
   user_group_id BIGINT NOT NULL REFERENCES t_user_group(id),
   approved BOOLEAN NOT NULL DEFAULT TRUE,
   is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
   approved_by INTEGER REFERENCES t_user(id),
   user_type VARCHAR(20) NOT NULL,
   initial_password_reset BOOLEAN NOT NULL DEFAULT FALSE,
   user_meta_id BIGINT  REFERENCES t_user(id)
);

CREATE TABLE t_user_meta(
    id SERIAL PRIMARY KEY,
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    created_by_id INTEGER REFERENCES t_user(id),
    modified_on TIMESTAMP,
    modified_by_id INTEGER REFERENCES t_user(id),
    is_active BOOLEAN DEFAULT FALSE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50),
    phone_number VARCHAR(15) NOT NULL,
    phone_number_2 VARCHAR(15),
    image_path TEXT,
    display_name VARCHAR(50),
    gender VARCHAR(6) NOT NULL,
    birth_date TIMESTAMP NOT NULL,
    email VARCHAR(100),
    country_id INTEGER NOT NULL REFERENCES t_country(id),
    identification VARCHAR(32) NOT NULL,
    identification_number VARCHAR(32) NOT NULL,
    identification_path TEXT,
    non_verified_email BOOLEAN DEFAULT FALSE,
    non_verified_phone_number BOOLEAN DEFAULT FALSE
);

CREATE TABLE t_tenant(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    modified_on TIMESTAMP,
    modified_by_id BIGINT REFERENCES t_user(id),
    is_active BOOLEAN DEFAULT false,
    username    varchar(50) NOT NULL UNIQUE,
    tenant_status VARCHAR(15) NOT NULL DEFAULT 'ACTIVE',
    occupation_status VARCHAR(60) NOT NULL,
    occupation_location VARCHAR(120),
    occupation_contact_phone VARCHAR(13),
    emergency_contact_name VARCHAR(120) NOT NULL,
    emergency_contact_phone VARCHAR(13) NOT NULL,
    password TEXT,
    user_meta_id INTEGER REFERENCES t_user_meta(id)
);

create table t_role(
     id BIGSERIAL PRIMARY KEY,
     name VARCHAR(20) UNIQUE,
     note TEXT,
     created_on TIMESTAMP NOT NULL DEFAULT now(),
     modified_on TIMESTAMP
);

create table t_permission(
     id BIGSERIAL PRIMARY KEY,
     role_id BIGINT NOT NULL REFERENCES t_role(id),
     permission_name VARCHAR(32) UNIQUE,
     created_on TIMESTAMP NOT NULL DEFAULT now(),
     modified_on TIMESTAMP,
     is_assignable BOOLEAN NOT NULL DEFAULT TRUE
);

create table t_user_approval(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES t_user(id),
    status INTEGER NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    modified_on TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_by BIGINT NOT NULL REFERENCES t_user(id),
    modified_by BIGINT REFERENCES t_user(id)
);

create table t_group_authority(
      id BIGSERIAL PRIMARY KEY,
      user_group_id BIGINT NOT NULL REFERENCES t_user_group(id),
      permission_id BIGINT NOT NULL REFERENCES t_permission(id),
      created_on TIMESTAMP NOT NULL DEFAULT now(),
      modified_on TIMESTAMP,
      UNIQUE (user_group_id, permission_id)
);