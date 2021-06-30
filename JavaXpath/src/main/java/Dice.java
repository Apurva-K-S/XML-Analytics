import java.util.*;
public class Dice
{
//----------------- ************* start of the dice function  **************** ----------------------------
    static Scanner sc= new Scanner(System.in);
    public static void dice()
    {
        System.out.println("do you want to perform dice operation by adding dimension table (yes or no)");
        if(sc.next().equals("yes")) //adding dimensions to perform dice
        {
            System.out.println("****^^^^ performing dice operation by adding dimension tables ****^^^^ ");
            String xquery = "for $fact in doc(\"factProductSales.xml\")//ProductSale";
            String where_condition= " where ";
            String dim_names_in_return="";
            while (true) {
                System.out.println("Enter the attribute/col for dice operation from primary keys of dimension table (store_id, customer_id, product_id)");
                String fk_ft = sc.next(); //foreign key fact table
                if(fk_ft.equals("product_id")) // slicing on product id
                {
                    //adding dimProd.xml to the for loop
                    xquery=xquery.concat(", $prod in doc(\"dimProd.xml\")//Product");
                    System.out.println("Enter the product_name:");
                    String product_name;
                    product_name= sc.nextLine();
                    product_name= sc.nextLine();
                    where_condition=where_condition.concat("$fact/product_id=$prod/ProductKey and $prod/ProductName=\""+product_name+"\"");
                    dim_names_in_return=dim_names_in_return.concat("\"&#x9;\", \"product_name = \", string($prod/ProductName), ");
                }
                else if(fk_ft.equals("customer_id")) // slicing on customer id
                {
                    //adding dimProd.xml to the for loop
                    xquery=xquery.concat(", $cust in doc(\"dimcust.xml\")//customer");
                    System.out.println("Enter the customer_name:");
                    String customer_name;
                    customer_name= sc.nextLine();
                    customer_name= sc.nextLine();
                    where_condition=where_condition.concat("$fact/customer_id=$cust/CustomerID and $cust/CustomerName=\""+customer_name+"\"");
                    dim_names_in_return=dim_names_in_return.concat("\"&#x9;\", \"customer_name = \", string($cust/CustomerName), ");
                    //issue with printing customer name from dimcust.xml .... so hardcoding the name from input directly
                    // only in customer code hardcoding and only at this place
                    //dim_names_in_return=dim_names_in_return.concat("\"&#x9;\", \"customer_name = \", \" "+ customer_name + "\", ");
                }
                else if(fk_ft.equals("store_id")) // slicing on store id
                {
                    //adding dimProd.xml to the for loop
                    xquery=xquery.concat(", $store in doc(\"DimStores.xml\")//store");
                    System.out.println("Enter the store_name:");
                    String store_name;
                    store_name= sc.nextLine();
                    store_name= sc.nextLine();
                    where_condition=where_condition.concat("$fact/store_id=$store/StoreID and $store/StoreName=\""+store_name+"\"");
                    dim_names_in_return=dim_names_in_return.concat("\"&#x9;\", \"store_name = \", string($store/StoreName), ");
                }
                else   //when the dimension primary key doesn't match with existing keys
                {
                    System.out.println("dimension key doesn't match with any dimension in current star schema");
                    continue;
                }
                System.out.println("do you want to input more dimension to dice operation to filter (yes or no)");
                if (sc.next().equals("no"))
                    break; //breaking out of infinite while loop

                where_condition= where_condition.concat(" and "); // adding and in the where condition

            } // end of while loop
            //xquery=xquery.concat(" return $x"); //to return entire ProductSale element
            xquery= xquery.concat(where_condition);
            xquery = xquery.concat(" return concat(\"&#xA;\",\"transaction_id = \", string($fact/transaction_id), \"&#x9;\", " +
                    "\"store_id= \", string($fact/store_id), \"&#x9;\", \"customer_id = \", string($fact/customer_id), " +
                    "\"&#x9;\", \"product_id = \", string($fact/product_id), \"&#x9;\", \"quantity = \", string($fact/quantity)," +
                    " \"&#x9;\", \"sales_total_cost= \", string($fact/sales_total_cost), \"&#x9;\"," +
                    "\"product_actual_cost= \", string($fact/product_actual_cost), \"&#x9;\", \"deviation= \", " +
                    "string($fact/deviation), "+ dim_names_in_return + "\"&#xA;\")");

            System.out.println(xquery);
            FireQuery.firingQuery(xquery);
        }
        //forming query
        else      //dice operation without adding dimension tables
        {
            System.out.println("****^^^^ performing dice operation without adding dimension tables  ****^^^^ ");
            String xquery = "for $fact in doc(\"factProductSales.xml\")//ProductSale where ";
            while (true) {
                System.out.println("Enter the attribute/col for dice operation from primary keys of dimension table (store_id, customer_id, product_id)");
                String attribute = sc.next();
                System.out.println("which operation you want to perform from(<, >, =, <=, >=, != )");
                String operation = sc.next();
                System.out.println("Enter the value(integer) for the attribute to perform dice operation");
                int val = sc.nextInt();
                xquery = xquery.concat("$fact/");
                xquery = xquery.concat(attribute);
                xquery = xquery.concat(operation);
                xquery = xquery.concat(Integer.toString(val));
                System.out.println("you want to input more dimension to dice operation to filter  (yes or no)");
                if (sc.next().equals("no"))
                    break; //breaking out of infinite while loop

                xquery = xquery.concat(" and "); // adding and in the where condition

            }
            //xquery=xquery.concat(" return $x"); //to return entire ProductSale element
            xquery = xquery.concat(" return concat(\"&#xA;\",\"transaction_id = \", string($fact/transaction_id), \"&#x9;\", " +
                    "\"store_id= \", string($fact/store_id), \"&#x9;\", \"customer_id = \", string($fact/customer_id), " +
                    "\"&#x9;\", \"product_id = \", string($fact/product_id), \"&#x9;\", \"quantity = \", string($fact/quantity)," +
                    " \"&#x9;\", \"sales_total_cost= \", string($fact/sales_total_cost), \"&#x9;\"," +
                    "\"product_actual_cost= \", string($fact/product_actual_cost), \"&#x9;\", \"deviation= \", " +
                    "string($fact/deviation), \"&#xA;\")");

            System.out.println(xquery);
            FireQuery.firingQuery(xquery);
        }
    }// end of dice method

//----------------- ************* end of the dice function  **************** ----------------------------

}
