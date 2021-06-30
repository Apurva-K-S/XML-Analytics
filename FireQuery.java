import java.util.*;
class FireQuery
{
	public static void main(String[] args)
	{
		System.out.println("Enter the dimension on which you want to perform slice operation");
		Scanner sc=new Scanner(System.in);
		String dim= sc.next();
		System.out.println("Enter the attribute/col for slice operation from(store_id, customer_id, product_id");
		String attribute= sc.next();
		System.out.println("which operation you want to perform from(<, >, ==, <=, >=, != )");
		String operation= sc.next();
		System.out.println("Enter the value for the attribute to perform slice operation");
		int val= sc.nextInt();
		
		String xquery="let $fact_product_entries:= doc(\"factProductSales.xml\")//ProductSale for $x in $fact_product_entries where $x/";
		xquery=xquery.concat(attribute);
		xquery=xquery.concat(operation);
		xquery=xquery.concat(Integer.toString(val));
		xquery=xquery.concat(" return $x/transaction_id");
		System.out.println(xquery);
		
		
	}

}

/*

let $fact_product_entries:= doc("factProductSales.xml")//ProductSale
for $x in $fact_product_entries
where $x/customer_id>1
return $x/transaction_id

*/
