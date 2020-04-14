package Dictionary;


import java.util.HashMap;

public class EntryOfDictionary {
    private String word;
    private final HashMap<String, Integer> associations_weak;
    private final HashMap<String, Integer> associations_medium;
    private final HashMap<String, Integer> associations_strong;
    private static final int medium_boundary = 2;
    private static final int strong_boundary = 10;
    private String lastKey;

    public EntryOfDictionary(String word) {
        this.word = word;
        associations_weak = new HashMap<String, Integer>();
        associations_medium = new HashMap<String, Integer>();
        associations_strong = new HashMap<String, Integer>();
    }

    public void addAssociation(String association){
        associations_weak.put(association, 1);
        lastKey = association;
    }

    /*add weight to last added association*/
    public void addWeight(int weight){
        try {
            if (weight < medium_boundary)
                associations_weak.replace(lastKey, weight);
            else if ((medium_boundary <= weight) && (weight < strong_boundary)) {
                associations_weak.remove(lastKey);
                associations_strong.remove(lastKey);
                associations_medium.put(lastKey, weight);
            } else {
                associations_weak.remove(lastKey);
                associations_medium.remove(lastKey);
                associations_strong.put(lastKey, weight);
            }
        } catch (Exception e) {
            System.out.println("lastKey: "+ lastKey +" weight: "+weight);
            System.out.println(e);
        }
    }

    public String getMainWord(){
        return word;
    }

    public HashMap<String, Integer> getAssociations_weak() {
        return associations_weak;
    }

    public HashMap<String, Integer> getAssociations_medium() {
        return associations_medium;
    }

    public HashMap<String, Integer> getAssociations_strong() {
        return associations_strong;
    }


    @Override
    public String toString() {
        return "EntryOfDictionary{" +
                "word='" + word + '\'' + "\n" +
                ", associations_weak("+associations_weak.size()+") =" + associations_weak.toString() +"\n" +
                ", associations_medium("+associations_medium.size()+") =" + associations_medium.toString() +"\n" +
                ", associations_strong("+associations_strong.size()+") =" + associations_strong.toString() +
                '}';
    }
}
