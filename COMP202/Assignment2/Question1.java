import java.util.*;

public class Question1
{
	public static void main(String[] args)
	{
	// Declarations + User input for the Binary Converter
		Scanner UserInput = new Scanner(System.in);
		System.out.println("Welcome to the Binary Converter!");
		
	// Here we ask for User input for the first time; We expect them to correctly input a Binary (hopefully haha)
		System.out.println("Input the Binary you wish to convert to base 10");
		String BinaryInput = UserInput.nextLine();
			
	// If the input is invalid, we want our program to continue and not end; Therefore, we restart the input procedure
		CheckInputCorrect(BinaryInput);
		
		while(CheckInputCorrect(BinaryInput) == false)
		{
			System.out.println("You did not enter a binary number, try again!!!");
			BinaryInput = UserInput.nextLine();
		}
		
		/* Now we execute our method/function that converts the input into a decimal output
		 * We create a new variable, BinaryOutput, which will equal to whatever the function BinaryToNumber has created
		 * and computed at an integer.
		 */
		int BinaryOutput = BinaryToNumber(BinaryInput);
		System.out.print("Your input was in fact a binary number and its decimal value is "+BinaryOutput+".");
	}
	
	// We want to check if the input is correct and has only 0s and 1 inside of it.
	public static boolean CheckInputCorrect(String input)
	{
		// We create a new boolean to have return that boolean to the CheckInputCorrect boolean.
		boolean Verified = true;
		
		// Then we proceed to check if any character of the input has either a 0 or a 1
		for (int i = 0; i < input.length(); i++)
		{
			if(input.charAt(i) =='1' || input.charAt(i) == '0')
			{
				continue;
			}
			else
			{
				Verified = false;
			}
		}
		
		// We then return the value of Verified
		return Verified;
	}
	
	// Now we want to convert our binary input into a decimal output
	public static int BinaryToNumber(String numberInput)
	{
		double number1 = 0;
		for(int i = 0; i < numberInput.length(); i++)
		{
			/* We should now go to every character and check if it contains a 1; given it does, we have to make it's position
			 * be the power of 2 and sum every one of them.	
			 */
			if (numberInput.charAt(i) == '1')
			{
				number1 = number1 + Math.pow(2,numberInput.length()-1-i);
			}
			/* We do -1 and - i because binary numbers' powers are given from right to left; We also use a double
			 * Since Math.pow imported function uses only doubles which we return back as an integer.
			 */ 
		}
		
		return (int) number1; 
	}
}