--liquibase formatted sql

--changeset tgryglak:1
create table if not exists product (
    id BIGINT generated always as identity primary key,
    unique_id UUID unique,
    name VARCHAR(255) NOT NULL,
    base_price DECIMAL(12,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--changeset tgryglak:2
create table if not exists discount_policy (
    id BIGINT generated always as identity primary key,
    unique_id UUID unique,
    type VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT true,
    priority INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--changeset tgryglak:3
create table if not exists quantity_discount_rule (
    id BIGINT generated always as identity primary key,
    unique_id UUID unique,
    policy_id BIGINT NOT NULL,
    min_quantity INT NOT NULL,
    max_quantity INT NOT NULL,
    discount_percent DECIMAL(5,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    constraint fk_quantity_discount_rule_discount_policy_id
        foreign key (policy_id) references discount_policy (id),
    constraint uq_policy_id_min_max_quantity_discount_percent
        unique (policy_id, min_quantity, max_quantity, discount_percent)
);

--changeset tgryglak:4
create table if not exists percentage_discount_rule (
    id BIGINT generated always as identity primary key,
    unique_id UUID unique,
    policy_id BIGINT NOT NULL,
    discount_percent DECIMAL(5,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    constraint fk_discount_policy_id
        foreign key (policy_id) references discount_policy (id),
    constraint uq_discount_percent
        unique (discount_percent)
);

--changeset tgryglak:5
CREATE TABLE product_discount_policy (
    id BIGINT generated always as identity primary key,
    unique_id UUID unique,
    product_id BIGINT NOT NULL,
    discount_policy_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    constraint fk_product_id
        foreign key (product_id) references product (id),
    constraint fk_product_discount_policy_discount_policy_id
        foreign key (discount_policy_id) references discount_policy (id),
    constraint uq_product_id_discount_policy_id
        unique (product_id, discount_policy_id)
);