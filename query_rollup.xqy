
xquery version "3.0";

for $fact in doc("factProductSales.xml")//ProductSale, $cust in doc("dimcust.xml")//customer
where $cust/CustomerID = $fact/customer_id
group by $d := ($fact/customer_id)
order by $d
return concat("&#xA;","customer_id = ", $d, "&#x9;&#x9;", "customer_name = ", string($cust/CustomerName) ,"&#x9;&#x9;", "total_cost = ", sum($fact/sales_total_cost), "&#xA;")


(: 

OUTPUT:

apurva@apurva:~/Downloads/SaxonHE10-3J$ java -cp saxon-he-10.3.jar net.sf.saxon.Query -q:/home/apurva/Desktop/DM/xquery/XML_Analytics/project_files/query_rollup.xqy -s:/home/apurva/Desktop/DM/xquery/XML_Analytics/project_files/factProductSales.xml
Source document ignored - query can be evaluated without reference to the context item
<?xml version="1.0" encoding="UTF-8"?>
customer_id = 1         customer_name = Henry Ford              total_cost = 513.5
 
customer_id = 2         customer_name = Bill Gates              total_cost = 244
 
customer_id = 3         customer_name = Muskan Shaikh           total_cost = 491.5
apurva@apurva:~/Downloads/SaxonHE10-3J$ 

:)
