package Dictionary;


import java.util.HashMap;
import java.util.Map;


public class EntryOfDictionary {
    String word;
    HashMap<String, Integer> associations;
    String lastKey;

    public EntryOfDictionary(String word) {
        this.word = word;
        associations = new HashMap<String, Integer>();
    }

    public void addAssociation(String association){
        associations.put(association, 1);
        lastKey = association;
    }

    /*add weight to last added association*/
    public void addWeight(int weight){
        associations.replace(lastKey, weight);
    }

    @Override
    public String toString() {
        return "EntryOfDictionary{" +
                "word='" + word + '\'' +
                ", associations("+associations.size()+") =" + associations.toString() +
                '}';
    }
}
