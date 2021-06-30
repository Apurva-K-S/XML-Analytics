import java.util.ArrayList;
import java.util.Scanner;

public class DiceParam {

    //need to implement similar to SliceParam

    static Scanner sc= new Scanner(System.in);
    public static void dice_take_input()
    {
        System.out.println("Enter the data warehouse name on which you want to perform this operation (folder name is same as data warehouse name)");
        String dw_name= sc.next();
        System.out.println("Select one of the below:\nPress 1 for performing dice operation alone on fact table" +
                "\nPress 2 to dice by joining dimension table(you can choose dimension attributes as entry points)");
        int selected_option= sc.nextInt();
        if(selected_option==1) //dice alone on fact table
        {
            System.out.println("******** You chose to dice using fact table only *********");
            GetRequiredFromDvfa dvfa_parse_object = new GetRequiredFromDvfa();
            dvfa_parse_object.loadParseDvfa(dw_name);
            //we just get name of the table/document -- //appending .xml extension to fact_name string
            String fact_table_name = dvfa_parse_object.getFactTableName().concat(".xml");
            String fact_table_innermost_wrapper = dvfa_parse_object.getFactInnermostWrapper();
            ArrayList<String> attribute_list= new ArrayList<>();
            ArrayList<String> operator_list= new ArrayList<>();
            ArrayList<String> value_list= new ArrayList<>();

// dice operation is performed on multiple dimensions so here we are storing each one in an array list.

            while (true)
            {

                System.out.print("select one of these dim foreign key attribute: (");
                for (String name : dvfa_parse_object.getFactTableFks())
                    System.out.print(name + ", ");

                System.out.println(") for dicing");
                
                String attribute=sc.next();
                
                attribute_list.add(attribute);
                
                //removing selected fk so that it won't get printed again in while loop....
                dvfa_parse_object.getFactTableFks().remove(attribute);
                
                System.out.println("if attribute " + attribute + "'s domain is numeric select operator from ( =, <, >, !=, <=, >= ) ");
                System.out.println("if attribute " + attribute + "'s domain is string select operator from ( =, != ) ");
                
                operator_list.add(sc.next());
                
                System.out.println("Enter the value for " + attribute + " to perform dice: ");
                
                value_list.add(sc.next());

                if(dvfa_parse_object.getFactTableFks().size()==0)//as we have selected all attributes for dice
                    break;
                System.out.println("you want to input more dimension to dice operation to filter  (yes or no)");
                if (sc.next().equals("no"))
                    break; //breaking out of infinite while loop
            }// end of while loop
            diceAloneOnFact(dw_name, fact_table_name, fact_table_innermost_wrapper, attribute_list, operator_list, value_list);
        } // end of if condition dice alone on fact table
        else if(selected_option==2) //dice by joining dimension table
        {
            System.out.println("******** You chose to dice by joining dimension table with fact table ********");
            GetRequiredFromDvfa dvfa_parse_object= new GetRequiredFromDvfa();
            dvfa_parse_object.loadParseDvfa(dw_name);
            //we just get name of the table/document -- //appending .xml extension to fact_name string
            String fact_table_name = dvfa_parse_object.getFactTableName().concat(".xml");
            String fact_table_innermost_wrapper = dvfa_parse_object.getFactInnermostWrapper();
            //String fact_table_pk= GetRequiredFromDvfa.getFactPk();
            ArrayList<String> dim_name_list= new ArrayList<>();
            ArrayList<String> fact_table_fk_of_selected_dim_list= new ArrayList<>();
            ArrayList<String> dim_table_innermost_wrapper_list = new ArrayList<>();
            ArrayList<String> dim_table_pk_list= new ArrayList<>();
            ArrayList<String> attribute_name_list= new ArrayList<>();
            ArrayList<String> operator_list= new ArrayList<>();
            ArrayList<String> value_list= new ArrayList<>();
            while(true)
            {
                System.out.print("select one of the dimension table from: (");
                for (String name : dvfa_parse_object.getDimensionTableNames())
                    System.out.print(name + ", ");
                System.out.println(") to join with fact table");
                String dim_name = sc.next();
                dvfa_parse_object.getDimensionTableNames().remove(dim_name); // as this dimension is already selected

                System.out.print("select foreign key from: (");
                for (String name : dvfa_parse_object.getFactTableFks())
                    System.out.print(name + ", ");
                    
                System.out.println(") which corresponds to primary key of selected " + dim_name + " table");
                String fact_table_fk = sc.next();
                fact_table_fk_of_selected_dim_list.add(fact_table_fk);
                dvfa_parse_object.getFactTableFks().remove(fact_table_fk); //as this fk is already selected

                String wrapper = dvfa_parse_object.getDimensionTableInnermostWrapper(dim_name);
                dim_table_innermost_wrapper_list.add(wrapper);

                String dim_table_pk = dvfa_parse_object.getDimensionTablePk(dim_name);
                dim_table_pk_list.add(dim_table_pk);

                System.out.println("select one of the below attribute of " + dim_name + " dimension table to dice");
                for (String name : dvfa_parse_object.getDimensionTableAttributes(dim_name))
                    System.out.print(name + ", ");
                System.out.println("");
                String attribute_name = sc.next();
                attribute_name_list.add(attribute_name);

                dim_name = dim_name.concat(".xml"); //adding .xml extension
                dim_name_list.add(dim_name);

                System.out.println("if attribute " + attribute_name + "'s domain is numeric select operator from ( =, <, >, !=, <=, >= ) ");
                System.out.println("if attribute " + attribute_name + "'s domain is string select operator from ( =, != ) ");
                String operator = sc.next();
                operator_list.add(operator);

                System.out.println("Enter the value for " + attribute_name + ": ");
                String attr_value = sc.nextLine();
                attr_value = sc.nextLine();
                value_list.add(attr_value);

                if(dvfa_parse_object.getFactTableFks().size()==0)//as we have selected all attributes for dice
                    break;
                System.out.println("you want to input more dimension to dice operation to filter  (yes or no)");
                if (sc.next().equals("no"))
                    break; //breaking out of infinite while loop
            } // end of while true infinite loop
            diceByJoiningDim(dw_name, fact_table_name, fact_table_innermost_wrapper, dim_name_list,
                    dim_table_innermost_wrapper_list, fact_table_fk_of_selected_dim_list,
                    dim_table_pk_list, attribute_name_list, operator_list, value_list);
        }// end of else if condition by joining dimension tables
    } // end of dice_take_input method

