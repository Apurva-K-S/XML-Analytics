(: pivot - 1 :)

(:
xquery version "3.0";

for $fact in doc("factProductSales.xml")//ProductSale, $cust in doc("dimcust.xml")//customer
group by $d := ($fact/product_id), $p := ($fact/customer_id)
order by $d, $p
return if ($fact/customer_id = 1) then concat("&#xA;","product_id = ", $d ,"&#x9;&#x9;", "C1 = ", sum($fact/sales_total_cost), "&#x9;&#x9;")
       else if($fact/customer_id = 2) then concat("C2 = ", sum($fact/sales_total_cost), "&#x9;&#x9;") 
       else concat("C3 = ", sum($fact/sales_total_cost), "&#xA;")
:)
(:

PIVOT - 1 OUTPUT

apurva@apurva:~/Downloads/SaxonHE10-3J$ java -cp saxon-he-10.3.jar net.sf.saxon.Query -q:/home/apurva/Desktop/DM/xquery/XML_Analytics/project_files/trail_query.xqy -s:/home/apurva/Desktop/DM/xquery/XML_Analytics/project_files/factProductSales.xml
Source document ignored - query can be evaluated without reference to the context item
<?xml version="1.0" encoding="UTF-8"?>
product_id = 1          C1 = 26          C3 = 24
 
product_id = 2          C1 = 54.5                C2 = 37                 C3 = 26
 
product_id = 3          C1 = 174                 C2 = 87                 C3 = 43.5
 
product_id = 4          C1 = 120                 C2 = 120                C3 = 120
 
product_id = 5          C1 = 139                 C3 = 278
apurva@apurva:~/Downloads/SaxonHE10-3J$ 

:)

(: pivot - 2 :)

xquery version "3.0";


for $fact in doc("factProductSales.xml")//ProductSale, $cust in doc("dimcust.xml")//customer
group by $p := ($fact/customer_id), $d := ($fact/product_id)
order by $p, $d
return if($fact/product_id = 1) then concat("&#xA;","customer_id = ", $p, "&#x9;&#x9;", "P1 = ", sum($fact/sales_total_cost), "&#xA;")
       else if($fact/product_id = 2) then concat("customer_id = ", $p ,"&#x9;","P2 = ", sum($fact/sales_total_cost), "&#xA;") 
       else if($fact/product_id = 3) then concat("customer_id = ", $p ,"&#x9;", "P3 = ", sum($fact/sales_total_cost), "&#xA;") 
       else if($fact/product_id = 4) then concat("customer_id = ", $p ,"&#x9;", "P4 = ", sum($fact/sales_total_cost), "&#xA;") 
       else concat("customer_id = ", $p , "&#x9;","P5 = ", sum($fact/sales_total_cost), "&#xA;")


(: 
PIVOT - 2 OUTPUT 

apurva@apurva:~/Downloads/SaxonHE10-3J$ java -cp saxon-he-10.3.jar net.sf.saxon.Query -q:/home/apurva/Desktop/DM/xquery/XML_Analytics/project_files/trail_query.xqy -s:/home/apurva/Desktop/DM/xquery/XML_Analytics/project_files/factProductSales.xml
Source document ignored - query can be evaluated without reference to the context item
<?xml version="1.0" encoding="UTF-8"?>
customer_id = 1         P1 = 26
 customer_id = 1        P2 = 54.5
 customer_id = 1        P3 = 174
 customer_id = 1        P4 = 120
 customer_id = 1        P5 = 139
 customer_id = 2        P2 = 37
 customer_id = 2        P3 = 87
 customer_id = 2        P4 = 120
 
customer_id = 3         P1 = 24
 customer_id = 3        P2 = 26
 customer_id = 3        P3 = 43.5
 customer_id = 3        P4 = 120
 customer_id = 3        P5 = 278
apurva@apurva:~/Downloads/SaxonHE10-3J$ 
:)