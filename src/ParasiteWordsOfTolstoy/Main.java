package ParasiteWordsOfTolstoy;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;




public class Main {

    public static void main(String[] args) throws IOException {

        // input data:
        System.out.print("Enter minimum length: ");
        int minLength = new Scanner(System.in).nextInt();
        System.out.print("Enter maximum length: ");
        int maxLength = new Scanner(System.in).nextInt();
        System.out.print("Enter minimum frequency: ");
        int minFrequency = new Scanner(System.in).nextInt();
        System.out.println("Enter filename: ");
        System.out.println("* hint: mayby you want try \"Tolstoy.txt\" ?");
        // paste this:
        // Tolstoy.txt

        // getting lines from file:
        try (final Stream<String> lines =
                Files.lines(
                     Paths.get("out/production/For_Politech_spring_semester_2020/ParasiteWordsOfTolstoy/"
                                                             + new Scanner(System.in).nextLine()))
                .map(line -> line.split("[-\\t,;.?!:@\\[\\](){}_*/\\s+]+"))  // delimiters
                .flatMap(Arrays::stream)){

            // getting map from lines:
            Map<String, Long> countMap = lines
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            // filtering words by length and frequency + sort
            Map<String, Long> filteredMap = countMap.entrySet().stream()
                    .filter(x -> x.getValue() >= minFrequency)
                    .filter(entry -> entry.getKey().length() > minLength) // filter for minimum length
                    .filter(entry -> entry.getKey().length() < maxLength) // filter for maximum length
                    .sorted((Map.Entry.<String, Long>comparingByValue().reversed()))   // sort by frequency (from the highest)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

            // printing out have got result
            for (Map.Entry<String, Long> entry : filteredMap.entrySet())
                System.out.println(String.format("%1$"+maxLength+ "s", entry.getKey()) +": " + entry.getValue().toString());
        }

    }
}
