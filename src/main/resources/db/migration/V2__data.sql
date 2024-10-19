INSERT INTO countries (id, name, alpha2, alpha3, status) VALUES
                                                             ('11111111-1111-1111-1111-111111111111', 'United States', 'US', 'USA', 'ACTIVE'),
                                                             ('22222222-2222-2222-2222-222222222222', 'Canada', 'CA', 'CAN', 'ACTIVE'),
                                                             ('33333333-3333-3333-3333-333333333333', 'Germany', 'DE', 'DEU', 'ACTIVE');

INSERT INTO addresses (id, country_id, address, zip_code, city, state, archived_at) VALUES
                                                                                        ('44444444-4444-4444-4444-444444444444', '11111111-1111-1111-1111-111111111111', '1234 Elm Street', '90210', 'Los Angeles', 'California', NULL),
                                                                                        ('55555555-5555-5555-5555-555555555555', '22222222-2222-2222-2222-222222222222', '5678 Maple Avenue', 'M5H 2N2', 'Toronto', 'Ontario', NULL),
                                                                                        ('66666666-6666-6666-6666-666666666666', '33333333-3333-3333-3333-333333333333', '1234 Bahnhofstrasse', '10115', 'Berlin', 'Berlin', '2023-01-01 12:00:00');

INSERT INTO users (id, secret_key, first_name, last_name, verified_at, archived_at, status, filled, address_id) VALUES
                                                                                                                    ('77777777-7777-7777-7777-777777777777', 'secret123', 'John', 'Doe', '2023-05-01 12:00:00', NULL, 'ACTIVE', true, '44444444-4444-4444-4444-444444444444'),
                                                                                                                    ('88888888-8888-8888-8888-888888888888', 'secret456', 'Jane', 'Smith', '2023-06-01 12:00:00', NULL, 'ACTIVE', false, '55555555-5555-5555-5555-555555555555'),
                                                                                                                    ('99999999-9999-9999-9999-999999999999', 'secret789', 'Max', 'Mustermann', '2023-07-01 12:00:00', '2023-08-01 12:00:00', 'ACTIVE', false, '66666666-6666-6666-6666-666666666666');

INSERT INTO merchants (id, creator_id, company_name, email, phone_number, verified_at, archived_at, status, filled) VALUES
                                                                                                                        ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '77777777-7777-7777-7777-777777777777', 'Acme Corp', 'acme@example.com', '123-456-7890', '2023-05-15 12:00:00', NULL, 'ACTIVE', true);

INSERT INTO merchant_members (id, user_id, merchant_id, member_role) VALUES
                                                                                 ('dddddddd-dddd-dddd-dddd-dddddddddddd', '88888888-8888-8888-8888-888888888888', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'MERCHANT_ADMIN');

INSERT INTO individuals (id, user_id, passport_number, email, phone_number) VALUES
                                                                                                                  ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '99999999-9999-9999-9999-999999999999', 'A1234567', 'max.mustermann@example.com', '123-456-7890');

INSERT INTO profile_history (id, profile_id, profile_type, reason, comment, changed_values) VALUES
                                                                                                ('11111111-2222-3333-4444-555555555555', '77777777-7777-7777-7777-777777777777', 'merchant', 'update', 'update merchant', '{"first_name": "John", "first_name_old": "Johnny"}'::jsonb),
                                                                                                ('66666666-7777-8888-9999-aaaaaaaaaaaa', '88888888-8888-8888-8888-888888888888', 'user', 'Status Update', 'User status changed to inactive', '{"first_name": "John", "first_name_old": "Johnny"}'::jsonb);

INSERT INTO merchant_members_invitations (id, expires, merchant_id, first_name, last_name, email, status) VALUES
                                                                                                              ('bbbbbbbb-cccc-dddd-eeee-ffffffffffff', '2024-01-01 12:00:00', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Michael', 'Jordan', 'mjordan@example.com', 'ACTIVE'),
                                                                                                              ('cccccccc-dddd-eeee-ffff-000000000000', '2024-02-01 12:00:00', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Kobe', 'Bryant', 'kbryant@example.com', 'ACTIVE');
