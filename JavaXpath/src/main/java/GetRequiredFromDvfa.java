import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;

//purpose of this class is to -- given data warehouse name in xml-project to a method, load and parse the
//dvfa file in that method, and have print methods which will return required values
public class GetRequiredFromDvfa {

    
    //dimension table buckets for populating
    ArrayList<String> dimension_table_names = new ArrayList<String>();//to store dimension names
    HashMap<String, ArrayList<String>> all_attributes_by_dimension= new HashMap<String, ArrayList<String>>();//dimension name as key and all attributes as arraylist
    HashMap<String, String> all_dim_innermost_wrapper = new HashMap<String, String>(); //dimension name, inner most wrapper
    HashMap<String, String> all_dim_pks= new HashMap<String, String>(); // dimension name, primary key of that dimension

    // fact table buckets for populating
    String fact_table_name="", fact_innermost_wrapper="", fact_pk="";
    ArrayList<String> all_attributes_fact_table = new ArrayList<String>();
    ArrayList<String> fact_table_fks = new ArrayList<String>();

    //once this function is called with dw_name it will do

    //method to load and parse dvfa and populate all the lists to retrieve pks, fks, attributes, wrappers
    public void loadParseDvfa(String dw_name)
    {
        try
        {
            //while storing -- all data warehouses are stored inside the xml-project folder
            
            String dw_path= "/home/apurva/Desktop/DM/project_repo"+"/"+dw_name;
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder loader = factory.newDocumentBuilder();

            //System.out.println("before parsing the file");
            //sometimes before even copying the file, the program is trying to access
            //so sleep until the file is copied successfully

            Document document = loader.parse(dw_path+"/dvfa.xml"); //loading
            DocumentTraversal trav = (DocumentTraversal) document;

            //ParserDvfa.MyFilter filter = new ParserDvfa.MyFilter();
            MyFilter filter= new MyFilter();
            NodeIterator it = trav.createNodeIterator(document.getDocumentElement(),
                    NodeFilter.SHOW_ELEMENT, filter, true);
            //System.out.println("before entering outer for loop");

            for (Node node = it.nextNode(); node != null; node = it.nextNode())
            {
                String node_name = node.getNodeName(); //name of node (fact-xml-document or dimension-xml-document) check filter class
                //System.out.println("node name is: " + node_name);

                if(node_name.equals("fact-xml-document")) //populating fact table arraylist and maps
                {
                    
                    NodeList first_level_children= node.getChildNodes();
                    for (int count = 0; count < first_level_children.getLength(); count++)
                    {
                        Node dummy_node = first_level_children.item(count);
                        //System.out.println("count is: "+ count + "and node name: "+ dummy_node.getNodeName());
                        if(dummy_node.getNodeName().equals("name")) //populating name of the fact table
                            fact_table_name= dummy_node.getTextContent();
                        else if(dummy_node.getNodeName().equals("innermost-wrapper")) //populating fact inner most wrapper of fact table
                            fact_innermost_wrapper= dummy_node.getTextContent();
                        else if(dummy_node.getNodeName().equals("fields"))
                        {
                            //inside field object
                            NodeList fields_children= dummy_node.getChildNodes();
                            //loop over fields_children list which contains pk fk and other fields
                            for(int fc=0; fc<fields_children.getLength(); fc++)
                            {
                                Node field_node= fields_children.item(fc);
                                if(fc%2==1) // at 0,2,4... indices there were some text fields
                                    all_attributes_fact_table.add(field_node.getTextContent());
                                if(field_node.getNodeName().equals("primary-key")) //populating pk from fact table
                                    fact_pk= field_node.getTextContent();
                                else if(field_node.getNodeName().equals("foreign-key")) //populating fk from fact table
                                    fact_table_fks.add(field_node.getTextContent());
                            }
                        } //fields else if end

                    } //first_level_children for loop end
                } // end of fact-xml-document else if -- populating fact table arrayList and maps
                else if(node_name.equals("dimension-xml-document")) //start of populating dimension table arrayList and maps
                {
                    
                    NodeList first_level_children= node.getChildNodes();
                    String cur_dim_name= "";
                    for (int count = 0; count < first_level_children.getLength(); count++)
                    {
                        Node dummy_node = first_level_children.item(count);
                        
                        if(dummy_node.getNodeName().equals("name")) // name of the dimension table
                        {
                            cur_dim_name= dummy_node.getTextContent();
                            dimension_table_names.add(cur_dim_name);
                        }
                        else if(dummy_node.getNodeName().equals("innermost-wrapper")) //populating fact inner most wrapper of fact table
                        {
                            //String cur_dim_name= dimension_table_names.get(dimension_table_names.size()-1);
                            all_dim_innermost_wrapper.put(cur_dim_name, dummy_node.getTextContent());
                        }
                        else if(dummy_node.getNodeName().equals("fields"))
                        {
                            //inside field object
                            NodeList fields_children= dummy_node.getChildNodes();
                            //loop over fields_children list which contains pk fk and other fields
                            ArrayList<String> cur_dim_attributes= new ArrayList<String>();
                            for(int fc=0; fc<fields_children.getLength(); fc++)
                            {
                                Node field_node= fields_children.item(fc);
                                if(fc%2==1) // at 0,2,4... indices there were some text fields
                                {
                                    //adding each attribute of current dimension the cur_dim_attributes list
                                    cur_dim_attributes.add(field_node.getTextContent());
                                }
                                if(field_node.getNodeName().equals("primary-key")) //populating pk from fact table
                                {
                                    //String cur_dim_name= dimension_table_names.get(dimension_table_names.size()-1);
                                    all_dim_pks.put(cur_dim_name, field_node.getTextContent());
                                }
                                else if(field_node.getNodeName().equals("foreign-key")) //populating fk from fact table
                                {
                                    //no statements -- we assume dims don't have foreign keys
                                    System.out.println("");
                                }
                            } //inner for loop
                            //finally adding all attribute list to map
                            //String cur_dim_name= dimension_table_names.get(dimension_table_names.size()-1);
                            all_attributes_by_dimension.put(cur_dim_name, cur_dim_attributes);
                        } //fields else if condition end
                    } //first_level_children for loop end
                } // end of dimension-xml-document else if -- populating dimension table arrayList and maps
            } // end of outer for loop

            //printPopulatedFields();
        }//try end
        catch (Exception e) {
            System.out.println("exception is: " + e.getMessage());
        }

    } //end of StoreFactDimUsingDvfa method

