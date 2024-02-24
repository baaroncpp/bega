CREATE TABLE t_refresh_token(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    modified_on TIMESTAMP,
    token TEXT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    user_id BIGINT REFERENCES t_user(id)
);

CREATE TABLE t_file(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    modified_on TIMESTAMP,
    modified_by_id BIGINT REFERENCES t_user(id),
    is_active BOOLEAN DEFAULT false,
    file_name VARCHAR(100) NOT NULL,
    file_path VARCHAR(20) UNIQUE,
    file_format VARCHAR(20),
    exists BOOLEAN DEFAULT FALSE,
    file_type VARCHAR(10),
    file_size DOUBLE PRECISION
);