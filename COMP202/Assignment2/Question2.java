public class Question2
{
	public static void main(String[] args) 
	{
		// Basic declarations needed
		System.out.println("Welcome to the Carthesian Graphic Calculator");
		
		// We create a general for loop to check for every value from 0 to 20 of our grid
		for(int j = 0;  j <= 20; j++)
		{
			for (int i = 0; i <= 20; i++)
			{
				// Here, the conditions are meant to eliminate any overlapping or double printing of methods and to keep
				// the axis on top, then the line then the Parabola. It also includes our general double for loop which
				// will draw every charcter 
				if(DrawAxis(i,j) != "")
				{
					System.out.print(DrawAxis(i,j));
				}
				else if(DrawLine(i,j,1,2) !="")
				{
					System.out.print(DrawLine(i,j,1,2));
				}
				else if(DrawParabola(i,j,0.3,1,-4) !="")
				{
					System.out.print(DrawParabola(i,j,0.3,1,-4));
				}
				else
				{
					System.out.print(" ");
				}
				
			}
			System.out.println();
		}
	}

	public static String DrawAxis(int i, int j)
	{		
		// We draw our Axis with the given order at the given coordinates
				if (j == 10 && i == 10)
		        {
		          System.out.print(".");
		        }
				else if (i == 20 && j == 10)
		        {
		        	System.out.print(">");
		        }
				else if (j == 0 && i == 10)
		        {
		        	System.out.print("^");
		        }
		        else if (i == 10)
		        {
		        	System.out.print("|");
		        }
		        else if (j == 10)
		        {
		        	System.out.print("-");
		        }
		        else
		        {
		          System.out.print(" ");
		        }
		
		
		return ""; 
	}

	public static String DrawLine(int i, int j, double a, double b)
	{
		// Our system is based of a quadrant of (0,0) to (20,20). Since our function goes from (-10,-10) to (+10,+10) we will have to
		// Rescale our grid to make the equation show completely.
		int centeredXaxis = 20/2;
		int centeredYaxis = 20/2;
		
		// We also want every i and j to be centered 
		int icentered = i-centeredXaxis;
		int jcentered = j-centeredYaxis;
		
		// Now let's draw our linear equation having to draw an * for given coordinates that respect y = x + 2 and nothing for
		// everything else
			if((jcentered <= -a*icentered - b && jcentered >= -a*icentered - b)) 
		// We don't want anything to be shown on the axis themselves
			{
				return "*"; // We don't print directly, we simply return it into the DrawLine String which will be printed in the main
			}
			else
			{
				System.out.print("");
			}
		return ""; 

	}
	public static String DrawParabola(int i, int j, double a, double b, double c)
	{
		// Our system is based of a quadrant of (0,0) to (20,20). Since our function goes from (-10,-10) to (+10,+10) we will have to
				// Rescale our grid to make the equation show completely.
				int centeredXaxis = 20/2;
				int centeredYaxis = 20/2;
				
				// We also want every i and j to be centered 
				int icentered = i-centeredXaxis;
				int jcentered = j-centeredYaxis;
				
				// Now let's draw our linear equation having to draw an * for given coordinates that respect y = x + 2 and nothing for
				// everything else
					if((jcentered -2 <= -a*icentered*icentered - b*icentered -c && jcentered +2 >= -a*icentered*icentered - b*icentered -c )&& jcentered != 10) 
				// We don't want anything to be shown on the axis themselves
					{
						return "#"; // We don't print directly, we simply return it into the DrawLine String which will be printed in the main
					}
					else
					{
						System.out.print("");
					}
				return "";  

	}
}
