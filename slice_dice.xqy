(:
let $fact_product_entries:= doc("factProductSales.xml")//ProductSale
for $x in $fact_product_entries
where $x/customer_id>1
return $x/transaction_id

:)



(: below is the **slice operation** by joining 2 tables and printing product_name from product dimension table along with other info in fact table
slice operation on product_id
:)

(:

for $fact in doc("factProductSales.xml")//ProductSale, $prod in doc("dimProd.xml")//Product
where $fact/product_id = $prod/ProductKey and $fact/product_id>2
return concat("&#xA;","transaction_id = ", string($fact/transaction_id), "&#x9;", "store_id= ", string($fact/store_id), "&#x9;", "customer_id = ", string($fact/customer_id), "&#x9;", "product_id = ", string($fact/product_id), "&#x9;", "product_name = ", string($prod/ProductName) ,"&#x9;", "quantity = ", string($fact/quantity), "&#xA;")

:)

(: below is the **dice operation** by joining 2 tables and printing product_name from product dimension table alog with other info in fact table
dice operation on product_id and store_id
:)


for $fact in doc("factProductSales.xml")//ProductSale, $prod in doc("dimProd.xml")//Product
where $fact/product_id = $prod/ProductKey and $fact/product_id>2 and $fact/store_id>1
return concat("&#xA;","transaction_id = ", string($fact/transaction_id), "&#x9;", "store_id= ", string($fact/store_id), "&#x9;", "customer_id = ", string($fact/customer_id), "&#x9;", "product_id = ", string($fact/product_id), "&#x9;", "product_name = ", string($prod/ProductName) ,"&#x9;", "quantity = ", string($fact/quantity), "&#xA;")








