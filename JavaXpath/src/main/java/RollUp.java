import java.util.*;
public class RollUp
{
//----------------- ************* start of the drillup/rollup function  **************** ----------------------------
    static Scanner sc= new Scanner(System.in);
    public static void rollUp()
    {
        //forming query
        String xquery = "for $fact in doc(\"factProductSales.xml\")//ProductSale";
        System.out.println("Do you wanna join dimension table in the query along with fact table(yes or no)");
        if(sc.next().equals("yes"))   //joining dimensions dynamically
        {
            xquery=xquery.concat(", ");
            System.out.println("choose the dimension table from (dimProd, dimcust, DimStores)");
            String dim_name= sc.next();
            if(dim_name.equals("dimProd"))
            {
                xquery=xquery.concat("$prod in doc(\""+dim_name+".xml\")//Product ");
                xquery=xquery.concat("where $fact/product_id=$prod/ProductKey ");
                xquery=xquery.concat("group by $d:=$fact/product_id order by $d");
                xquery = xquery.concat(" return concat(\"&#xA;\", \"product_id = \", $d, \"&#x9;\", \"product_name = \", " +
                        "string($prod/ProductName),\"&#x9;\", \"total_quantity_sold = \", sum($fact/quantity)," +
                        " \"&#x9;\", \"total_cost_price= \", sum($fact/product_actual_cost)," +
                        " \"&#x9;\", \"total_selling_price= \", sum($fact/sales_total_cost), " +
                        " \"&#x9;\", \"total_profit= \", sum($fact/sales_total_cost)-sum($fact/product_actual_cost), \"&#xA;\")");
            }
            else if(dim_name.equals("dimcust"))
            {
                xquery=xquery.concat("$cust in doc(\""+dim_name+".xml\")//customer ");
                xquery=xquery.concat("where $fact/customer_id=$cust/CustomerID ");
                xquery=xquery.concat("group by $d:=$fact/customer_id order by $d");
                xquery = xquery.concat(" return concat(\"&#xA;\", \"customer_id = \", $d, \"&#x9;\", \"customer_name = \", " +
                        "string($cust/CustomerName),\"&#x9;\", \"total_quantity_sold = \", sum($fact/quantity)," +
                        " \"&#x9;\", \"total_cost_price= \", sum($fact/product_actual_cost)," +
                        " \"&#x9;\", \"total_selling_price= \", sum($fact/sales_total_cost), " +
                        " \"&#x9;\", \"total_profit= \", sum($fact/sales_total_cost)-sum($fact/product_actual_cost), \"&#xA;\")");
            }
            else if(dim_name.equals("DimStores"))
            {
                xquery=xquery.concat("$store in doc(\""+dim_name+".xml\")//store ");
                xquery=xquery.concat("where $fact/store_id=$store/StoreID ");
                xquery=xquery.concat("group by $d:=$fact/store_id order by $d");
                xquery = xquery.concat(" return concat(\"&#xA;\", \"store_id = \", $d, \"&#x9;\", \"store_name = \", " +
                        "string($store/StoreName),\"&#x9;\", \"total_quantity_sold = \", sum($fact/quantity)," +
                        " \"&#x9;\", \"total_cost_price= \", sum($fact/product_actual_cost)," +
                        " \"&#x9;\", \"total_selling_price= \", sum($fact/sales_total_cost), " +
                        " \"&#x9;\", \"total_profit= \", sum($fact/sales_total_cost)-sum($fact/product_actual_cost), \"&#xA;\")");
            }

        } //end of joining dimensions dynamically if condition
        else // without joining dimensions
        {
            xquery=xquery.concat(" group by ");
            String print_out="";
            while(true)
            {

                System.out.println("Enter the attribute/col for rollup operation from primary keys of dimension table (store_id, customer_id, product_id)");
                String attribute = sc.next();
                xquery=xquery.concat("$dim_"+attribute+":="+"$fact/"+attribute);
                //xquery = xquery.concat(attribute);

                print_out=print_out.concat("\""+attribute+"=\","+"$dim_"+attribute+", "+"\"&#x9;\""+", ");
                System.out.println("you want to input more dimensions to rollup operation (yes or no)");
                if(sc.next().equals("no"))
                    break; //breaking out of infinite while loop

                xquery= xquery.concat(", "); // adding and in the group by condition

            } // end of while loop
            xquery = xquery.concat(" return concat(\"&#xA;\", " + print_out +"\"&#x9;\", \"total_quantity_sold = \", sum($fact/quantity)," +
                    " \"&#x9;\", \"total_cost_price= \", sum($fact/product_actual_cost)," +
                    " \"&#x9;\", \"total_selling_price= \", sum($fact/sales_total_cost), " +
                    " \"&#x9;\", \"total_profit= \", sum($fact/sales_total_cost)-sum($fact/product_actual_cost), \"&#xA;\")");


        }// end of else without joining dimensions

        System.out.println(xquery);
        FireQuery.firingQuery(xquery);

    } // end of rollUp method

//----------------- ************* end of the drillup/rollup function  **************** ----------------------------

}
