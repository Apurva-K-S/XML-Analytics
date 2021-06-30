import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ValidateXml
{
    //validating xml using xmllint
    public static void validateXml()
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the absolute path of the xml schema file including file extension (.xsd file)");
        String schema_file_path = sc.next();
        if(!schema_file_path.endsWith(".xsd")) //if schema file doesn't end with xsd
        {
            System.out.println("not a valid extension for schema file\nconventionally schema file should have .xsd extension\n");
            return;
        }
        //checking whether schema and instance files exists or not
        Path path = Paths.get(schema_file_path); //converting into Path type to use it in exists method
        if(!Files.exists(path)) //if the file doesn't exist
        {
            System.out.println("^^^^____specified schema file doesn't exist at given absolute path\n" +
                    "Please provide the correct absolute path where the schema file is stored----^^^^\n");
            return;
        }

        /*
        checks:
        1. valid extension
        2. wrong path.
        */
        System.out.println("Enter the absolute path of the xml instance file including file extension (.xml file)");
        String instance_doc_path = sc.next();
        if(!instance_doc_path.endsWith(".xml")) //if schema file doesn't end with xsd
        {
            System.out.println("not a valid extension for xml instance file\nconventionally schema file should have .xml extension\n");
            return;
        }
        //checking whether schema and instance files exists or not
        path = Paths.get(instance_doc_path); //converting into Path type to use it in exists method
        if(!Files.exists(path)) //if the file doesn't exist
        {
            System.out.println("^^^^____specified instance file doesn't exist at given absolute path\n" +
                    "Please provide the correct absolute path where the instance file is stored----^^^^\n");
            return;
        }
        System.out.println("Validating xml instance document against xsd");
        String validation_query = "xmllint -noout -schema ";
        validation_query = validation_query.concat(schema_file_path);
        validation_query = validation_query.concat(" ");
        validation_query = validation_query.concat(instance_doc_path);
        //System.out.println(validation_query);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", validation_query);
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            //System.out.println("inside second try block");
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor(); //  non-zero return value from bash/terminal indicates error
            if (exitVal == 0)
                System.out.println("Hoilaaa !!!!!! Successfully validated xml instance against xsd!\n");
            else
                System.out.println("^^^^*****Danger Danger*****^^^^xml validation failed--xml instance document " +
                        "doesn't comply with schema file.\nplease verify xml schema and instance files\n");

            System.out.println(output); //unable to get exact error from bash/terminal
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }// end of validateXml method

/*
    We are opening bash from java and executing xmllint query with schema and instance paths. 
    Here we are explicitly asking user to enter paths for schema and instance files.

    the below function is used before storing dimensions and fact tables into new seperate folder. 
    so we have DVFA file where all the paths are specified and it may be the case that these instance 
    and schema files at different locations so we want to store them at one location and while storing them
    at a single location we first validate them and move them to our datawarehouse folder. For this purpose 
    we created function that takes in parameters.
*/

    public static boolean validateXmlParam(String schema_file_path, String instance_doc_path)
    {
        if(!schema_file_path.endsWith(".xsd")) //if schema file doesn't end with xsd
        {
            System.out.println("not a valid extension for schema file\nconventionally schema file should have .xsd extension\n");
            return false;
        }
        //checking whether schema and instance files exists or not
        Path path = Paths.get(schema_file_path); //converting into Path type to use it in exists method
        //System.out.println("printing path: "+path);
        if(!Files.exists(path)) //if the file doesn't exist
        {
            System.out.println("^^^^____specified schema file doesn't exist at given absolute path\n" +
                    "Please provide the correct absolute path where the schema file is stored----^^^^\n");
            return false;
        }
        if(!instance_doc_path.endsWith(".xml")) //if schema file doesn't end with xsd
        {
            System.out.println("not a valid extension for xml instance file\nconventionally schema file should have .xml extension\n");
            return false;
        }
        //checking whether schema and instance files exists or not
        path = Paths.get(instance_doc_path); //converting into Path type to use it in exists method
        if(!Files.exists(path)) //if the file doesn't exist
        {
            System.out.println("^^^^____specified instance file doesn't exist at given absolute path\n" +
                    "Please provide the correct absolute path where the instance file is stored----^^^^\n");
            return false;
        }
        System.out.println("Validating xml instance document against xsd");
        String validation_query = "xmllint -noout -schema ";
        validation_query = validation_query.concat(schema_file_path);
        validation_query = validation_query.concat(" ");
        validation_query = validation_query.concat(instance_doc_path);
        //System.out.println(validation_query);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", validation_query);
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            //System.out.println("inside second try block");
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor(); //  non-zero return value from bash/terminal indicates error
            if (exitVal == 0) {
                System.out.println("Hoilaaa !!!!!!  Successfully validated xml instance against xsd!\n");
                return true;
            }
            else {
                System.out.println("^^^^*****Danger Danger*****^^^^xml validation failed--xml instance document " +
                        "doesn't comply with schema file.\nplease verify xml schema and instance files\n");
                return false;
            }
            //System.out.println(output); //unable to get exact error from bash/terminal
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }// end of validateXmlParam method

}
