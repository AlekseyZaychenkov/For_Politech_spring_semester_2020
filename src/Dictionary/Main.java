package Dictionary;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<EntryOfDictionary> listOfEntriesD = new ArrayList<EntryOfDictionary>();

        // getting lines from file:
        try (final Stream<String> lines =
                Files.lines(
                     Paths.get("src\\Dictionary\\Русский Региональный Ассоциативный СловарьТезаурус.txt"))
                 .flatMap(line->Arrays.stream(line.split("[,;\\s+]")))){

            // creating a List with all words from the file
            List<String> storageList = lines
                    .filter(x -> x.length() != 0)
                    .collect(Collectors.toList());


            EntryOfDictionary entryOfDictionary = new EntryOfDictionary("start");

            for (String word : storageList){
                word = word.replace(",", " ").trim();
                try {
                    if (!word.matches("[0-9]{1,30}"))
                        entryOfDictionary.addAssociation(word);
                    else
                        entryOfDictionary.addWeight(Integer.parseInt(word));
                } catch (NumberFormatException nfe) {
                    System.out.println(nfe);
                }

                if(word.matches("[А-Я]{1,50}:")){
                    listOfEntriesD.add(entryOfDictionary);
                    entryOfDictionary = new EntryOfDictionary(word);
                }
            }
            listOfEntriesD.add(entryOfDictionary);

            for (int i=0; i<10; i++) {
                System.out.println(listOfEntriesD.get(i).toString());
                System.out.println("=============================");
            }


            for (int i=300; i<350; i++) {
                System.out.println(storageList.get(i));
                System.out.println("---------------");
            }





            // getting map from lines:
           /* Map<String, Long> countMap = lines
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
        */
        } catch (NullPointerException npe) {
            System.out.println("Wow, NullPointerException, yeah?");
        } catch (NoSuchFileException ex){
            System.out.println("File does not exists");
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
