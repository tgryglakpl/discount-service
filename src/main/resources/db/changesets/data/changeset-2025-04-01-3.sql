--liquibase formatted sql
--changeset tgryglak:11
-- Insert data into the `product` table
INSERT INTO product (unique_id, name, base_price)
VALUES
    ('bd367a29-7d12-42c4-9f7f-8b7cad7e8f67', 'Product 1', 100.00),
    ('bf40b60b-8253-4181-9e45-d7f8f8ee874d', 'Product 2', 150.00),
    ('e733874f-feb4-42d3-9c18-96467b17a5c2', 'Product 3', 200.00),
    ('4f325360-4045-4b04-8b8a-e8af332c0ff1', 'Product 4', 250.00);
--rollback ;

--changeset tgryglak:12
-- Insert data into the `discount_policy` table
INSERT INTO discount_policy (unique_id, type, active, priority)
VALUES
    ('3f7d14ef-c4c1-43ed-b887-333f7d8fa183', 'QUANTITY_DISCOUNT', true, 1),
    ('1e4ac7a7-e393-43f8-bd45-c1d4b63f109f', 'QUANTITY_DISCOUNT', true, 1),
    ('e7d61e3d-01e0-4297-85bc-e58c7b479de4', 'PERCENTAGE_DISCOUNT', true, 2),
    ('97d61e3d-01e0-4297-85bc-e58c7b479de4', 'PERCENTAGE_DISCOUNT', true, 2);
--rollback ;

--changeset tgryglak:13
-- Insert data into the `quantity_discount_rule` table
INSERT INTO quantity_discount_rule (unique_id, policy_id, min_quantity, max_quantity, discount_percent)
VALUES
    ('941aeca5-5b3f-40c4-bb1b-ad31061589a8', 1, 1, 9, 0.00),      -- Policy 1 (Quantity Discount), 1 to 9 units, 0% discount
    ('efe8c4ec-0570-4340-83ff-6a368a558c6d', 1, 10, 19, 5.00),    -- Policy 1 (Quantity Discount), 10 to 19 units, 5% discount
    ('4fa73580-b659-48ac-951e-7bc6729245e6', 1, 20, 49, 10.00),   -- Policy 1 (Quantity Discount), 20 to 49 units, 10% discount
    ('63288539-2f80-4399-b579-12523dbe2edb', 1, 50, 100, 15.00),  -- Policy 1 (Quantity Discount), more than 50 units, 15% discount
    ('21a4c8e2-559b-4ee6-ab0d-c18be8cf35df', 2, 10, 20, 0.00),    -- Policy 2 (Quantity Discount), 10 to 20 units, 0% discount
    ('eed54319-671b-4171-a030-8f5fc6409230', 2, 20, 30, 5.00),    -- Policy 2 (Quantity Discount), 20 to 30 units, 10% discount
    ('0b77e6fd-bc09-479f-8824-c6167c6c69c6', 2, 30, 50, 10.00),   -- Policy 2 (Quantity Discount), 30 to 50 units, 10% discount
    ('7682975f-2ee8-406e-ae1e-297f2a9da9df', 2, 50, 100, 15.00);  -- Policy 2 (Quantity Discount), more than 50 units, 15% discount
--rollback ;

--changeset tgryglak:14
-- Insert data into the `percentage_discount_rule` table
INSERT INTO percentage_discount_rule (unique_id, policy_id, discount_percent)
VALUES
    ('670e7065-8a4c-4bbc-b4d5-021f9fc9c0af', 3, 10.00),  -- Policy 3 (Percentage Discount), 10% discount
    ('d2ab79f3-acff-438e-90d8-ccfbcbab0240', 4, 15.00);  -- Policy 4 (Percentage Discount), 15% discount
--rollback ;

--changeset tgryglak:15
-- Insert data into the `product_discount_policy` table
INSERT INTO product_discount_policy (unique_id, product_id, discount_policy_id)
VALUES
    ('9eb17b97-39fd-4ed4-b04b-3dbe96ca6c6c', 1, 1), -- Product 1 has Quantity Discount Policy 1
    ('6823975a-6207-4e72-9ff5-c703282aceee', 1, 3), -- Product 1 has Percentage Discount Policy 3
    ('db267ff1-682f-4418-8eaf-078acc3b6a5e', 2, 2), -- Product 2 has Quantity Discount Policy 2
    ('d5b8ce3e-6b26-489d-9506-47a1ad1d489b', 2, 4); -- Product 2 has Percentage Discount Policy 4
--rollback ;

