let $fact_product_entries:= doc("factProductSales.xml")/ProductSales-collection/ProductSale
for $x in $fact_product_entries
where $x/customer_id>1
return $x/transaction_id
