CREATE TABLE t_refresh_token(
    id BIGSERIAL PRIMARY KEY,
    created_on TIMESTAMP DEFAULT now(),
    created_by_id BIGINT NOT NULL REFERENCES t_user(id),
    token TEXT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    user_id BIGINT REFERENCES t_user(id)
)