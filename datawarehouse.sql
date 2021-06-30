
create table DimCustomer
(
CustomerID int not null auto_increment,
CustomerAltID varchar(10) not null,
CustomerName varchar(50),
Gender varchar(20),
constraint pk_DimCustomer PRIMARY KEY (CustomerID)
);

insert into DimCustomer(CustomerAltID,CustomerName,Gender) values
('IMI-001','Henry Ford','M'),
('IMI-002','Bill Gates','M'),
('IMI-003','Muskan Shaikh','F'),
('IMI-004','Richard Thrubin','M'),
('IMI-005','Emma Wattson','F');




create table DimProduct
(
ProductKey int not null auto_increment,
ProductAltKey varchar(10) not null,
ProductName varchar(100),
ProductActualCost DECIMAL(10,2),
ProductSalesCost DECIMAL(10,2),
constraint pk_DimProduct PRIMARY KEY (ProductKey)
);

insert into DimProduct(ProductAltKey,ProductName, ProductActualCost, ProductSalesCost) values
('ITM-001','Wheat Floor 1kg',5.50,6.50),
('ITM-002','Rice Grains 1kg',22.50,24),
('ITM-003','SunFlower Oil 1 ltr',42,43.5),
('ITM-004','Nirma Soap',18,20),
('ITM-005','Arial Washing Powder 1kg',135,139),
('ITM-006','Wheat Floor 1kg',10.50,16.50),
('ITM-007','Rice Grains 1kg',32.50,30),
('ITM-008','SunFlower Oil 1 ltr',62,23.5),
('ITM-009','Nirma Soap',28,40),
('ITM-010','Arial Washing Powder 1kg',150,189);




create table DimStores
(
StoreID int not null auto_increment,
StoreAltID varchar(10) not null,
StoreName varchar(100),
StoreLocation varchar(100),
City varchar(100),
State varchar(100),
Country varchar(100),
constraint pk_DimStores PRIMARY KEY(StoreID)
);

insert into DimStores(StoreAltID,StoreName,StoreLocation,City,State,Country ) values
('LOC-A1','X-Mart','S.P. RingRoad','Ahmedabad','Guj','India'),
('LOC-A2','X-Mart','Maninagar','Ahmedabad','Guj','India'),
('LOC-A3','X-Mart','Sivranjani','Ahmedabad','Guj','India');

create table DimSalesPerson
(
SalesPersonID int not null auto_increment,
SalesPersonAltID varchar(10) not null,
SalesPersonName varchar(100),
StoreID int,
City varchar(100),
State varchar(100),
Country varchar(100),
constraint pk_DimSalesPerson PRIMARY KEY(SalesPersonID)
);

insert into DimSalesPerson(SalesPersonAltID,SalesPersonName,StoreID,City,State,Country ) values
('SP-DMSPR1','Ashish',1,'Ahmedabad','Guj','India'),
('SP-DMSPR2','Ketan',1,'Ahmedabad','Guj','India'),
('SP-DMNGR1','Srinivas',2,'Ahmedabad','Guj','India'),
('SP-DMNGR2','Saad',2,'Ahmedabad','Guj','India'),
('SP-DMSVR1','Jasmin',3,'Ahmedabad','Guj','India'),
('SP-DMSVR2','Jacob',3,'Ahmedabad','Guj','India');



create table FactProductSales
(
	transaction_id int primary key auto_increment,
	sales_invoice_number int not null,
	
	store_id int not null,
	customer_id int not null,
	product_id int not null,
	sales_person_id int not null,
	
	quantity decimal(10,2),
	sales_total_cost decimal(10,2),
	product_actual_cost decimal(10,2),
	deviation decimal
);


AlTER TABLE FactProductSales ADD CONSTRAINT FK_store_id FOREIGN KEY (store_id) REFERENCES DimStores(StoreID);

AlTER TABLE FactProductSales ADD CONSTRAINT FK_customer_id FOREIGN KEY (customer_id) REFERENCES DimCustomer(CustomerID);

AlTER TABLE FactProductSales ADD CONSTRAINT FK_product_key FOREIGN KEY (product_id) REFERENCES DimProduct(ProductKey);

AlTER TABLE FactProductSales ADD CONSTRAINT FK_sales_person_id FOREIGN KEY (sales_person_id) REFERENCES DimSalesPerson(SalesPersonID);

Insert into FactProductSales(sales_invoice_number, store_id, customer_id, product_id, sales_person_id, quantity, product_actual_cost, sales_total_cost, deviation) values
(1,1,1,1,1,2,11,13,2),
(1,1,1,2,1,1,22.50,24,1.5),
(1,1,1,3,1,1,42,43.5,1.5),
(2,1,2,3,1,1,42,43.5,1.5),
(2,1,2,4,1,3,54,60,6),
(3,1,3,2,2,2,11,13,2),
(3,1,3,3,2,1,42,43.5,1.5),
(3,1,3,4,2,3,54,60,6),
(3,1,3,5,2,1,135,139,4),
(4,1,1,1,1,2,11,13,2),
(4,1,1,2,1,1,22.50,24,1.5),
(5,1,2,3,1,1,42,43.5,1.5),
(5,1,2,4,1,3,54,60,6),
(6,1,3,2,2,2,11,13,2),
(6,1,3,5,2,1,135,139,4),
(7,2,1,4,3,3,54,60,6),
(7,2,1,5,3,1,135,139,4),
(8,1,1,3,1,2,84,87,3),
(8,1,1,4,1,3,54,60,3),
(9,1,2,1,1,1,5.5,6.5,1),
(9,1,2,2,1,1,22.50,24,1.5),
(10,1,3,1,2,2,11,13,2),
(10,1,3,4,2,3,54,60,6),                
(11,2,1,2,3,1,5.5,6.5,1),
(11,2,1,3,3,1,42,43.5,1.5);


