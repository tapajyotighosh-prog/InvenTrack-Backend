-- V3: Seed sample categories and warehouses
INSERT INTO categories (name, description, icon) VALUES
    ('Electronics', 'Electronic devices and components', 'Monitor'),
    ('Clothing', 'Apparel and accessories', 'Shirt'),
    ('Food & Beverages', 'Consumable goods', 'Coffee'),
    ('Office Supplies', 'Stationery and office equipment', 'Briefcase'),
    ('Hardware', 'Tools and hardware supplies', 'Wrench'),
    ('Furniture', 'Home and office furniture', 'Armchair');

INSERT INTO warehouses (name, location, capacity, current_occupancy) VALUES
    ('Main Warehouse', 'New York, NY', 10000, 0),
    ('West Coast Hub', 'Los Angeles, CA', 8000, 0),
    ('Central Depot', 'Chicago, IL', 5000, 0);

INSERT INTO suppliers (company_name, contact_name, email, phone, address, rating) VALUES
    ('TechSupply Co.', 'John Smith', 'john@techsupply.com', '+1-555-0101', '123 Tech Blvd, San Jose, CA', 4.5),
    ('Global Parts Ltd.', 'Jane Doe', 'jane@globalparts.com', '+1-555-0102', '456 Industry Ave, Detroit, MI', 4.2),
    ('Quality Goods Inc.', 'Bob Wilson', 'bob@qualitygoods.com', '+1-555-0103', '789 Commerce St, Houston, TX', 3.8);
