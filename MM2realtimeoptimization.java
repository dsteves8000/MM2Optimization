import java.util.*;
public class MM2realtimeoptimization
{
   static double cutoff = 0.0;
   static double optcutoff = 0.0;
   static int clears = 0;
   static int optclears = 0;
   static double timespent = 0.0;
   static double opttimespent = 0.0;
   static double cph = 0.0;
   static double maxcph = 0.0;
   public static void main(String [] args)
   {
      ArrayList<Double> timevals = new ArrayList<Double>();
      for(;;)
      {
         System.out.println();
         Scanner sc = new Scanner(System.in);
         while (!sc.hasNextDouble()) 
         {
           sc.next();
         }
         timevals.add(sc.nextDouble()); 
         Double [] timearray = timevals.toArray(new Double[timevals.size()]);
         for(int i = 0; i < timearray.length; i++)
         {
            System.out.println(timearray[i]);
         }
         maxcph = 0.0;
         for(double cutoff = 120.0; cutoff >= 0.0; cutoff -= 0.1)
         {
           //timespent = 0.0;
           //clears = 0;
           clearanalysis(cutoff, timearray, true);
           cph = (clears*3600.000)/timespent;
           if(cph >= maxcph)
           {
               maxcph = cph;
               optcutoff = cutoff;
               optclears = clears;
               opttimespent = timespent;
           }
           //System.out.printf("!WR Skip cutoff: %.1f   Clears: %d  Time elapsed: %.3f   Average clear time: %.3f   Clears per hour: %.3f\n", cutoff, clears, timespent, timespent/clears, cph);
         }
         System.out.printf("WR Skip cutoff: %.1f   Clears: %d  Time elapsed: %.3f   Average clear time: %.3f   Clears per hour: %.3f\n", optcutoff, optclears, opttimespent, opttimespent/optclears, maxcph);
      }
   }
   public static void clearanalysis(double cutoff, Double [] times, boolean handheldmode)
   {
         timespent = 0.0;
         clears = 0;
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
         cph = (clears*3600.000)/timespent;
         //System.out.printf("WR Skip cutoff: %.1f   Clears: %d  Time elapsed: %.3f   Average clear time: %.3f   Clears per hour: %.3f\n", cutoff, clears, timespent, timespent/clears, cph);
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