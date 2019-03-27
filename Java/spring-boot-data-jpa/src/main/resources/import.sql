/* Populate tables: This file will be searched by JPA to populate the tables */
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Jane', 'Doe', 'jane.doe@gmail.com', '2019-02-23', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('John', 'Doe', 'john.doe@gmail.com', '2019-02-23', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2017-08-03', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Jane', 'Doe', 'jane.doe@gmail.com', '2017-08-04', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2017-08-05', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Erich', 'Gamma', 'erich.gamma@gmail.com', '2017-08-06', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Richard', 'Helm', 'richard.helm@gmail.com', '2017-08-07', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2017-08-08', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('John', 'Vlissides', 'john.vlissides@gmail.com', '2017-08-09', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('James', 'Gosling', 'james.gosling@gmail.com', '2017-08-010', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Bruce', 'Lee', 'bruce.lee@gmail.com', '2017-08-11', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Johnny', 'Doe', 'johnny.doe@gmail.com', '2017-08-12', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('John', 'Roe', 'john.roe@gmail.com', '2017-08-13', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Jane', 'Roe', 'jane.roe@gmail.com', '2017-08-14', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Richard', 'Doe', 'richard.doe@gmail.com', '2017-08-15', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Janie', 'Doe', 'janie.doe@gmail.com', '2017-08-16', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Phillip', 'Webb', 'phillip.webb@gmail.com', '2017-08-17', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Stephane', 'Nicoll', 'stephane.nicoll@gmail.com', '2017-08-18', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Sam', 'Brannen', 'sam.brannen@gmail.com', '2017-08-19', '');  
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Juergen', 'Hoeller', 'juergen.Hoeller@gmail.com', '2017-08-20', ''); 
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Janie', 'Roe', 'janie.roe@gmail.com', '2017-08-21', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('John', 'Smith', 'john.smith@gmail.com', '2017-08-22', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Joe', 'Bloggs', 'joe.bloggs@gmail.com', '2017-08-23', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('John', 'Stiles', 'john.stiles@gmail.com', '2017-08-24', '');
INSERT INTO Clients (name, surname, email, creation_date, photo) VALUES ('Richard', 'Roe', 'stiles.roe@gmail.com', '2017-08-25', '');

/* Populate products table */
INSERT INTO products (name, price, creation_date) VALUES ('Panasonic LCD Screen', 259990, NOW());
INSERT INTO products (name, price, creation_date) VALUES ('Sony Digital Camera DSC-W320B', 123490, NOW());
INSERT INTO products (name, price, creation_date) VALUES ('Apple iPod Shuffle', 1499990, NOW());
INSERT INTO products (name, price, creation_date) VALUES ('Sony Notebook Z110', 37990, NOW());
INSERT INTO products (name, price, creation_date) VALUES ('Hewlett Packard Multifunctional F2280', 69990, NOW());
INSERT INTO products (name, price, creation_date) VALUES ('Bianchi Bike Aro 26', 69990, NOW());
INSERT INTO products (name, price, creation_date) VALUES ('Mica White Table', 299990, NOW());

/* Mocked invoices */
INSERT INTO invoices (description, observations, client_id, creation_date) VALUES ('Invoice Office Laptops', 'None observations', 1, NOW());
INSERT INTO invoices_lines (quantity, invoice_id, product_id) VALUES (1, 1, 1);
INSERT INTO invoices_lines (quantity, invoice_id, product_id) VALUES (2, 1, 4);
INSERT INTO invoices_lines (quantity, invoice_id, product_id) VALUES (1, 1, 5);
INSERT INTO invoices_lines (quantity, invoice_id, product_id) VALUES (1, 1, 7);
INSERT INTO invoices (description, observations, client_id, creation_date) VALUES ('Invoice Bike', 'The colour is dark red', 1, NOW());
INSERT INTO invoices_lines (quantity, invoice_id, product_id) VALUES (3, 2, 6);
