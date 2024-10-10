CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE countries (
                                  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  name VARCHAR(32),
                                  alpha2 VARCHAR(2),
                                  alpha3 VARCHAR(3),
                                  status VARCHAR(32)
);

CREATE TABLE addresses (
                                  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  country_id INTEGER REFERENCES countries (id),
                                  address VARCHAR(128),
                                  zip_code VARCHAR(32),
                                  archived_at TIMESTAMP,
                                  city VARCHAR(32),
                                  state VARCHAR(32)
);

CREATE TABLE users (
                              id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              secret_key VARCHAR(32),
                              created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              first_name VARCHAR(32),
                              last_name VARCHAR(32),
                              verified_at TIMESTAMP,
                              archived_at TIMESTAMP,
                              status VARCHAR(64),
                              filled BOOLEAN,
                              address_id UUID REFERENCES addresses(id)
);

CREATE TABLE merchants (
                                  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                  creator_id UUID REFERENCES users(id),
                                  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  company_name VARCHAR(32),
                                  email VARCHAR(32),
                                  phone_number VARCHAR(32),
                                  verified_at TIMESTAMP,
                                  archived_at TIMESTAMP,
                                  status VARCHAR(32),
                                  filled BOOLEAN
);

CREATE TABLE merchant_members (
                                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                         user_id UUID UNIQUE REFERENCES users(id),
                                         created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         merchant_id UUID REFERENCES merchants(id),
                                         member_role VARCHAR(32),
                                         status VARCHAR(32)
);

CREATE TABLE individuals (
                                    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                    user_id UUID UNIQUE REFERENCES users(id),
                                    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    passport_number VARCHAR(32),
                                    email VARCHAR(32),
                                    phone_number VARCHAR(32),
                                    verified_at TIMESTAMP,
                                    archived_at TIMESTAMP,
                                    status VARCHAR(32)
);

CREATE TABLE profile_history (
                                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                        created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        profile_id UUID REFERENCES users(id),
                                        profile_type VARCHAR(32),
                                        reason VARCHAR(255),
                                        comment VARCHAR(255),
                                        changed_values VARCHAR(1024)
);

CREATE TABLE merchant_members_invitations (
                                                     id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                                     created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                     expires TIMESTAMP NOT NULL,
                                                     merchant_id UUID REFERENCES merchants(id),
                                                     first_name VARCHAR(32),
                                                     last_name VARCHAR(32),
                                                     email VARCHAR(32),
                                                     status VARCHAR(32)
);
