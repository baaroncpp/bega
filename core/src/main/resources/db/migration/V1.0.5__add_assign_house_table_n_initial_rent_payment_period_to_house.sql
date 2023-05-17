ALTER TABLE t_house ADD COLUMN initial_rent_payment_period INTEGER NOT NULL DEFAULT 3;

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
   placement_date TIMESTAMP
);