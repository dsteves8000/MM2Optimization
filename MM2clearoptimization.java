import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
public class MM2clearoptimization
{
   static File inputFile;
   public static void main(String [] args) throws FileNotFoundException
   {
      if(handleArgs(args) == true)
      {
         Scanner in = new Scanner(inputFile);
         double [] arr = inpArray(in);
         for(double c = 120; c >= 0.0; c -= 0.1)
         {
            clearanalysis(c, arr, true);
         }
      }
   }
   public static void clearanalysis(double cutoff, double [] times, boolean handheldmode)
   {
         double timespent = 0.0;
         int clears = 0;
         for(int i = 0; i < times.length; i++)
         {
            timespent += 2.0;
            if(times[i] < cutoff)
            {
               timespent += 25.0;
               timespent += times[i];
               clears++;
            }
            else
            {
               timespent += 10.0;
               if(handheldmode == false)
               {
                  timespent += 3.0;
               }
            }
         }        
         System.out.printf("WR Skip cutoff: %.1f   Clears: %d  Time elapsed: %.3f   Average clear time: %.3f   Clears per hour: %.3f\n", cutoff, clears, timespent, timespent/clears, (clears*3600.000)/timespent);
   }
   public static boolean handleArgs(String[] args)
   {
      if (args.length != 1)
      {
         System.out.println("Wrong number of command line arguments.");
         return false;
      }
      inputFile = new File(args[0]);
      if (inputFile.canRead() == false)
      {
         System.out.println("The file " + args[0] + " cannot be opened for input.");
         return false;
      }
      return true;
   }
   public static double [] inpArray(Scanner input) 
   {
         ArrayList<Double> num = new ArrayList<Double>();
         while(input.hasNextDouble())
         {
             double inp = input.nextDouble();
             num.add(inp);
         }
         Double [] array = num.toArray(new Double[num.size()]);
         double[] a = new double [num.size()];
         for(int i = 0; i < num.size(); i++)
         {
            a[i] = array[i];
         }
         return a; 
   }
}