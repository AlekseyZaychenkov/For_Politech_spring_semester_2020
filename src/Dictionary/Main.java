package Dictionary;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    ArrayList<EntryOfDictionary> listOfEntriesD;
    public static void main(String[] args) throws IOException {
        ArrayList<EntryOfDictionary> listOfEntriesD = (ArrayList<EntryOfDictionary>)DictionaryReader.readDictionary("src\\Dictionary\\Русский Региональный Ассоциативный СловарьТезаурус.txt");
          do {
              Game game = new Game(listOfEntriesD);
              game.play();
              System.out.println("Do you want to start a new game?"+"\n"+
                      "Press Y or N");
          }while (new Scanner(System.in).nextLine().toUpperCase().equals("Y"));
    }
}
