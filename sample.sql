create table table1(
	productLine varchar(255),
	orderYear int,
	orderValue float
);

insert into table1(productLine, orderYear, orderValue) values('Vintage Cars', 2003, 4080.00);
insert into table1(productLine, orderYear, orderValue) values('Classic Cars', 2003, 5571.80);
insert into table1(productLine, orderYear, orderValue) values('Trucks and Buses', 2003, 3284.28);
insert into table1(productLine, orderYear, orderValue) values('Trains', 2003, 2770.95);
insert into table1(productLine, orderYear, orderValue) values('Ships', 2003, 5072.71);
insert into table1(productLine, orderYear, orderValue) values('Planes', 2003, 4825.44);
insert into table1(productLine, orderYear, orderValue) values('Motor Cycles', 2003, 2440.50);

insert into table1(productLine, orderYear, orderValue) values('Classic Cars', 2004, 8124.98);
insert into table1(productLine, orderYear, orderValue) values('Vintage Cars', 2004, 2819.28);
insert into table1(productLine, orderYear, orderValue) values('Trains', 2004, 4646.88);
insert into table1(productLine, orderYear, orderValue) values('Ships', 2004, 4301.15);
insert into table1(productLine, orderYear, orderValue) values('Planes', 2004, 2857.35);
insert into table1(productLine, orderYear, orderValue) values('Motorcycles', 2004, 2598.77);
insert into table1(productLine, orderYear, orderValue) values('Trucks and Buses', 2004, 4615.64);

insert into table1(productLine, orderYear, orderValue) values('Motorcycles', 2005, 4004.88);
insert into table1(productLine, orderYear, orderValue) values('Classic Cars', 2005, 5971.35);
insert into table1(productLine, orderYear, orderValue) values('Vintage Cars', 2005, 5346.50);
insert into table1(productLine, orderYear, orderValue) values('Trucks and Buses', 2005, 6295.03);
insert into table1(productLine, orderYear, orderValue) values('Trains', 2005, 1603.20);
insert into table1(productLine, orderYear, orderValue) values('Ships', 2005, 3774.00);
insert into table1(productLine, orderYear, orderValue) values('Planes', 2005, 4018.00);


