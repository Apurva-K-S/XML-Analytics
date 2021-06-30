import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class RollUPParam {

    //need to implement similar to SliceParam
    //take SliceParam as reference
    static Scanner sc= new Scanner(System.in);
    public static void rollup_take_input() {
        //important thing here is -- we need to ask user on which attributes of fact_table he
        //wants to apply aggregation funtions (ex:- sum() )
        //aggregation functions are applied only on numeric fields of fact_table
        System.out.println("Enter the data warehouse name on which you want to perform this operation (folder name is same as data warehouse name)");
        String dw_name = sc.next();

        System.out.println("Select one of the below:\nPress 1 for performing rollup operation alone on fact table" +
                "\nPress 2 to rollup by joining dimension table(you can choose dimension attributes as entry points)");
        int selected_option = sc.nextInt();

        if (selected_option == 1) //rollup alone on fact table
        {

            System.out.println("******** You chose to rollup using fact table only *********");
            GetRequiredFromDvfa dvfa_parse_object = new GetRequiredFromDvfa();
            dvfa_parse_object.loadParseDvfa(dw_name);
            //we just get name of the table/document -- //appending .xml extension to fact_name string

            String fact_table_name = dvfa_parse_object.getFactTableName().concat(".xml");
            String fact_table_innermost_wrapper = dvfa_parse_object.getFactInnermostWrapper();
            ArrayList<String> fact_table_groupby_attribute_list = new ArrayList<>();
            ArrayList<String> fact_table_aggr_attr_list= new ArrayList<>();

            ArrayList<String> all_attributes= dvfa_parse_object.getAllAttributesFactTable();
            //removing pk, fks from the all_attributes list so that we can pick remaining attrs for aggregation
            
            ArrayList<String> fact_fks= dvfa_parse_object.getFactTableFks();

            all_attributes.removeAll(fact_fks);
            
            String fact_pk= dvfa_parse_object.getFactPk();
            
            all_attributes.remove(fact_pk);
            //ArrayList<String> operator_list= new ArrayList<>();
            //ArrayList<String> value_list= new ArrayList<>();
            
            while (true) // rollup attributes input while loop
            {

                System.out.print("select one of these dim foreign key attribute: (");

                for (String name : dvfa_parse_object.getFactTableFks())
                    System.out.print(name + ", ");
                
                System.out.println(") for rollup(group by)");
                
                String attribute = sc.next();
                
                fact_table_groupby_attribute_list.add(attribute);
                
//                System.out.println("if attribute " + attribute + "'s domain is numeric select operator from ( =, <, >, !=, <=, >= ) ");
//                System.out.println("if attribute " + attribute + "'s domain is string select operator from ( =, != ) ");
//                operator_list.add(sc.next());
//                System.out.println("Enter the value for " + attribute + " to perform dice");
//                value_list.add(sc.next());


                //removing selected fk so that it won't get printed again in while loop....
                dvfa_parse_object.getFactTableFks().remove(attribute);

                if (dvfa_parse_object.getFactTableFks().size() == 0)//as we have selected all attributes for dice
                    break;
                System.out.println("you want to input more dimension to rollup(yes or no)");
                if (sc.next().equals("no"))
                    break; //breaking out of infinite while loop
            }// end of rollup attributes input while loop

            //now storing all the attributes on which we want to apply aggregation on
            while(true)
            {
                System.out.print("select one of these attributes(");
                for (String name : all_attributes)
                    System.out.print(name + ", ");
                System.out.println(") to apply aggregation functions");
                String attribute_name= sc.next();
                all_attributes.remove(attribute_name); //removing already selected attribute/column
                fact_table_aggr_attr_list.add(attribute_name);
                if (all_attributes.size() == 0)//as we have selected all attributes for dice
                    break;
                System.out.println("you want to input more attributes for aggregation (yes or no)");
                if (sc.next().equals("no"))
                    break; //breaking out of infinite while loop
            }
            rollupAloneOnFact(dw_name, fact_table_name, fact_table_innermost_wrapper,
                    fact_table_groupby_attribute_list, fact_table_aggr_attr_list);
        } // end of if condition groupBy alone on fact table
        else if (selected_option == 2) //dice by joining dimension table
        {
            System.out.println("******** You chose to rollup joining dimension table *********");
            GetRequiredFromDvfa dvfa_parse_object = new GetRequiredFromDvfa();
            dvfa_parse_object.loadParseDvfa(dw_name);
            //we just get name of the table/document -- //appending .xml extension to fact_name string
            String fact_table_name = dvfa_parse_object.getFactTableName().concat(".xml");
            String fact_table_innermost_wrapper = dvfa_parse_object.getFactInnermostWrapper();
            ArrayList<String> fact_table_groupby_attribute_list = new ArrayList<>();
            ArrayList<String> fact_table_aggr_attr_list= new ArrayList<>();

            ArrayList<String> all_attributes= dvfa_parse_object.getAllAttributesFactTable();
            //removing pk, fks from the all_attributes list so that we can pick remaining attrs for aggregation
            ArrayList<String> fact_fks= dvfa_parse_object.getFactTableFks();
            all_attributes.removeAll(fact_fks);
            String fact_pk= dvfa_parse_object.getFactPk();
            all_attributes.remove(fact_pk);

            ArrayList<String> dim_name_list= new ArrayList<>();
            ArrayList<String> dim_table_innermost_wrapper_list= new ArrayList<>();
            ArrayList<String> dim_table_pk_list= new ArrayList<>();
            ArrayList<String> dim_attribute_list= new ArrayList<>();
            ArrayList<String> operator_list= new ArrayList<>();
            ArrayList<String> value_list= new ArrayList<>();
            while (true) // rollup attributes input while loop
            {

                System.out.print("select one of these dim foreign key attribute: (");
                for (String name : dvfa_parse_object.getFactTableFks())
                    System.out.print(name + ", ");
                System.out.println(") for rollup(group by)");
                String attribute = sc.next();
                fact_table_groupby_attribute_list.add(attribute);
                //removing selected fk so that it won't get printed again in while loop....
                dvfa_parse_object.getFactTableFks().remove(attribute);

                System.out.print("select one of the dimension table from: (");
                for (String name : dvfa_parse_object.getDimensionTableNames())
                    System.out.print(name + ", ");
                System.out.println(") to join with fact table");
                String dim_name = sc.next();
                dvfa_parse_object.getDimensionTableNames().remove(dim_name);

                String wrapper = dvfa_parse_object.getDimensionTableInnermostWrapper(dim_name);
                dim_table_innermost_wrapper_list.add(wrapper);

                String dim_table_pk = dvfa_parse_object.getDimensionTablePk(dim_name);
                dim_table_pk_list.add(dim_table_pk);

                System.out.println("select one of the below attribute of " + dim_name + " dimension table to do rollup");
                for (String name : dvfa_parse_object.getDimensionTableAttributes(dim_name))
                    System.out.print(name + ", ");
                System.out.println("");
                String attribute_name = sc.next();
                dim_attribute_list.add(attribute_name);

                dim_name = dim_name.concat(".xml"); //adding .xml extension
                dim_name_list.add(dim_name);

//                System.out.println("if attribute " + attribute_name + "'s domain is numeric select operator from ( =, <, >, !=, <=, >= ) ");
//                System.out.println("if attribute " + attribute_name + "'s domain is string select operator from ( =, != ) ");
//                String operator = sc.next();
//                operator_list.add(operator);
//
//                System.out.println("Enter the value for " + attribute_name + ": ");
//                String attr_value = sc.nextLine();
//                attr_value = sc.nextLine();
//                value_list.add(attr_value);

                if(dvfa_parse_object.getFactTableFks().size()==0)//as we have selected all attributes for dice
                    break;
                System.out.println("you want to input more dimension to rollup  (yes or no)");
                if (sc.next().equals("no"))
                    break; //breaking out of infinite while loop
            }// end of rollup attributes input while loop

            //now storing all the attributes on which we want to apply aggregation on
            while(true)
            {
                System.out.print("select one of these attributes(");
                for (String name : all_attributes)
                    System.out.print(name + ", ");
                System.out.println(") to apply aggregation functions");
                String attribute_name= sc.next();
                all_attributes.remove(attribute_name); //removing already selected attribute/column
                fact_table_aggr_attr_list.add(attribute_name);
                if (all_attributes.size() == 0)//as we have selected all attributes for dice
                    break;
                System.out.println("you want to input more attributes for aggregation (yes or no)");
                if (sc.next().equals("no"))
                    break; //breaking out of infinite while loop
            }
            /*rollupOnFactAttributesByJoiningDim(dw_name, fact_table_name, fact_table_innermost_wrapper, dim_name_list,
                    dim_table_innermost_wrapper_list, fact_table_groupby_attribute_list,  dim_table_pk_list,
                    dim_attribute_list, operator_list, value_list, fact_table_aggr_attr_list);
            */

            rollupOnDimAttributesByJoiningDim(dw_name, fact_table_name, fact_table_innermost_wrapper, dim_name_list,
                    dim_table_innermost_wrapper_list, fact_table_groupby_attribute_list,  dim_table_pk_list,
                    dim_attribute_list, operator_list, value_list, fact_table_aggr_attr_list);


        } // end of else if condition rollup- groupBy by joining dimension table

    }// end of rollup_take_input() method


    //group by fact table fks and attributes of dimension list are used as filters in where condition
    //populate operator_list and value_list in else if condition of rollup_take_input for conditional group by result
    //uncomment the last 3 lines in where condition in rollupOnFactAttributesByJoiningDim
    // NOT USED FUNCTION
    public static void rollupOnFactAttributesByJoiningDim(String dw_name, String fact_table_name, String fact_table_innermost_wrapper,
                                         ArrayList<String> dim_name_list,
                                         ArrayList<String> dim_table_innermost_wrapper_list,
                                         ArrayList<String> fact_table_groupby_attribute_list,
                                         ArrayList<String> dim_table_pk_list, ArrayList<String> dim_attribute_list,
                                         ArrayList<String> operator_list, ArrayList<String> value_list,
                                         ArrayList<String> fact_table_aggr_attr_list)
    {

        String xquery = "for $fact in doc(\""+ fact_table_name+ "\")//"+fact_table_innermost_wrapper;
        for(int i=0;i<dim_name_list.size();i++)
        {
            xquery= xquery.concat(", $dim"+ i+ " in doc(\""+dim_name_list.get(i)+ "\")//" + dim_table_innermost_wrapper_list.get(i));
        }
        xquery= xquery.concat(" where ");
        //for loop to add where condition before applying aggregation
        for(int i=0;i<dim_attribute_list.size();i++)
        {
            xquery= xquery.concat("$fact/"+fact_table_groupby_attribute_list.get(i)+ "=");
            xquery= xquery.concat("$dim"+i +"/"+ dim_table_pk_list.get(i));
            xquery= xquery.concat(" and ");

            //commenting where condition to get all possible groupBy results on dim columns
            // i.e removing conditional roll up -- uncomment to perform conditional rollup
            //xquery= xquery.concat("$dim"+i +"/"+ dim_attribute_list.get(i)+ operator_list.get(i));
            //xquery= xquery.concat("\""+ value_list.get(i)+ "\"");
            //xquery= xquery.concat(" and ");
        }
        xquery= xquery.substring(0, xquery.length()-5); //removing last unnecessary and

        xquery= xquery.concat(" group by ");
        String print_out="";
        for(int i=0;i<fact_table_groupby_attribute_list.size();i++)
        {
            String cur_attribute= fact_table_groupby_attribute_list.get(i);
            xquery = xquery.concat("$fact_attr" + i + ":=" + "$fact/" + cur_attribute + ", ");

            print_out=print_out.concat("\""+cur_attribute+"=\","+"$fact_attr"+ i+ ", "+ "\"&#x9;\""+", ");
        }
        xquery=xquery.substring(0, xquery.length()-2); //removing last unnecessary comma
        print_out= print_out.substring(0, print_out.length()-2); //removing last unnecessary comma
        xquery= xquery.concat(" return concat(\"&#xA;\", "+ print_out );
        //concat attributes on which aggregation has to be done
        for(int i=0;i<fact_table_aggr_attr_list.size();i++)
        {
            String agg_attribute = fact_table_aggr_attr_list.get(i);
            xquery = xquery.concat(", \"total_"+agg_attribute+"=\", sum($fact/"+agg_attribute+"), \"&#x9;\"");
        }
        xquery= xquery.concat(", \"&#xA;\")");

        System.out.println(xquery);
        FireQuery.firingQueryParam(xquery, dw_name, fact_table_name);
    }  //end of rollupByJoiningDim method



    //groupBy purely on dim table attributes
    //populate operator_list and value_list in else if condition in rollup_take_input for conditional group by result
    //uncomment the last 3 lines in where condition in rollupOnDimAttributesByJoiningDim
    // example group by customer_name Bill Gates and store_name X-Mart
    public static void rollupOnDimAttributesByJoiningDim(String dw_name, String fact_table_name, String fact_table_innermost_wrapper,
                                                         ArrayList<String> dim_name_list,
                                                         ArrayList<String> dim_table_innermost_wrapper_list,
                                                         ArrayList<String> fact_table_groupby_attribute_list,
                                                         ArrayList<String> dim_table_pk_list, ArrayList<String> dim_attribute_list,
                                                         ArrayList<String> operator_list, ArrayList<String> value_list,
                                                         ArrayList<String> fact_table_aggr_attr_list)

    {

        String xquery = "for $fact in doc(\""+ fact_table_name+ "\")//"+fact_table_innermost_wrapper;
        for(int i=0;i<dim_name_list.size();i++)
        {
            xquery= xquery.concat(", $dim"+ i+ " in doc(\""+dim_name_list.get(i)+ "\")//" + dim_table_innermost_wrapper_list.get(i));
        }

        xquery= xquery.concat(" where ");
        //for loop to add where condition before applying aggregation
        for(int i=0;i<dim_attribute_list.size();i++)
        {
            String dim_attribute= dim_attribute_list.get(i);
            xquery= xquery.concat("$fact/"+fact_table_groupby_attribute_list.get(i)+ "=");
            xquery= xquery.concat("$dim"+i +"/"+ dim_table_pk_list.get(i));
            xquery= xquery.concat(" and ");

            //commenting where condition to get all possible groupBy results on dim columns
            // i.e removing conditional roll up -- uncomment to perform conditional rollup
            //xquery= xquery.concat("$dim"+i +"/"+ dim_attribute_list.get(i)+ operator_list.get(i));
            //xquery= xquery.concat("\""+ value_list.get(i)+ "\"");
            //xquery= xquery.concat(" and ");

        }
        xquery= xquery.substring(0, xquery.length()-5); //removing last unnecessary and

        xquery= xquery.concat(" group by ");
        String print_out="";
        for(int i=0;i<fact_table_groupby_attribute_list.size();i++)
        {
            String cur_attribute= dim_attribute_list.get(i);
            xquery = xquery.concat("$fact_attr" + i + ":=" + "$dim"+i+"/" + cur_attribute + ", ");

            print_out=print_out.concat("\""+cur_attribute+"=\","+"$fact_attr"+ i+ ", "+ "\"&#x9;\""+", ");
        }
        xquery=xquery.substring(0, xquery.length()-2); //removing last unnecessary comma
        print_out= print_out.substring(0, print_out.length()-2); //removing last unnecessary comma
        xquery= xquery.concat(" return concat(\"&#xA;\", "+ print_out );
        //concat attributes on which aggregation has to be done
        for(int i=0;i<fact_table_aggr_attr_list.size();i++)
        {
            String agg_attribute = fact_table_aggr_attr_list.get(i);
            xquery = xquery.concat(", \"total_"+agg_attribute+"=\", sum($fact/"+agg_attribute+"), \"&#x9;\"");
        }
        xquery= xquery.concat(", \"&#xA;\")");

        System.out.println(xquery);
        FireQuery.firingQueryParam(xquery, dw_name, fact_table_name);
    }  //end of rollupByJoiningDimOnDimAttributes method


    public static void rollupAloneOnFact(String dw_name, String fact_table_name, String fact_table_innermost_wrapper,
                                         ArrayList<String> fact_table_groupby_attribute_list,
                                         ArrayList<String> fact_table_aggr_attr_list)
    {

        String xquery = "for $fact in doc(\""+ fact_table_name+ "\")//"+fact_table_innermost_wrapper+" group by ";
        String print_out="";
        for(int i=0;i<fact_table_groupby_attribute_list.size();i++)
        {
            String cur_attribute= fact_table_groupby_attribute_list.get(i);
            xquery = xquery.concat("$fact_attr" + i + ":=" + "$fact/" + cur_attribute + ", ");
            print_out=print_out.concat("\""+cur_attribute+"=\","+"$fact_attr"+i+", "+"\"&#x9;\""+", ");
        }
        xquery=xquery.substring(0, xquery.length()-2); //removing last unnecessary comma
        print_out= print_out.substring(0, print_out.length()-2); //removing last unnecessary comma
        xquery= xquery.concat(" return concat(\"&#xA;\", "+ print_out );
        //concat attributes on which aggregation has to be done
        for(int i=0;i<fact_table_aggr_attr_list.size();i++)
        {
            String agg_attribute = fact_table_aggr_attr_list.get(i);
            xquery = xquery.concat(", \"total_"+agg_attribute+"=\", sum($fact/"+agg_attribute+"), \"&#x9;\"");
        }
        xquery= xquery.concat(", \"&#xA;\")");

        System.out.println(xquery);
        FireQuery.firingQueryParam(xquery, dw_name, fact_table_name);
    }  //end of rollupAloneonFact method
}// end of RollUpParam class
