--liquibase formatted sql
--changeset tgryglak:6 runInTransaction:false dbms:postgresql
create index concurrently if not exists idx_product_unique_id on product (unique_id);
--rollback drop index if exists idx_product_unique_id;

--changeset tgryglak:7 runInTransaction:false dbms:postgresql
create index concurrently if not exists idx_quantity_discount_rule_policy_id on quantity_discount_rule (policy_id);
--rollback drop index if exists idx_quantity_discount_rule_policy_id;

--changeset tgryglak:8 runInTransaction:false dbms:postgresql
create index concurrently if not exists idx_percentage_discount_rule_policy_id on percentage_discount_rule (policy_id);
--rollback drop index if exists idx_percentage_discount_rule_policy_id;

--changeset tgryglak:9 runInTransaction:false dbms:postgresql
create index concurrently if not exists idx_product_discount_policy_product_id on product_discount_policy (product_id);
--rollback drop index if exists idx_product_unique_id;

--changeset tgryglak:10 runInTransaction:false dbms:postgresql
create index concurrently if not exists idx_product_discount_policy_discount_policy_id on product_discount_policy (discount_policy_id);
--rollback drop index if exists idx_product_discount_policy_discount_policy_id;