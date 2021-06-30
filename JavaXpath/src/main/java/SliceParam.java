import java.util.*;

public class SliceParam {
    static Scanner sc= new Scanner(System.in);
    public static void slice_take_input()
    {
        System.out.println("Enter the data warehouse name on which you want to perform this operation (folder name is same as data warehouse name)");
        String dw_name= sc.next();

        System.out.println("Select one of the below:\nPress 1 for performing slice operation alone on fact table" +
                "\nPress 2 to slice by joining dimension table(you can choose dimension attributes as entry points)");
        int selected_option= sc.nextInt();

        if(selected_option==1) //slice alone on fact table
        {
            //read fact table name from dvfa file in the dw_name folder

            //initially call loadParseDvfa() method of GetRequiredFromDvfa.java class to fill the buckets
            //all methods of GetRequiredFromDvfa.java class are static
            System.out.println("******** You chose to slice using fact table only *********");
            GetRequiredFromDvfa dvfa_parse_object= new GetRequiredFromDvfa();
            dvfa_parse_object.loadParseDvfa(dw_name);
            //we just get name of the table/document -- //appending .xml extension to fact_name string
            String fact_table_name = dvfa_parse_object.getFactTableName().concat(".xml");

            String fact_table_innermost_wrapper = dvfa_parse_object.getFactInnermostWrapper();
            
            System.out.print("select one of these dimension foreign key attributes: (");
            
            for (String name : dvfa_parse_object.getFactTableFks())
                System.out.print(name + ", ");
            
            System.out.println(") for slicing");
            
            String attribute = sc.next();
            
            System.out.println("if attribute "+ attribute+ "'s domain is numeric select operator from ( =, <, >, !=, <=, >= ) ");
            
            System.out.println("if attribute "+ attribute+ "'s domain is string select operator from ( =, != ) ");
            
            String operation = sc.next();
            
            System.out.println("Enter the value for " + attribute + " to perform slice");
            
            String value = sc.nextLine();
            
            value=sc.nextLine();

            sliceAloneOnFact(dw_name, fact_table_name, fact_table_innermost_wrapper, attribute, operation, value);
        } // end of if condition -- slice alone on fact table
        else if(selected_option==2) //slice by joining dimension table
        {
            System.out.println("******** You chose to slice by joining dimension table with fact table ********");
            
            GetRequiredFromDvfa dvfa_parse_object= new GetRequiredFromDvfa();
            
            dvfa_parse_object.loadParseDvfa(dw_name);
            
            //we just get name of the table/document -- //appending .xml extension to fact_name string
            
            String fact_table_name = dvfa_parse_object.getFactTableName().concat(".xml");
            
            String fact_table_innermost_wrapper = dvfa_parse_object.getFactInnermostWrapper();
            
            //String fact_table_pk= GetRequiredFromDvfa.getFactPk();
            
            System.out.print("select one of the dimension table from: (");
            
            for (String name : dvfa_parse_object.getDimensionTableNames())
                System.out.print(name + ", ");
            
            System.out.println(") to join with fact table");
            
            String dim_name= sc.next();
            
            System.out.print("select foreign key from: (");
            
            for (String name : dvfa_parse_object.getFactTableFks())
                System.out.print(name + ", ");
            
            System.out.println(") which corresponds to primary key of selected "+ dim_name+ " table");
            
            String fact_table_fk_of_selected_dim= sc.next();
            
            String dim_table_innermost_wrapper= dvfa_parse_object.getDimensionTableInnermostWrapper(dim_name);
            
            String dim_table_pk= dvfa_parse_object.getDimensionTablePk(dim_name);
            
            System.out.println("select one of the below attribute of "+dim_name +" dimension table to slice");
            
            for (String name : dvfa_parse_object.getDimensionTableAttributes(dim_name))
                System.out.print(name + ", ");
            
            System.out.println("");
            
            String attribute_name= sc.next();

            dim_name= dim_name.concat(".xml"); //adding .xml extension

            System.out.println("if attribute "+ attribute_name+ "'s domain is numeric select operator from ( =, <, >, !=, <=, >= ) ");
            
            System.out.println("if attribute "+ attribute_name+ "'s domain is string select operator from ( =, != ) ");
            
            String operator= sc.next();
            
            System.out.println("Enter the value for "+ attribute_name + ": ");
            
            String attr_value= sc.nextLine();
            
            attr_value=sc.nextLine();
            
            sliceByJoiningDim(dw_name, fact_table_name, fact_table_innermost_wrapper, dim_name, fact_table_fk_of_selected_dim,
                    dim_table_innermost_wrapper, dim_table_pk, attribute_name, operator, attr_value);
        }
    } //end of slice_take_input method


    public static void sliceByJoiningDim(String dw_name, String fact_table_name, String fact_table_innermost_wrapper,
                                         String dim_name, String fact_table_fk_of_selected_dim,
                                         String dim_table_innermost_wrapper,
                                         String dim_table_pk, String attribute_name, String operator, String value)
    {
        String xquery = "for $fact in doc(\""+ fact_table_name + "\")//"+ fact_table_innermost_wrapper;
        xquery= xquery.concat(", $dim in doc(\""+dim_name+ "\")//" + dim_table_innermost_wrapper +" where ");
        xquery= xquery.concat("$fact/"+ fact_table_fk_of_selected_dim+ "=$dim/" + dim_table_pk+ " and $dim/"+ attribute_name+ operator+"\""+ value+"\"");
        xquery= xquery=xquery.concat(" return $fact");
        System.out.println(xquery);
        FireQuery.firingQueryParam(xquery, dw_name, fact_table_name);
    }

    public static void sliceAloneOnFact(String dw_name, String fact_table_name, String fact_table_innermost_wrapper,
                                        String attribute, String operation, String value )
    {
        //we need fact table
        //attribute on which we want to slice, operation to slice, value to slice on attribute
        //forming query
        String xquery = "for $x in doc(\""+ fact_table_name + "\")//"+ fact_table_innermost_wrapper+ " where $x/";
        xquery = xquery.concat(attribute);
        xquery = xquery.concat(operation);
        xquery = xquery.concat("\""+ value+ "\"");
        xquery = xquery.concat(" return $x"); //to return entire ProductSale element
//        xquery = xquery.concat(" return concat(\"&#xA;\",\"transaction_id = \", string($x/transaction_id), \"&#x9;\", " +
//                "\"store_id= \", string($x/store_id), \"&#x9;\", \"customer_id = \", string($x/customer_id), " +
//                "\"&#x9;\", \"product_id = \", string($x/product_id), \"&#x9;\", \"quantity = \", string($x/quantity)," +
//                " \"&#x9;\", \"sales_total_cost= \", string($x/sales_total_cost), \"&#x9;\"," +
//                "\"product_actual_cost= \", string($x/product_actual_cost), \"&#x9;\", \"deviation= \", " +
//                "string($x/deviation), \"&#xA;\")");

        System.out.println(xquery);
        FireQuery.firingQueryParam(xquery, dw_name, fact_table_name);

    }

}
