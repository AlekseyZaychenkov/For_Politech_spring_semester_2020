package Dictionary;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DictionaryReader {
    public static List<EntryOfDictionary> readDictionary(String pathToDictionary){
        ArrayList<EntryOfDictionary> listOfEntriesD = new ArrayList<EntryOfDictionary>();

        // getting lines from file:
        try (final Stream<String> lines =
                     Files.lines(
                             Paths.get(pathToDictionary))
                             .flatMap(line-> Arrays.stream(line.split("[,;\\s+]")))){

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

                if (word.matches("[А-Я]{1,50}:")) {
                    entryOfDictionary = new EntryOfDictionary(word.substring(0,word.length()-1));
                    listOfEntriesD.add(entryOfDictionary);
                }
            }


        } catch (NullPointerException npe) {
            System.out.println("Wow, NullPointerException, yeah?");
        } catch (NoSuchFileException ex){
            System.out.println("File does not exists");
        } catch (Exception e){
            System.out.println(e);
        }

        return listOfEntriesD;
    }
}