    static class MyFilter implements NodeFilter
    {
        @Override
        public short acceptNode(Node thisNode)
        {
            if(thisNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element e = (Element) thisNode;
                String nodeName = e.getNodeName();
                if ("dimension-xml-document".equals(nodeName) || "fact-xml-document".equals(nodeName)) {
                    return NodeFilter.FILTER_ACCEPT;
                }
            }
            return NodeFilter.FILTER_REJECT;
        }
    } //Node filter inner static class end


    public void printPopulatedFields()
    {
        //printing fact table populated fields for fun
        System.out.println("printing fact table populated fields for fun");
        System.out.println("fact table name: "+ fact_table_name);
        System.out.println("fact innermost wrapper name: "+ fact_innermost_wrapper);
        System.out.println("fact primary key name: " + fact_pk);    // primary key not printing check once
        System.out.println("below are foreign keys of fact table");
        for(String cur: fact_table_fks)
            System.out.println(cur);
        System.out.println("below are all the attributes fact table");
        for(String cur: all_attributes_fact_table)
            System.out.println(cur);
        System.out.println("end of printing fact table populated fields for fun");

        //printing dim table populated fields for fun
        System.out.println("printing dim table populated fields for fun");
        System.out.println("below are dim table names");
        for(String dim_name: dimension_table_names)
            System.out.println(dim_name);
        System.out.println("below are dim table primary keys");
        for(Map.Entry<String, String> key_value: all_dim_pks.entrySet())
        {
            System.out.println("dim name is: " + key_value.getKey() + " and pk is: " + key_value.getValue());
        }
        System.out.println("below are dim table inner most wrapper names");
        for(Map.Entry<String, String> key_value: all_dim_innermost_wrapper.entrySet())
        {
            System.out.println("dim name is: " + key_value.getKey() + " and wrapper is: " + key_value.getValue());
        }
        System.out.println("below are all attributes of each dimension");
        for(Map.Entry<String, ArrayList<String>> key_value: all_attributes_by_dimension.entrySet())
        {
            System.out.println("below are the attributes of the dim: "+ key_value.getKey());
            for(String attr: key_value.getValue())
                System.out.println(attr);
        }
        System.out.println("end of printing dim table populated fields for fun");

    } // end of printPopulatedFields method

    //after calling loadParseDvfa(String dw_name) with stored dw_name, we can call getter methods

    //Getter methods fact populated fields
    public String getFactTableName()
    {
        return fact_table_name;
    }
    public String getFactInnermostWrapper()
    {
        return fact_innermost_wrapper;
    }
    public String getFactPk()
    {
        return fact_pk;
    }
    public ArrayList<String> getAllAttributesFactTable()
    {
        return all_attributes_fact_table;
    }
    public ArrayList<String> getFactTableFks()
    {
        return fact_table_fks;
    }

    //Getter methods for dimension populating fields
    public ArrayList<String> getDimensionTableNames()
    {
        return dimension_table_names;
    }
    public String getDimensionTablePk(String dim_name)
    {
        return all_dim_pks.get(dim_name);
    }
    public String getDimensionTableInnermostWrapper(String dim_name)
    {
        return all_dim_innermost_wrapper.get(dim_name);
    }
    public ArrayList<String> getDimensionTableAttributes(String dim_name)
    {
        return all_attributes_by_dimension.get(dim_name);
    }

    // end of getter methods

} // end of class GetRequiredFromDvfa
