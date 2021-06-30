import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
public class StoreXmlDataWarehouse
{
    static Scanner sc= new Scanner(System.in);
    static ProcessBuilder processBuilder = new ProcessBuilder();

    public static void store()
    {
        //this method creates directory and passes control to next method to copy data warehouse files
        System.out.println("Enter the name of the data warehouse");
        String dw_name= sc.next();
        try 
        {
            //  /home/apurva/Desktop/DM/project_repo  -- location where i'm sharing all datawarehouse folders
            processBuilder.command("bash", "-c", "cd ; cd /home/apurva/Desktop/DM/project_repo ; mkdir "+dw_name);
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Successfully created folder for data warehouse with given name: "+dw_name);
                String dw_path= "/home/apurva/Desktop/DM/project_repo"+"/"+dw_name;
                storeFactDimUsingDvfa(dw_path);
            }
            else {
                System.out.println("There is some issue creating the folder for specified data warehouse name" +
                        "\nThe reason could be:\n1. Folder/data warehouse with similar name already exists" +
                        "\n2.Please use only alphabets, numbers and - for the data warehouse name\nexiting ....");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    } // end of store method

/*
    first we are asking user to enter folder name (datawarehouse folder).
    now we create a directory at location /home/apurva/Desktop/DM/project_repo with name as chosen by user above.
    in this location we will put all the files (dimensions and fact table files). 
    and we will take these dimensions and fact files from the locations specified in dvfa file and store thm in this
    newly created directory.
*/

    //storeFactDimUsingDvfa  -- parse dvfa file and store using that file
    public static void storeFactDimUsingDvfa(String dw_path)
    {
        try
        {
            processBuilder.command("bash", "-c", "cd ; cd " + dw_path);
            Process process = processBuilder.start();

            System.out.println("Enter the dvfa file name for current data warehouse(name must be like dvfa.xml)");
            String dvfa_name=sc.next();
            System.out.println("Enter the absolute path of the dvfa file to store it into data warehouse");
            String dvfa_source_location= sc.next();
            //copying dvfa file to data warehouse folder
            processBuilder.command("bash", "-c", "cd ; cp -u " + dvfa_source_location + " " + dw_path);
            processBuilder.start();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder loader = factory.newDocumentBuilder();

            System.out.println("before parsing the file");
            //sometimes before even copying the file, the program is trying to access
            //so sleep until the file is copied successfully
            //Path path = Paths.get(dw_path+ "/"+ dvfa_name); //converting into Path type to use it in exists method
            //while(Files.exists(path)); //if the file doesn't exist infinite loop won't break;

            Document document = loader.parse(dvfa_source_location); //loading
            DocumentTraversal trav = (DocumentTraversal) document;

            MyFilter filter = new MyFilter();

            NodeIterator it = trav.createNodeIterator(document.getDocumentElement(),
                    NodeFilter.SHOW_ELEMENT, filter, true);
            System.out.println("before entering outer for loop");
            //loop to copy fact, dim tables by traversing dvfa.xml and using schema, instance
            for (Node node = it.nextNode(); node != null; node = it.nextNode())
            {
                String name = node.getNodeName();
                String text = node.getTextContent().trim().replaceAll("\\s+", " ");
                String[] splited = text.split("\\s+");

//                System.out.println("file schema path: "+ splited[1]);
                processBuilder.command("bash", "-c", "cd ; cp -u " + splited[1] + " " + dw_path);
                processBuilder.start();
                processBuilder.command("bash", "-c", "cd ; cp -u " + splited[2] + " " + dw_path);
                processBuilder.start();

                boolean validated = ValidateXml.validateXmlParam(splited[1], splited[2]); 
                // here we are validating schema and instance files before storing them. this is the function in validateXML file.
                if(validated)
                    System.out.println("schema and instance of " + splited[0] + " are valid");
                else
                    System.out.println("schema and instance of " + splited[0] + " are not valid");

                System.out.println();
            } // end of outer for loop
        }
        catch (Exception e) {
            System.out.println("exception is: " + e.getMessage());
        }

    } //end of StoreFactDimUsingDvfa method

/*
    So here we are first asking for dvfa file. after getting the dvfa file, we are storing it in the directory and then 
    we parse this file, extract file paths from this file and get the schema and instance file locations, validate them, if they are valid 
    then store them to the folder where dvfa file is present.
*/

    static class MyFilter implements NodeFilter {

        @Override
        public short acceptNode(Node thisNode) {
            if (thisNode.getNodeType() == Node.ELEMENT_NODE) {

                Element e = (Element) thisNode;
                String nodeName = e.getNodeName();

                if ("dimension-xml-document".equals(nodeName) || "fact-xml-document".equals(nodeName)) {
                    return NodeFilter.FILTER_ACCEPT;
                }
            }

            return NodeFilter.FILTER_REJECT;
        }
    } //Node filter inner static class end


    public static void storeFactDim(String dw_path)  //storing from user inputs (not using dvfa)
    {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", "cd ; cd " + dw_path);

            Process process = processBuilder.start();
            String location;
            System.out.println("Enter the absolute location of the fact schema file");
            location = sc.next();
            processBuilder.command("bash", "-c", "cd ; cp -u " + location + " " + dw_path);
            processBuilder.start();

            System.out.println("Enter the absolute location of the fact xml instance file");
            location = sc.next();
            processBuilder.command("bash", "-c", "cd ; cp -u " + location + " " + dw_path);
            processBuilder.start();

            System.out.println("Enter the no of dimension tables in the data warehouse");
            int n = sc.nextInt();
            for (int i = 0; i < n; i++) {
                System.out.println("Enter the absolute location of the dimension xsd file");
                location = sc.next();
                processBuilder.command("bash", "-c", "cd ; cp -u " + location + " " + dw_path);
                processBuilder.start();
                System.out.println("Enter the absolute location of the dimension xml instance file");
                location = sc.next();
                processBuilder.command("bash", "-c", "cd ; cp -u " + location + " " + dw_path);
                processBuilder.start();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    } //storeFactDim method end



}
