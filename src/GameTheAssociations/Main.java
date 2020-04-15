package GameTheAssociations;

/*"Подсчет частоты появления слов во входном потоке. Дать возможность указывать минимальную/максимальную длину слова, участвующую в подсчёте частоты, для фильтрации предлогов и местоимений. Использовать Java8 Stream API. Файл для анализа https://drive.google.com/open?id=1YnRy5H8Emx4kyA1-lLZkNuY8LDTplulu
 * Выяснить самые популярные прилагательные в тексте."*/

import java.io.IOException;
import java.util.*;


public class Main {
    ArrayList<EntryOfDictionary> listOfEntriesD;
    public static void main(String[] args) throws IOException {
        ArrayList<EntryOfDictionary> listOfEntriesD = (ArrayList<EntryOfDictionary>)DictionaryReader.readDictionary("src\\GameTheAssociations\\Русский Региональный Ассоциативный СловарьТезаурус.txt");
          if (listOfEntriesD == null){
              System.out.println("The dictionary is has not been read!");
              return;
          }
        do {
              Game game = new Game(listOfEntriesD);
              game.play();
              System.out.println("Do you want to start a new game?"+"\n"+
                      "Press Y or N");
          } while (new Scanner(System.in).nextLine().toUpperCase().equals("Y"));
    }
}
