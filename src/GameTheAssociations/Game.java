package GameTheAssociations;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private final int difficultyLevel;
    private String difficultyLevelString;
    private int roundLeftsCounter;
    private EntryOfDictionary entryOfDictionary;
    ArrayList<EntryOfDictionary> listOfEntriesD;
    private ArrayList<String> givenAssociations;
    private ArrayList<String> entriesAssociations;
    private boolean wantToContinue = true;

    public Game(ArrayList<EntryOfDictionary> listOfEntriesD) {
        givenAssociations = new ArrayList<>();
        this.listOfEntriesD = listOfEntriesD;
        System.out.println("<===   New Game  ===>"+"\n" +
                "Please, select difficulty level:"+"\n" +
                "1 - easy (12 attempts, usual and medium associations)"+"\n" +
                "2 - medium (10 attempts, usual and medium associations)"+"\n" +
                "3 - hard (10 attempts, rare associations)");
        this.difficultyLevel = new Scanner(System.in).nextInt();

        switch (difficultyLevel){
            case (1):
                roundLeftsCounter = 12;
                difficultyLevelString = "easy";
                do {
                    entryOfDictionary = listOfEntriesD.get((int)Math.floor(Math.random()*listOfEntriesD.size()));
                } while ((entryOfDictionary.getAssociations_strong().size()+entryOfDictionary.getAssociations_medium().size())<=roundLeftsCounter);
                entriesAssociations = new ArrayList<String>(entryOfDictionary.getAssociations_strong().keySet());
                entriesAssociations.addAll(entryOfDictionary.getAssociations_medium().keySet());
                break;
            case (2):
                roundLeftsCounter = 10;
                difficultyLevelString = "medium";
                do {
                    entryOfDictionary = listOfEntriesD.get((int)Math.floor(Math.random()*listOfEntriesD.size()));
                } while ((entryOfDictionary.getAssociations_strong().size()+entryOfDictionary.getAssociations_medium().size())<=roundLeftsCounter);
                entriesAssociations =  new ArrayList<String>(entryOfDictionary.getAssociations_strong().keySet());
                entriesAssociations.addAll(entryOfDictionary.getAssociations_medium().keySet());
                break;
            case (3):
                roundLeftsCounter = 10;
                difficultyLevelString = "hard";
                do {
                    entryOfDictionary = listOfEntriesD.get((int)Math.floor(Math.random()*listOfEntriesD.size()));
                } while (entryOfDictionary.getAssociations_weak().size()<=roundLeftsCounter);
                entriesAssociations =  new ArrayList<String>(entryOfDictionary.getAssociations_weak().keySet());
                break;
        }
    }

    public void play(){
        boolean isSuccess = false;
        while ((roundLeftsCounter>0)&&!isSuccess&&wantToContinue)
            isSuccess = playRound();


        System.out.println("The word was \""+entryOfDictionary.getMainWord()+"\"");
        if (isSuccess) {
            System.out.println("CONGRATULATIONS! YOU ARE WON! \n");
        } else {
            System.out.println("Attempts is over. Good look next time! \n");
        }

    }

    private boolean playRound(){
        System.out.println("Difficulty level: "+difficultyLevelString+"\n"+
                "Attempts left: "+roundLeftsCounter+"\n"+
                "The list of associations:"
        );
        int numberInList = (int)(Math.random()*entriesAssociations.size());
        String newAssociation = entriesAssociations.get(numberInList);
        entriesAssociations.remove(numberInList);
        givenAssociations.add(newAssociation);

        for (String association : givenAssociations)
            System.out.println(association);


        String guess = new Scanner(System.in).nextLine();
        roundLeftsCounter--;
        if(guess.equals("exit")) {
            wantToContinue = false;
            System.out.println("You printed \"exit\"");
            return false;
        } else if (guess.equalsIgnoreCase(entryOfDictionary.getMainWord())) {
            System.out.println("YES! \n");
            return true;
        }else {
            System.out.println("No ( \n");
            return false;
        }
    }
}
