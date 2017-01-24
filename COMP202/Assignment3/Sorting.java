	public class Sorting {
    
		public static int Compare (String s1, String s2)
		{	
			for (int i = 0; i < s1.length() && i < s2.length(); i++)
			{
				int s1LowerCase = s1.charAt(i);
				int s2LowerCase = s2.charAt(i);
				
				if(s1.charAt(i) < 97)
				{
					s1LowerCase  += 32; //We convert to LowerCase for s1 every UpperCase char. We add the ASCII value
										//of 32.
				}
				if(s2.charAt(i) < 97)
				{
					s2LowerCase  += 32; //Ibid
				}
				
				if (s1LowerCase > s2LowerCase) //Return 1 if s1 > s2
				{
					return 1;
				}
				else if (s1LowerCase  < s2LowerCase) //Return -1 if s1 < s2
				{
					return -1;
				}
				else if(s1.length() > s2.length()) //Return 1 if they are the same but s1 is longer
				{
					return 1;
				}
				else if(s1.length() < s2.length()) //Return -1 if they are the same but s2 is longer
				{
					return -1;
				}
				
			}
			return 0; //Return zero in all other cases (if they are identical)
		}

	//We look through every single item of the array to find the target String t.
    public static int linearSearch (String [] a, String t)
	{
		int index = -1;
		for(int i = 0; i<a.length; i++)
		{	
			if(a[i].equals(t))
			{
				index = i;
			}
			
		}
		return index;
	}
    
    //Here we implement the Binary search which reduces the gap everytime. Although, that requieres a sorted array
    public static int binarySearch(String a [], String t)
	{	
    	
    	//Basic variables declarations
		int min = 0;
		int max = a.length-1;
		int mid = 0;
		
		//This is a searching algorithm which reduces search time v.s. linary serach since it halves the number of
		//possibilities by 2 every time we try to find the value.
		while(max >= min)
		{
			mid = ((min + max)/2);
			
			if(a[mid].equals(t))
			{
				return mid;
			}	
			else if (Compare(a[mid], t) == -1)
			{
				min = mid +1;
			}
			else if (Compare(a[mid], t) == 1)
			{
				max = min-1;
			}
		}
		
		return -1;
	}
    
    //Here we implement the Bubble Sorting algorithm
    //We follow the given pseudo code to obtain...
    public static int bubbleSort(String[] input_array) {
    	
    	//Basic needed variables
        int n = input_array.length;
        int counter = 0;
        boolean swapped = true;
        
        //We've seen this time of sorting many times, but basically you check for every value from 0 to the length 
        //of the array and compare two items at the time and exchange their places. 
        while(swapped)
        {
        	swapped = false;

		    for(int i = 1; i < n; i++)
		    {
		    	if(Compare(input_array[i-1], input_array[i]) == 1)
		    	{
		    		String temp = input_array [i-1];
		    		input_array[i-1] = input_array[i];
		    		input_array[i] = temp;
		    		
		    		swapped = true;
		    	}
		    	counter++;
		    }
        }
        return counter;
    }
	
    //Here we implement the Comb Sort algorithm
    public static int combSort(String[] input_array) {
    	
    	//Basic needed variables
        int gap = input_array.length;
        int counter = 0;
        double shrink = 1.247330950103979; //From Wikipedia on CombSorting.
        boolean swapped = false;
        
        //This loop (while) makes the gap smaller 
        while (swapped || gap != 1)
        {
        gap = (int) (gap/shrink);
        //if the gap is smaller than 1, it will reset the value of gap and break the while look and make it restart
        	if (gap < 1)
        	{
        		gap = 1;
        	}
        	
        }
        
        //Once the gap is equal to 1, then we can proceed to sort every time with our regular sorting algorithm but taking
        //into account the gap this time
        int i = 0;
        swapped = false;
        
        while (i+gap < input_array.length)
        {
        	
        	if(Compare(input_array[i], input_array[i+gap]) == -1)
        	{
        		String temp = input_array[i+gap];
        		input_array[i+gap] = input_array[i];
        		input_array[i] = temp;
        		swapped = true;
        	}
        	//Counting the number of time we swap items
        	i++;
        	counter++;
        	
        }
        return counter;
    }
    public static void plotBubbleSortTest(int N_MAX) {
        /* 
         * bubble_sort_results[N] = the number of comparisons made
         * when sorting an array of size N.
         */
        int[] bubble_sort_results = new int[N_MAX];
      
        /*
         * For each array size between 1 (the smallest array size)
         * and N_MAX (an upper limit passed to this method), we will:
         * 1) create a random test array
         * 2) sort it, and store the number of comparisons in bubble_sort_results
         * MAKE SURE THAT YOUR METHOD IS ACTUALLY SORTING THE TEST ARRAY!!!!!!
         */
        for (int i = 1; i < N_MAX; i++) {
            String[] test_array = ArrayUtilities.getRandomNamesArray(i);
            bubble_sort_results[i] = bubbleSort(test_array);
        }
        
        // create a plot window
        // The argument passed to  PlotWindow is the title of the window
        PlotWindow pw = new PlotWindow("Bubble Sort!");
       
        // add a plot to the window using our results array
        /*
         *  The first argument for addPlot is a name for your data set
         *  The second argument for addPlot is an array of integers,
         *  In position "N" in the array, you should put the number of
         *  comparisons that your algorithm made, when sorting an array
         *  of size N. For example, bubble_sort_results[100] will contain
         *  the number of comparisons made for sorting an array of 100 elements
         */
        pw.addPlot("BubbleSort", bubble_sort_results);
    }
    
    public static void plotCombSortTest(int N_MAX) {
        // the results array
        int[] comb_sort_results = new int[N_MAX];
        
        // test sorting for arrays from size 1 to N_MAX
        // MAKE SURE THAT YOUR METHOD IS ACTUALLY SORTING THE TEST ARRAY!!!!!!
        for (int i = 1; i < N_MAX; i++) {
            String[] test_array = ArrayUtilities.getRandomNamesArray(i);
            comb_sort_results[i] = combSort(test_array);
        }
        // create a plot window
        PlotWindow pw = new PlotWindow("Comb Sort!");
        // add a plot to the window using our results array
        pw.addPlot("CombSort", comb_sort_results);
    }   
    
    public static void plotCombBubble(int N_MAX)
    {
    	int[] comb_sort_results = new int[N_MAX];
    	int[] bubble_sort_results = new int[N_MAX];
    	for (int i = 1; i < N_MAX; i++) 
    	{
            String[] test_array = ArrayUtilities.getRandomNamesArray(i);
            bubble_sort_results[i] = bubbleSort(test_array);
            comb_sort_results[i] = combSort(test_array);
        }
    	
    	PlotWindow pw = new PlotWindow("Comb/Bubble Sort Comparison!");
    	
    	pw.addPlot("BubbleSort", bubble_sort_results);
    	pw.addPlot("CombSort", comb_sort_results);
    }

    public static void main(String[] args) {
		//String [] a = {"Vladimir", "Ismail", "Priya", "Jiequn", "Melanie", "Camilo", "Jonathan"};
		//String t ="Melanie";
		//System.out.println(linearSearch(a, t));
    	
    	
        //plotBubbleSortTest(100);
        //plotCombSortTest(100);
        plotCombBubble(100);
    }
}
