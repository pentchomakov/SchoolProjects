import java.util.*;
/* We import the scanner function that has been coded by someone else */

public class Game1
{

	public static void main(String[] args)
	{
		/* Our Declarations */
		String numberLaws;
		Scanner scan = new Scanner(System.in);
		
		/* We ask the user to input his value of the number of laws of robotics*/
		System.out.println("Hal : How many laws of robotics are they according to Isaac Asimov?");
		numberLaws = scan.nextLine();
		
		/* We check if the input was correct and if it equals to 3, three or Three to cover all possibilities */
		if (numberLaws.equals("3") || numberLaws.equalsIgnoreCase("three")) 
		{
			System.out.println("Hal: You got it right. Three indeed.");
		}
		
		/* We create the situation where numberLaws != 3, three or Three which is every other String that exists and give out the right laws */
		else 
		{
			System.out.println("Hal : No, the answer is not "+numberLaws+".\n");
			System.out.println("Here are the 3 laws of robotics of Isaac Assimov :");
			System.out.println("1. A robot may not injure a human being or, through inaction, allow a human being to come to harm.");
			System.out.println("2. A robot must obey the orders given to it by human beings, except where such orders would conflict with the First Law.");
			System.out.println("3. A robot must protect its own existence as long as such protection does not conflict with the First or Second Law.\n");
			System.out.println("Source : http://en.wikipedia.org/wiki/Three_Laws_of_Robotics");
		}
	}
}

