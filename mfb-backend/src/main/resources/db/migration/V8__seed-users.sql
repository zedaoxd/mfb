WITH usuario_insert AS (
    INSERT INTO users (id, first_name, last_name, email, password, created_at, updated_at, is_active)
        VALUES (gen_random_uuid(),
                'admin',
                'admin',
                'admin@admin.com',
                -- password: senha-123
                '$2a$10$nP7tffNV9uqdgYD0YoIsZu1getIuxavsWh24T5VQKxJD7X2vUsoly',
                now(),
                now(),
                true)
        RETURNING id
)
INSERT INTO users_roles (user_id, role_id)
SELECT usuario_insert.id, roles.id
FROM usuario_insert, roles
WHERE roles.name IN ('ROLE_ADMIN', 'ROLE_USER');
