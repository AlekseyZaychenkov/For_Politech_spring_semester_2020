package DiscreteRandomVariable;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) throws IOException {

        // input data:
      /*  System.out.print("Enter minimum length of word: ");
        int minLength = 5;//new Scanner(System.in).nextInt();
        System.out.print("Enter maximum length of word: ");
        int maxLength = 15;//new Scanner(System.in).nextInt();
        System.out.print("Enter minimum frequency: ");
        int minFrequency = 1;//new Scanner(System.in).nextInt();*/
        System.out.println("Enter filename: ");
        System.out.println("* hint: mayby you want try \"Tolstoy.txt\" ?");
        // paste this:
        // Tolstoy.txt

        // getting lines from file:
        try (final Stream<String> lines =
                Files.lines(
                     Paths.get("src/DiscreteRandomVariable/"
                                              + new Scanner(System.in).nextLine()))
                .map(line -> line.split("[-\\t,;.?!:@\\[\\](){}_*/\\s+]+"))  // delimiters
                .flatMap(Arrays::stream)){




            List<String> storageList = lines.collect(Collectors.toList());
            // getting map from lines:
            /*Map<String, Long> countMap = lines
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));*/

            // calculating of maximum length of word
            int maxLength = 0;
            OptionalInt maxOpt  = storageList
                    .stream()
                    .mapToInt(String::length)
                    .max();
            maxLength = maxOpt.orElse(-1);

            long totalWords = storageList.size();
            Main.EntityForFrequency entityFF;
            ArrayList<EntityForFrequency> entitiesList = new ArrayList<EntityForFrequency>(maxLength+1);

            // counting of number words of each length
            for (int i=0; i<(maxLength+1); i++) {
                entityFF = new Main().new EntityForFrequency();
                final int currentLength = i;
                entityFF.setXi(storageList
                        .stream()
                        .filter(x -> x.length() == currentLength)
                        .count());

                entitiesList.add(currentLength, entityFF);
            }


            // counting of probability for each length
            for (int i=1; i<(maxLength+1); i++) {
                entityFF = entitiesList.get(i);
                double Pi = entityFF.getXi()/totalWords;
                entityFF.setPi(Pi);
                entitiesList.add(i, entityFF);
            }




            printTable( entitiesList);


           // for (Map.Entry<String, Long> entry : countMap.entrySet())
            //      System.out.println(String.format(entry.getKey()) +": " + entry.getValue().toString());

      }

    }








    public static void printTable(List<EntityForFrequency> entitiesList){

       // String.format("%1$"+maxLength+ "s", entry.getKey()) +": " + entry.getValue().toString());

        for(int i=1; i<(entitiesList.size()+1);i++)
            System.out.format("%12d", i);
        System.out.println();
        for(EntityForFrequency entityFF : entitiesList)
            System.out.format("%12d", entityFF.getXi());
        System.out.println();
        for(EntityForFrequency entityFF : entitiesList)
            System.out.format("%12f", entityFF.getPi());
        System.out.println();
        for(EntityForFrequency entityFF : entitiesList)
            System.out.format("%12f", entityFF.getM());
        System.out.println();
        for(EntityForFrequency entityFF : entitiesList)
            System.out.format("%12f", entityFF.getD());
        System.out.println();
      /*  for(EntityForFrequency entityFF : entitiesList)
            System.out.print(String.format("%1$"+entityFF.getXi()+" "+'\t'+'\t'+'\t'+'\t'));
        System.out.println();
        for(EntityForFrequency entityFF : entitiesList)
            System.out.print(String.format("%1$"+entityFF.getPi()+" "+'\t'+'\t'+'\t'+'\t'));
        System.out.println();
        for(EntityForFrequency entityFF : entitiesList)
            System.out.print(String.format("%1$"+entityFF.getM()+" "+'\t'+'\t'+'\t'+'\t'));
        System.out.println();
        for(EntityForFrequency entityFF : entitiesList)
            System.out.print(String.format("%1$"+entityFF.getD()+" "+'\t'+'\t'+'\t'+'\t'));
        System.out.println();*/
    }


    class EntityForFrequency{
        long Xi;  // Frequency
        double Pi;  // Probability
        double M;   // Mathematical Expectation
        double D;   // Random Variance Dispersion

        public long getXi() {
            return Xi;
        }

        public void setXi(long xi) {
            Xi = xi;
        }

        public double getPi() {
            return Pi;
        }

        public void setPi(double pi) {
            Pi = pi;
        }

        public double getM() {
            return M;
        }

        public void setM(double m) {
            M = m;
        }

        public double getD() {
            return D;
        }

        public void setD(double d) {
            D = d;
        }
    }


}
