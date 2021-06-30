import java.util.*;
public class Slice
{
    //----------------- ************* start of the slice function  **************** ----------------------------
    static Scanner sc= new Scanner(System.in);
    public static void slice()
    {
        System.out.println("do you want to perform slice by operation adding dimension table (yes or no)");

        if(sc.next().equals("yes")) //adding dimensions to perform slice
        {
            String xquery = "for $fact in doc(\"factProductSales.xml\")//ProductSale";
            System.out.println("Enter the attribute/col for slice operation from primary keys of dimension table (store_id, customer_id, product_id)");
            String fk_ft = sc.next(); //foreign key fact table
            if(fk_ft.equals("product_id")) // slicing on product id
            {
                System.out.println("Enter the product_name:");
                String product_name;
                product_name= sc.nextLine();
                product_name= sc.nextLine();
                xquery=xquery.concat(", $prod in doc(\"dimProd.xml\")//Product where " +
                        "$fact/product_id=$prod/ProductKey and $prod/ProductName=\""+product_name+"\"");
                //xquery=xquery.concat(" return $fact");
                xquery = xquery.concat(" return concat(\"&#xA;\",\"transaction_id = \", string($fact/transaction_id), \"&#x9;\", " +
                        "\"store_id= \", string($fact/store_id), \"&#x9;\", \"customer_id = \", string($fact/customer_id), " +
                        "\"&#x9;\", \"product_id = \", string($fact/product_id), \"&#x9;\", " +
                        "\"&#x9;\", \"product_name = \", string($prod/ProductName), \"&#x9;\", \"quantity = \", string($fact/quantity)," +
                        " \"&#x9;\", \"sales_total_cost= \", string($fact/sales_total_cost), \"&#x9;\"," +
                        "\"product_actual_cost= \", string($fact/product_actual_cost), \"&#x9;\", \"deviation= \", " +
                        "string($fact/deviation), \"&#xA;\")");
            }
            else if(fk_ft.equals("customer_id")) //slicing on customer id
            {
                System.out.println("Enter the customer_name: ");
                String cust_name;
                cust_name= sc.nextLine();
                cust_name= sc.nextLine();
                xquery=xquery.concat(", $cust in doc(\"dimcust.xml\")//customer where " +
                        "$fact/customer_id=$cust/CustomerID and $cust/CustomerName=\""+cust_name+"\"");
                //xquery=xquery.concat(" return $fact");
                xquery = xquery.concat(" return concat(\"&#xA;\",\"transaction_id = \", string($fact/transaction_id), \"&#x9;\", " +
                        "\"store_id= \", string($fact/store_id), \"&#x9;\", \"customer_id = \", string($fact/customer_id), " +
                        "\"&#x9;\", \"product_id = \", string($fact/product_id), \"&#x9;\", " +
                        "\"&#x9;\", \"customer_name = \", string($cust/CustomerName), \"&#x9;\", \"quantity = \", string($fact/quantity)," +
                        " \"&#x9;\", \"sales_total_cost= \", string($fact/sales_total_cost), \"&#x9;\"," +
                        "\"product_actual_cost= \", string($fact/product_actual_cost), \"&#x9;\", \"deviation= \", " +
                        "string($fact/deviation), \"&#xA;\")");
            }
            else if(fk_ft.equals("store_id")) //slicing on store id
            {
                System.out.println("Enter the store_name: ");
                String store_name;
                store_name= sc.nextLine();
                store_name= sc.nextLine();
                xquery=xquery.concat(", $store in doc(\"DimStores.xml\")//store where " +
                        "$fact/store_id=$store/StoreID and $store/StoreName=\""+store_name+"\"");
                //xquery=xquery.concat(" return $fact");
                xquery = xquery.concat(" return concat(\"&#xA;\",\"transaction_id = \", string($fact/transaction_id), \"&#x9;\", " +
                        "\"store_id= \", string($fact/store_id), \"&#x9;\", \"customer_id = \", string($fact/customer_id), " +
                        "\"&#x9;\", \"product_id = \", string($fact/product_id), \"&#x9;\", " +
                        "\"&#x9;\", \"store_name = \", string($store/StoreName), \"&#x9;\", \"quantity = \", string($fact/quantity)," +
                        " \"&#x9;\", \"sales_total_cost= \", string($fact/sales_total_cost), \"&#x9;\"," +
                        "\"product_actual_cost= \", string($fact/product_actual_cost), \"&#x9;\", \"deviation= \", " +
                        "string($fact/deviation), \"&#xA;\")");
            }
            System.out.println(xquery);
            FireQuery.firingQuery(xquery);
        } //end of if condition-- adding dimensions to perform slice

        else //slice without dimension table
        {
            System.out.println("Enter the attribute/col for slice operation from primary keys of dimension table (store_id, customer_id, product_id)");
            String attribute = sc.next();
            System.out.println("which operation you want to perform from(<, >, =, <=, >=, != )");
            String operation = sc.next();
            System.out.println("Enter the value(integer) for the attribute to perform slice operation");
            int val = sc.nextInt();

            //forming query
            String xquery = "let $fact:= doc(\"factProductSales.xml\")//ProductSale for $x in $fact where $x/";
            xquery = xquery.concat(attribute);
            xquery = xquery.concat(operation);
            xquery = xquery.concat(Integer.toString(val));
            //xquery = xquery.concat(" return $x"); //to return entire ProductSale element
            xquery = xquery.concat(" return concat(\"&#xA;\",\"transaction_id = \", string($x/transaction_id), \"&#x9;\", " +
                    "\"store_id= \", string($x/store_id), \"&#x9;\", \"customer_id = \", string($x/customer_id), " +
                    "\"&#x9;\", \"product_id = \", string($x/product_id), \"&#x9;\", \"quantity = \", string($x/quantity)," +
                    " \"&#x9;\", \"sales_total_cost= \", string($x/sales_total_cost), \"&#x9;\"," +
                    "\"product_actual_cost= \", string($x/product_actual_cost), \"&#x9;\", \"deviation= \", " +
                    "string($x/deviation), \"&#xA;\")");

            System.out.println(xquery);
            FireQuery.firingQuery(xquery);

        }// end of slice without dimension table
        //System.out.println(xquery);
        //firingQuery(xquery);

    } // end  of slice method

//----------------- ************* end of the slice function  **************** ----------------------------

}
