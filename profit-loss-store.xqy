(: profit/loss incurred by particular store given store id as input to the query:)

let $fact_product_entries := (doc("factProductSales.xml")/FactProductSales/ProductSales-collection/ProductSale)
let $dim_store_entries := (doc("DimStores.xml")/DimStores/store-collection/store)

let $result := sum( 
    for $f in $fact_product_entries, $s in $dim_store_entries 
    where $f/store_id = 1 and $f/store_id = $s/StoreID
    return ($f/sales_total_cost - $f/product_actual_cost)
)
return $result
