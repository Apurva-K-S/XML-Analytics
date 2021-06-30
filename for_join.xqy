(: dice - 2 :)
let $fact_product_entries := (doc("factProductSales.xml")/FactProductSales/ProductSales-collection/ProductSale)
let $dim_cust_entries := (doc("dimcust.xml")/DimCustomer/Customer-collection/customer)

return <table><tr><th>customer_id</th> <th> cust_id </th> <th> total_sales_cost </th> </tr>
{
   for $x in $fact_product_entries, $y in $dim_cust_entries
   where $y/CustomerID = $x/customer_id and $y/CustomerName= "Bill Gates"
   return <tr><td>{string($y/CustomerName)}</td> <td> {string($x/transaction_id)} </td> <td> {string($x/sales_total_cost)} </td> </tr>
}
</table>