    public static void diceByJoiningDim(String dw_name, String fact_table_name, String fact_table_innermost_wrapper,
                                        ArrayList<String> dim_name_list, ArrayList<String> dim_table_innermost_wrapper_list,
                                        ArrayList<String> fact_table_fk_of_selected_dim_list, ArrayList<String> dim_table_pk_list,
                                        ArrayList<String> dim_attribute_list, ArrayList<String> operator_list,
                                        ArrayList<String> value_list)
    {
        String xquery = "for $fact in doc(\""+ fact_table_name + "\")//"+ fact_table_innermost_wrapper;
        for(int i=0;i<dim_name_list.size();i++)
        {
            xquery= xquery.concat(", $dim"+ i+ " in doc(\""+dim_name_list.get(i)+ "\")//" + dim_table_innermost_wrapper_list.get(i));
        }
        xquery= xquery.concat(" where ");
        for(int i=0;i<dim_attribute_list.size();i++) //adding all attributes to dice operation
        {
            xquery= xquery.concat("$fact/"+ fact_table_fk_of_selected_dim_list.get(i)+ "=$dim"+ i+"/"+
                    dim_table_pk_list.get(i)+" and ");
            xquery= xquery.concat("$dim"+ i+"/"+ dim_attribute_list.get(i)+ operator_list.get(i)+
                    "\""+ value_list.get(i)+ "\"");
            xquery= xquery.concat(" and ");
        }
        //removing unnecessary and concatenated after last attributes value
        xquery=xquery.substring(0, xquery.length()-4);
        xquery = xquery.concat(" return $fact"); //to return entire ProductSale element
        System.out.println(xquery);
        FireQuery.firingQueryParam(xquery, dw_name, fact_table_name);

    } // end of diceByJoiningDim method


    public static void diceAloneOnFact(String dw_name, String fact_table_name, String fact_table_innermost_wrapper,
                                       ArrayList<String> attribute_list, ArrayList<String> operator_list,
                                       ArrayList<String> value_list)
    {
        String xquery = "for $fact in doc(\""+ fact_table_name + "\")//"+ fact_table_innermost_wrapper+ " where ";
        for(int i=0;i<attribute_list.size();i++) //adding all attributes to dice operation
        {
            xquery= xquery.concat("$fact/"+ attribute_list.get(i)+ operator_list.get(i)+ "\""+ value_list.get(i)+ "\"");
            xquery= xquery.concat(" and ");
        }
        //removing unnecessary and concatenated after last attributes value
        xquery=xquery.substring(0, xquery.length()-4);
        xquery = xquery.concat(" return $fact"); //to return entire ProductSale element
        System.out.println("\nYour query is: \n\n" + xquery + "\n");
        FireQuery.firingQueryParam(xquery, dw_name, fact_table_name);
    } //end of diceAloneOnFact method
}
