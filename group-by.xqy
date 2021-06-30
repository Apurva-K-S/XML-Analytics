xquery version "3.0";

for $item in doc("factProductSales.xml")//ProductSale
group by $d := ($item/customer_id)
return
    <group cust_id="{$d}" totQuant="{sum($item/sales_total_cost)}"/>
