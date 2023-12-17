ALTER TABLE t_refresh_token ADD COLUMN modified_on TIMESTAMP;
ALTER TABLE t_refresh_token DROP COLUMN created_by_id;