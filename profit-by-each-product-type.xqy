(: roll up eliminating customer, store dimensions by applying groupBy on product_id
Meaning of query -- finding the total quantity, total cost price, total selling price, total profit incurred by selling each type of product to customers across all the stores 
Output- printing product_id, product_name, total_cost_price, total_selling_price, total_profit :)




xquery version "3.0";

for $fact in doc("factProductSales.xml")//ProductSale, $prod in doc("dimProd.xml")//Product
where $fact/product_id = $prod/ProductKey
group by $product := ($fact/product_id)
order by $product
return concat("&#xA;","product_id = ", $product, "&#x9;&#x9;", "product_name = ", string($prod/ProductName) ,"&#x9;&#x9;", "total_quantity_sold= ", sum($fact/quantity), "&#x9;&#x9;", "total_cost_price = ", sum($fact/product_actual_cost), "&#x9;&#x9;", "total_selling_price = ", sum($fact/sales_total_cost), "&#x9;&#x9;", "total_profit = ", sum($fact/sales_total_cost)-sum($fact/product_actual_cost), "&#xA;")



(: ACTUAL OUTPUT 

apurva@apurva-Inspiron-3542:~/Downloads/SaxonHE10-3J$ java -cp saxon-he-10.3.jar net.sf.saxon.Query -t -wrap -q:/home/apurva/Music/profit-by-each-product-type.xqy -s:/home/apurva/Music/factProductSales.xml

product_id = 1		product_name = Wheat Floor 1kg		total_quantity_sold= 6		total_cost_price = 44.5		total_selling_price = 50		total_profit = 5.5

product_id = 2		product_name = Rice Grains 1kg		total_quantity_sold= 10		total_cost_price = 106		total_selling_price = 117.5		total_profit = 11.5

product_id = 3		product_name = SunFlower Oil 1 ltr		total_quantity_sold= 7		total_cost_price = 294		total_selling_price = 304.5		total_profit = 10.5

product_id = 4		product_name = Nirma Soap		total_quantity_sold= 18		total_cost_price = 324		total_selling_price = 360		total_profit = 36

product_id = 5		product_name = Arial Washing Powder 1kg		total_quantity_sold= 3		total_cost_price = 405		total_selling_price = 417		total_profit = 12

:)

