import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class ArrayUtilities {
    /*
     * This will load the names file into the names array
     */
    public static String[] names = loadNamesArray();

    /*
     * This method reads the first-names.txt file and load the list of names
     */
    public static String[] loadNamesArray(){
        Scanner dataScanner;
        String filepath = "first-names.txt";
        ArrayList<String> arraylist = new ArrayList<String>();

        try {
            dataScanner = new Scanner(new File(filepath));
            while(dataScanner.hasNextLine()){
                arraylist.add(dataScanner.nextLine());
            }
            dataScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("The file "+filepath+" was not found.");
            e.printStackTrace();
        }

        String[] array = new String[arraylist.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = arraylist.get(i);
        }

        return array;
    }

    /*
     * This method will return an array with n sorted names
     */
    public static String[] getSortedNamesArray(int n){
        String[] array = new String[n];
        int step = Math.max(names.length/(n+1),1);
        int index = 0;
        for (int i = 0; i < n; i++) {
            index+=step;
            array[i] = names[index];
        }
        return array;
    }

    /*
     * This method will return an array with n names sorted in 
     * reverse alphabetical order
     */
    public static String[] getReverseSortedNamesArray(int n){
        String[] array = new String[n];
        int step = Math.max(names.length/(n+1),1);
        int index = names.length-step;
        for (int i = 0; i < n; i++) {
            index-=step;
            array[i] = names[index];
        }
        return array;
    }

    /*
     * This method will return an array with n names in random order
     */
    public static String[] getRandomNamesArray(int n){
        int MAX = names.length;
        String[] array = new String[n];
        Random rng;
        rng = new Random();

        for (int i = 0; i < n; i++) {
            array[i] = names[rng.nextInt(MAX)];
        }
        return array;
    }

    /*
     * This method will return an array with n names in "random" order.
     * Passing the same value for seed, will result in the same "random"
     * array being returned
     */
    public static String[] getRandomNamesArray(int n, int seed){
        int MAX = names.length;
        String[] array = new String[n];
        Random rng;
        rng = new Random();

        for (int i = 0; i < n; i++) {
            array[i] = names[rng.nextInt(MAX)];
        }
        return array;
    }

    /*
     * This method will print all the elements of an array
     */
    public static void printArray(String[] a){
        System.out.print("{ ");
        for (int i = 0; i < a.length-1; i++) {
            System.out.print("\""+a[i]+"\", ");
        }
        System.out.print("\""+a[a.length-1]+"\" }");
        System.out.println();
    }

    /*
     * This method will return true is the array a is sorted
     */
    public static boolean checkIsSorted(String[] a){
    	for (int i = 1; i < a.length; i++)
    	{
    		if (a[i].compareTo(a[i-1]) > 0)
    		{
    			return false;
    		}
    	}
    	return true;
    }

    public static void main(String[] args) {
        printArray(loadNamesArray());
    }
}
