-- V2: Seed roles and admin user (password: admin123)
INSERT INTO roles (name, description) VALUES
    ('ADMIN', 'Full system access'),
    ('MANAGER', 'Manage products and orders'),
    ('VIEWER', 'Read-only access');

-- Default admin user (password: admin123 encoded with BCrypt)
INSERT INTO users (username, email, password_hash, full_name, is_active, role_id)
VALUES ('admin', 'admin@inventory.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'System Admin', true, 1);
