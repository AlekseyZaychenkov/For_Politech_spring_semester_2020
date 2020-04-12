package DiscreteRandomVariable;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    static double M;   // Mathematical Expectation
    static double D;   // Random Variance Dispersion
    static long totalWords; // Total words in a file
    static Main.EntityForFrequency entityFF; // temporal variable for calculations

    public static void main(String[] args) throws IOException {

        // input data:
        System.out.println("Enter filename: ");
        System.out.println("* hint: mayby you want try \"Tolstoy.txt\"?");
        // paste this:
        // Tolstoy.txt

        // getting lines from file:
        try (final Stream<String> lines =
                Files.lines(
                     Paths.get("src/DiscreteRandomVariable/"
                                              + new Scanner(System.in).nextLine()))
                .map(line -> line.split("[-\\t,;.?!:@\\[\\](){}_*/\\s+]+"))  // delimiters
                .flatMap(Arrays::stream)){

            // creating a List with all words from the file
            List<String> storageList = lines
                    .filter(x -> x.length() != 0)
                    .collect(Collectors.toList());

            // calculating of a maximum length of word
            int maxLength = 0;
            OptionalInt maxOpt  = storageList
                    .stream()
                    .mapToInt(String::length)
                    .max();
            maxLength = maxOpt.orElse(-1);

            totalWords = storageList.size();

            // counting of number words of each length
            ArrayList<EntityForFrequency> entitiesList
                    = (ArrayList)createEntitiesList(storageList, maxLength);

            // counting of probability for each length, M and D
            calculatePiMD( entitiesList);

            // print result
            printResult(entitiesList, M, D);

        } catch (NullPointerException npe) {
            System.out.println("Wow, NullPointerException, yeah?");
        } catch (NoSuchFileException ex){
            System.out.println("File does not exists");
        } catch (Exception e){
            System.out.println(e);
        }
    }

    static  List<EntityForFrequency> createEntitiesList(List<String> storageList, int maxLength){
        ArrayList<EntityForFrequency> entitiesList = new ArrayList<EntityForFrequency>(maxLength+1);
        for (int i=0; i<(maxLength+1); i++) {
            entityFF = new Main().new EntityForFrequency();
            final int currentLength = i;
            entityFF.setXi(storageList
                    .stream()
                    .filter(x -> x.length() == currentLength)
                    .count());
            entitiesList.add(currentLength, entityFF);
        }
        return entitiesList;
    }

    static void calculatePiMD(List<EntityForFrequency> entitiesList) {
        for (int i = 1; i < (entitiesList.size()); i++) {
            entityFF = entitiesList.get(i);
            entityFF.setPi(((double) entityFF.getXi()) / ((double) totalWords));
            M += (double) entityFF.getXi() * entityFF.getPi();
            D += (double) entityFF.getXi() * (double) entityFF.getXi() * entityFF.getPi();
            entitiesList.set(i, entityFF);
        }
        D -= M*M;
    }




    static void printResult(List<EntityForFrequency> entitiesList, double M, double D){
       // String.format("%1$"+maxLength+ "s", entry.getKey()) +": " + entry.getValue().toString());

        System.out.print("Letters:");
        for(int i=0; i<entitiesList.size(); i++)
            System.out.format("%12d", i);
        System.out.println();

        System.out.print("Xi       :");
        for(EntityForFrequency entityFF : entitiesList)
            System.out.format("%12d", entityFF.getXi());
        System.out.println();

        System.out.print("Pi        :");
        for(EntityForFrequency entityFF : entitiesList)
            System.out.format("%12f", entityFF.getPi());
        System.out.println();

        System.out.println("Mathematical Expectation: "+M);
        System.out.println("Random Variance Dispersion: "+D);
    }


    class EntityForFrequency{
        long Xi;  // Frequency
        double Pi;  // Probability


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
    }
}