/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
/*
apurva@apurva:~$ mysql -u apurva -p
Enter password: 
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 10
Server version: 8.0.23-0ubuntu0.20.04.1 (Ubuntu)

Copyright (c) 2000, 2021, Oracle and/or its affiliates.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| academicerpproject |
| companydb          |
| exercise           |
| information_schema |
| lab5rest           |
| mysql              |
| performance_schema |
| sample_db          |
| sample_db2         |
| sys                |
+--------------------+
10 rows in set (0.01 sec)

mysql> create database sample;
Query OK, 1 row affected (0.21 sec)

mysql> use sample;
Database changed
mysql> show tables;
Empty set (0.00 sec)

mysql> create table table1(productline varchar(255), totalOrderValue float);
Query OK, 0 rows affected (0.73 sec)

mysql> select * from table1;
Empty set (0.01 sec)

mysql> INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country)
    -> 
    -> ;
;
^C
mysql> insert into table1 (productline, totalOrderValue) values ('Vintage Cars', 12245.78);
Query OK, 1 row affected (0.14 sec)

mysql> insert into table1 (productline, totalOrderValue) values ('Classic Cars', 19668.13);
Query OK, 1 row affected (0.12 sec)

mysql> insert into table1 (productline, totalOrderValue) values ('Trucks and Buses', 14194.95);
Query OK, 1 row affected (0.11 sec)

mysql> insert into table1 (productline, totalOrderValue) values ('Trains', 9021.03);
Query OK, 1 row affected (0.12 sec)

mysql> insert into table1 (productline, totalOrderValue) values ('Ships', 13147.86);
Query OK, 1 row affected (0.10 sec)

mysql> insert into table1 (productline, totalOrderValue) values ('Planes', 11700.79);
Query OK, 1 row affected (0.14 sec)

mysql> insert into table1 (productline, totalOrderValue) values ('MotorCycles', 89022.69);
Query OK, 1 row affected (0.09 sec)

mysql> select * from table1;
+------------------+-----------------+
| productline      | totalOrderValue |
+------------------+-----------------+
| Vintage Cars     |         12245.8 |
| Classic Cars     |         19668.1 |
| Trucks and Buses |           14195 |
| Trains           |         9021.03 |
| Ships            |         13147.9 |
| Planes           |         11700.8 |
| MotorCycles      |         89022.7 |
+------------------+-----------------+
7 rows in set (0.00 sec)

mysql> delete from table1 where productline like 'MotorCycles';
Query OK, 1 row affected (0.10 sec)

mysql> insert into table1 (productline, totalOrderValue) values ('MotorCycles', 9044.15);
Query OK, 1 row affected (0.09 sec)

mysql> insert into table1 (productline, totalOrderValue) values (, 89022.69);
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near ', 89022.69)' at line 1
mysql> insert into table1 (productline, totalOrderValue) values ( NULL, 89022.69);
Query OK, 1 row affected (0.13 sec)

mysql> select * from table1;
+------------------+-----------------+
| productline      | totalOrderValue |
+------------------+-----------------+
| Vintage Cars     |         12245.8 |
| Classic Cars     |         19668.1 |
| Trucks and Buses |           14195 |
| Trains           |         9021.03 |
| Ships            |         13147.9 |
| Planes           |         11700.8 |
| MotorCycles      |         9044.15 |
| NULL             |         89022.7 |
+------------------+-----------------+
8 rows in set (0.00 sec)

mysql> clear
mysql> 

^C
mysql> cls
    -> ;
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'cls' at line 1
mysql> 
mysql> select * from table1;
+------------------+-----------------+
| productline      | totalOrderValue |
+------------------+-----------------+
| Vintage Cars     |         12245.8 |
| Classic Cars     |         19668.1 |
| Trucks and Buses |           14195 |
| Trains           |         9021.03 |
| Ships            |         13147.9 |
| Planes           |         11700.8 |
| MotorCycles      |         9044.15 |
| NULL             |         89022.7 |
+------------------+-----------------+
8 rows in set (0.03 sec)
mysql> 
mysql> select productline, sum(orderValue) totalOrderValue from table1 group by productline with ROLLUP;
mysql> 
mysql> 



mysql> delete table table1;
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'table table1' at line 1
mysql> drop table table1;
Query OK, 0 rows affected (0.38 sec)

mysql> clear
mysql> 














mysql> source ./Downloads/ACADEMIC_ERP_TABLES_CREATE.sql
Query OK, 0 rows affected (0.52 sec)

Query OK, 1 row affected (0.14 sec)

Query OK, 1 row affected (0.13 sec)

Query OK, 1 row affected (0.12 sec)

Query OK, 1 row affected (0.18 sec)

Query OK, 1 row affected (0.20 sec)

Query OK, 1 row affected (0.23 sec)

Query OK, 1 row affected (0.22 sec)

Query OK, 1 row affected (0.16 sec)

Query OK, 1 row affected (0.23 sec)

Query OK, 1 row affected (0.17 sec)

Query OK, 1 row affected (0.30 sec)

Query OK, 1 row affected (0.18 sec)

Query OK, 1 row affected (0.20 sec)

Query OK, 1 row affected (0.12 sec)

Query OK, 1 row affected (0.11 sec)

Query OK, 1 row affected (0.07 sec)

Query OK, 1 row affected (0.10 sec)

Query OK, 1 row affected (0.10 sec)

Query OK, 1 row affected (0.10 sec)

Query OK, 1 row affected (0.10 sec)

Query OK, 1 row affected (0.10 sec)

mysql> show tables;
+------------------+
| Tables_in_sample |
+------------------+
| table1           |
+------------------+
1 row in set (0.00 sec)

mysql> select * from table1;
+------------------+-----------+------------+
| productLine      | orderYear | orderValue |
+------------------+-----------+------------+
| Vintage Cars     |      2003 |       4080 |
| Classic Cars     |      2003 |     5571.8 |
| Trucks and Buses |      2003 |    3284.28 |
| Trains           |      2003 |    2770.95 |
| Ships            |      2003 |    5072.71 |
| Planes           |      2003 |    4825.44 |
| Motor Cycles     |      2003 |     2440.5 |
| Classic Cars     |      2004 |    8124.98 |
| Vintage Cars     |      2004 |    2819.28 |
| Trains           |      2004 |    4646.88 |
| Ships            |      2004 |    4301.15 |
| Planes           |      2004 |    2857.35 |
| Motorcycles      |      2004 |    2598.77 |
| Trucks and Buses |      2004 |    4615.64 |
| Motorcycles      |      2005 |    4004.88 |
| Classic Cars     |      2005 |    5971.35 |
| Vintage Cars     |      2005 |     5346.5 |
| Trucks and Buses |      2005 |    6295.03 |
| Trains           |      2005 |     1603.2 |
| Ships            |      2005 |       3774 |
| Planes           |      2005 |       4018 |
+------------------+-----------+------------+
21 rows in set (0.00 sec)

mysql> select productLine, sum(orderValue) totalOrderValue from sales group by productLine with ROLLUP;
ERROR 1146 (42S02): Table 'sample.sales' doesn't exist
mysql> select productLine, sum(orderValue) totalOrderValue from table1 group by productLine with ROLLUP;
+------------------+--------------------+
| productLine      | totalOrderValue    |
+------------------+--------------------+
| Classic Cars     |   19668.1298828125 |
| Motor Cycles     |             2440.5 |
| Motorcycles      |   6603.64990234375 |
| Planes           |   11700.7900390625 |
| Ships            |  13147.85986328125 |
| Trains           |   9021.02978515625 |
| Trucks and Buses | 14194.949951171875 |
| Vintage Cars     | 12245.780029296875 |
| NULL             |    89022.689453125 |
+------------------+--------------------+
9 rows in set (0.01 sec)

+------------------+-----------+--------------------+ totalOrderValue from table1 group by productLine, orderYear with ROLLUP;
| productLine      | orderYear | totalOrderValue    |
+------------------+-----------+--------------------+
| Classic Cars     |      2003 |    5571.7998046875 |
| Classic Cars     |      2004 |   8124.97998046875 |
| Classic Cars     |      2005 |   5971.35009765625 |
| Classic Cars     |      NULL |   19668.1298828125 |
| Motor Cycles     |      2003 |             2440.5 |
| Motor Cycles     |      NULL |             2440.5 |
| Motorcycles      |      2004 |   2598.77001953125 |
| Motorcycles      |      2005 |    4004.8798828125 |
| Motorcycles      |      NULL |   6603.64990234375 |
| Planes           |      2003 |   4825.43994140625 |
| Planes           |      2004 |   2857.35009765625 |
| Planes           |      2005 |               4018 |
| Planes           |      NULL |   11700.7900390625 |
| Ships            |      2003 |    5072.7099609375 |
| Ships            |      2004 |   4301.14990234375 |
| Ships            |      2005 |               3774 |
| Ships            |      NULL |  13147.85986328125 |
| Trains           |      2003 |  2770.949951171875 |
| Trains           |      2004 |    4646.8798828125 |
| Trains           |      2005 |  1603.199951171875 |
| Trains           |      NULL |   9021.02978515625 |
| Trucks and Buses |      2003 |  3284.280029296875 |
| Trucks and Buses |      2004 |   4615.64013671875 |
| Trucks and Buses |      2005 |   6295.02978515625 |
| Trucks and Buses |      NULL | 14194.949951171875 |
| Vintage Cars     |      2003 |               4080 |
| Vintage Cars     |      2004 |  2819.280029296875 |
| Vintage Cars     |      2005 |             5346.5 |
| Vintage Cars     |      NULL | 12245.780029296875 |
| NULL             |      NULL |    89022.689453125 |
+------------------+-----------+--------------------+
30 rows in set (0.01 sec)

mysql> select productLine, year, sum(orerValue) totalOrderValue from table1 group by productLine, year ROLLDOWN(productLine, year);
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'ROLLDOWN(productLine, year)' at line 1
mysql> select productLine, year, sum(orerValue) totalOrderValue from table1 group by productLine, year ROLLDOWN;
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'ROLLDOWN' at line 1
mysql> select * from table1;
+------------------+-----------+------------+
| productLine      | orderYear | orderValue |
+------------------+-----------+------------+
| Vintage Cars     |      2003 |       4080 |
| Classic Cars     |      2003 |     5571.8 |
| Trucks and Buses |      2003 |    3284.28 |
| Trains           |      2003 |    2770.95 |
| Ships            |      2003 |    5072.71 |
| Planes           |      2003 |    4825.44 |
| Motor Cycles     |      2003 |     2440.5 |
| Classic Cars     |      2004 |    8124.98 |
| Vintage Cars     |      2004 |    2819.28 |
| Trains           |      2004 |    4646.88 |
| Ships            |      2004 |    4301.15 |
| Planes           |      2004 |    2857.35 |
| Motorcycles      |      2004 |    2598.77 |
| Trucks and Buses |      2004 |    4615.64 |
| Motorcycles      |      2005 |    4004.88 |
| Classic Cars     |      2005 |    5971.35 |
| Vintage Cars     |      2005 |     5346.5 |
| Trucks and Buses |      2005 |    6295.03 |
| Trains           |      2005 |     1603.2 |
| Ships            |      2005 |       3774 |
| Planes           |      2005 |       4018 |
+------------------+-----------+------------+
21 rows in set (0.00 sec)

mysql> select productLine, year totalOrderValue from table1 group by productLine, year ROLLDOWN;
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'ROLLDOWN' at line 1
mysql> select * from table1;
+------------------+-----------+------------+
| productLine      | orderYear | orderValue |
+------------------+-----------+------------+
| Vintage Cars     |      2003 |       4080 |
| Classic Cars     |      2003 |     5571.8 |
| Trucks and Buses |      2003 |    3284.28 |
| Trains           |      2003 |    2770.95 |
| Ships            |      2003 |    5072.71 |
| Planes           |      2003 |    4825.44 |
| Motor Cycles     |      2003 |     2440.5 |
| Classic Cars     |      2004 |    8124.98 |
| Vintage Cars     |      2004 |    2819.28 |
| Trains           |      2004 |    4646.88 |
| Ships            |      2004 |    4301.15 |
| Planes           |      2004 |    2857.35 |
| Motorcycles      |      2004 |    2598.77 |
| Trucks and Buses |      2004 |    4615.64 |
| Motorcycles      |      2005 |    4004.88 |
| Classic Cars     |      2005 |    5971.35 |
| Vintage Cars     |      2005 |     5346.5 |
| Trucks and Buses |      2005 |    6295.03 |
| Trains           |      2005 |     1603.2 |
| Ships            |      2005 |       3774 |
| Planes           |      2005 |       4018 |
+------------------+-----------+------------+
21 rows in set (0.00 sec)

mysql> select productLine, sum(orderValue) from table1 where productLine='Planes' group by productLine;
+-------------+------------------+
| productLine | sum(orderValue)  |
+-------------+------------------+
| Planes      | 11700.7900390625 |
+-------------+------------------+
1 row in set (0.00 sec)

mysql> select productLine, year, sum(orderValue) from table1 where productLine='Planes' and year=2003 group by productLine;
ERROR 1054 (42S22): Unknown column 'year' in 'field list'
mysql> select productLine, Year, sum(orderValue) from table1 where productLine='Planes' and Year=2003 group by productLine;
ERROR 1054 (42S22): Unknown column 'Year' in 'field list'
mysql> select productLine, orderYear, sum(orderValue) from table1 where productLine='Planes' and orderYear=2003 group by productLine;
+-------------+-----------+------------------+
| productLine | orderYear | sum(orderValue)  |
+-------------+-----------+------------------+
| Planes      |      2003 | 4825.43994140625 |
+-------------+-----------+------------------+
1 row in set (0.00 sec)

mysql> select productLine, orderYear, orderValue from table1 where productLine='Planes' and orderYear=2003 group by productLine;
ERROR 1055 (42000): Expression #3 of SELECT list is not in GROUP BY clause and contains nonaggregated column 'sample.table1.orderValue' which is not functionally dependent on columns in GROUP BY clause; this is incompatible with sql_mode=only_full_group_by
mysql> select productLine, orderYear, orderValue from table1 where productLine='Planes' and orderYear=2003 group by productLine;
ERROR 1055 (42000): Expression #3 of SELECT list is not in GROUP BY clause and contains nonaggregated column 'sample.table1.orderValue' which is not functionally dependent on columns in GROUP BY clause; this is incompatible with sql_mode=only_full_group_by
mysql> select productLine, orderYear, orderValue from table1 where productLine='Planes' and orderYear=2003;
+-------------+-----------+------------+
| productLine | orderYear | orderValue |
+-------------+-----------+------------+
| Planes      |      2003 |    4825.44 |
+-------------+-----------+------------+
1 row in set (0.00 sec)

mysql> select productLine, orderYear, orderValue from table1 where productLine='Planes';
+-------------+-----------+------------+
| productLine | orderYear | orderValue |
+-------------+-----------+------------+
| Planes      |      2003 |    4825.44 |
| Planes      |      2004 |    2857.35 |
| Planes      |      2005 |       4018 |
+-------------+-----------+------------+
3 rows in set (0.00 sec)

mysql> select * from table1;
+------------------+-----------+------------+
| productLine      | orderYear | orderValue |
+------------------+-----------+------------+
| Vintage Cars     |      2003 |       4080 |
| Classic Cars     |      2003 |     5571.8 |
| Trucks and Buses |      2003 |    3284.28 |
| Trains           |      2003 |    2770.95 |
| Ships            |      2003 |    5072.71 |
| Planes           |      2003 |    4825.44 |
| Motor Cycles     |      2003 |     2440.5 |
| Classic Cars     |      2004 |    8124.98 |
| Vintage Cars     |      2004 |    2819.28 |
| Trains           |      2004 |    4646.88 |
| Ships            |      2004 |    4301.15 |
| Planes           |      2004 |    2857.35 |
| Motorcycles      |      2004 |    2598.77 |
| Trucks and Buses |      2004 |    4615.64 |
| Motorcycles      |      2005 |    4004.88 |
| Classic Cars     |      2005 |    5971.35 |
| Vintage Cars     |      2005 |     5346.5 |
| Trucks and Buses |      2005 |    6295.03 |
| Trains           |      2005 |     1603.2 |
| Ships            |      2005 |       3774 |
| Planes           |      2005 |       4018 |
+------------------+-----------+------------+
21 rows in set (0.00 sec)

mysql> select productLine, orderYear, sum(orderValue) totalOrderValue from table1 group by productLine, orderYear with ROLLUP;
+------------------+-----------+--------------------+
| productLine      | orderYear | totalOrderValue    |
+------------------+-----------+--------------------+
| Classic Cars     |      2003 |    5571.7998046875 |
| Classic Cars     |      2004 |   8124.97998046875 |
| Classic Cars     |      2005 |   5971.35009765625 |
| Classic Cars     |      NULL |   19668.1298828125 |
| Motor Cycles     |      2003 |             2440.5 |
| Motor Cycles     |      NULL |             2440.5 |
| Motorcycles      |      2004 |   2598.77001953125 |
| Motorcycles      |      2005 |    4004.8798828125 |
| Motorcycles      |      NULL |   6603.64990234375 |
| Planes           |      2003 |   4825.43994140625 |
| Planes           |      2004 |   2857.35009765625 |
| Planes           |      2005 |               4018 |
| Planes           |      NULL |   11700.7900390625 |
| Ships            |      2003 |    5072.7099609375 |
| Ships            |      2004 |   4301.14990234375 |
| Ships            |      2005 |               3774 |
| Ships            |      NULL |  13147.85986328125 |
| Trains           |      2003 |  2770.949951171875 |
| Trains           |      2004 |    4646.8798828125 |
| Trains           |      2005 |  1603.199951171875 |
| Trains           |      NULL |   9021.02978515625 |
| Trucks and Buses |      2003 |  3284.280029296875 |
| Trucks and Buses |      2004 |   4615.64013671875 |
| Trucks and Buses |      2005 |   6295.02978515625 |
| Trucks and Buses |      NULL | 14194.949951171875 |
| Vintage Cars     |      2003 |               4080 |
| Vintage Cars     |      2004 |  2819.280029296875 |
| Vintage Cars     |      2005 |             5346.5 |
| Vintage Cars     |      NULL | 12245.780029296875 |
| NULL             |      NULL |    89022.689453125 |
+------------------+-----------+--------------------+
30 rows in set (0.00 sec)

mysql> select * from table1
    -> ;
+------------------+-----------+------------+
| productLine      | orderYear | orderValue |
+------------------+-----------+------------+
| Vintage Cars     |      2003 |       4080 |
| Classic Cars     |      2003 |     5571.8 |
| Trucks and Buses |      2003 |    3284.28 |
| Trains           |      2003 |    2770.95 |
| Ships            |      2003 |    5072.71 |
| Planes           |      2003 |    4825.44 |
| Motor Cycles     |      2003 |     2440.5 |
| Classic Cars     |      2004 |    8124.98 |
| Vintage Cars     |      2004 |    2819.28 |
| Trains           |      2004 |    4646.88 |
| Ships            |      2004 |    4301.15 |
| Planes           |      2004 |    2857.35 |
| Motorcycles      |      2004 |    2598.77 |
| Trucks and Buses |      2004 |    4615.64 |
| Motorcycles      |      2005 |    4004.88 |
| Classic Cars     |      2005 |    5971.35 |
| Vintage Cars     |      2005 |     5346.5 |
| Trucks and Buses |      2005 |    6295.03 |
| Trains           |      2005 |     1603.2 |
| Ships            |      2005 |       3774 |
| Planes           |      2005 |       4018 |
+------------------+-----------+------------+
21 rows in set (0.00 sec)

mysql> insert into table1 values('Classic Cars', 2003, 5000);
Query OK, 1 row affected (0.09 sec)

mysql> select * from table1;
+------------------+-----------+------------+
| productLine      | orderYear | orderValue |
+------------------+-----------+------------+
| Vintage Cars     |      2003 |       4080 |
| Classic Cars     |      2003 |     5571.8 |
| Trucks and Buses |      2003 |    3284.28 |
| Trains           |      2003 |    2770.95 |
| Ships            |      2003 |    5072.71 |
| Planes           |      2003 |    4825.44 |
| Motor Cycles     |      2003 |     2440.5 |
| Classic Cars     |      2004 |    8124.98 |
| Vintage Cars     |      2004 |    2819.28 |
| Trains           |      2004 |    4646.88 |
| Ships            |      2004 |    4301.15 |
| Planes           |      2004 |    2857.35 |
| Motorcycles      |      2004 |    2598.77 |
| Trucks and Buses |      2004 |    4615.64 |
| Motorcycles      |      2005 |    4004.88 |
| Classic Cars     |      2005 |    5971.35 |
| Vintage Cars     |      2005 |     5346.5 |
| Trucks and Buses |      2005 |    6295.03 |
| Trains           |      2005 |     1603.2 |
| Ships            |      2005 |       3774 |
| Planes           |      2005 |       4018 |
| Classic Cars     |      2003 |       5000 |
+------------------+-----------+------------+
22 rows in set (0.00 sec)

mysql> select productLine, orderYear, sum(orderValue) totalOrderValue from table1 group by productLine, orderYear with ROLLUP;
+------------------+-----------+--------------------+
| productLine      | orderYear | totalOrderValue    |
+------------------+-----------+--------------------+
| Classic Cars     |      2003 |   10571.7998046875 |
| Classic Cars     |      2004 |   8124.97998046875 |
| Classic Cars     |      2005 |   5971.35009765625 |
| Classic Cars     |      NULL |   24668.1298828125 |
| Motor Cycles     |      2003 |             2440.5 |
| Motor Cycles     |      NULL |             2440.5 |
| Motorcycles      |      2004 |   2598.77001953125 |
| Motorcycles      |      2005 |    4004.8798828125 |
| Motorcycles      |      NULL |   6603.64990234375 |
| Planes           |      2003 |   4825.43994140625 |
| Planes           |      2004 |   2857.35009765625 |
| Planes           |      2005 |               4018 |
| Planes           |      NULL |   11700.7900390625 |
| Ships            |      2003 |    5072.7099609375 |
| Ships            |      2004 |   4301.14990234375 |
| Ships            |      2005 |               3774 |
| Ships            |      NULL |  13147.85986328125 |
| Trains           |      2003 |  2770.949951171875 |
| Trains           |      2004 |    4646.8798828125 |
| Trains           |      2005 |  1603.199951171875 |
| Trains           |      NULL |   9021.02978515625 |
| Trucks and Buses |      2003 |  3284.280029296875 |
| Trucks and Buses |      2004 |   4615.64013671875 |
| Trucks and Buses |      2005 |   6295.02978515625 |
| Trucks and Buses |      NULL | 14194.949951171875 |
| Vintage Cars     |      2003 |               4080 |
| Vintage Cars     |      2004 |  2819.280029296875 |
| Vintage Cars     |      2005 |             5346.5 |
| Vintage Cars     |      NULL | 12245.780029296875 |
| NULL             |      NULL |    94022.689453125 |
+------------------+-----------+--------------------+
30 rows in set (0.00 sec)

mysql> select * from table1;
+------------------+-----------+------------+
| productLine      | orderYear | orderValue |
+------------------+-----------+------------+
| Vintage Cars     |      2003 |       4080 |
| Classic Cars     |      2003 |     5571.8 |
| Trucks and Buses |      2003 |    3284.28 |
| Trains           |      2003 |    2770.95 |
| Ships            |      2003 |    5072.71 |
| Planes           |      2003 |    4825.44 |
| Motor Cycles     |      2003 |     2440.5 |
| Classic Cars     |      2004 |    8124.98 |
| Vintage Cars     |      2004 |    2819.28 |
| Trains           |      2004 |    4646.88 |
| Ships            |      2004 |    4301.15 |
| Planes           |      2004 |    2857.35 |
| Motorcycles      |      2004 |    2598.77 |
| Trucks and Buses |      2004 |    4615.64 |
| Motorcycles      |      2005 |    4004.88 |
| Classic Cars     |      2005 |    5971.35 |
| Vintage Cars     |      2005 |     5346.5 |
| Trucks and Buses |      2005 |    6295.03 |
| Trains           |      2005 |     1603.2 |
| Ships            |      2005 |       3774 |
| Planes           |      2005 |       4018 |
| Classic Cars     |      2003 |       5000 |
+------------------+-----------+------------+
22 rows in set (0.00 sec)

mysql> 
*/
