
(:  select customer_id, sum(sales_total_cost) from FactProductSales group by customer_id with ROLLUP; :)

let $fact_product_entries := (doc("factProductSales.xml")/FactProductSales/ProductSales-collection/ProductSale)

for $s in $fact_product_entries
let $storeno := $s/store_id
group by $storeno
return <store number="{$storeno}" total-qty="{sum($s/quantity)}"/>
