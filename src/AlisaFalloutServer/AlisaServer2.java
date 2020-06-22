package AlisaFalloutServer;


import GameTheAssociations.DictionaryReader;
import GameTheAssociations.EntryOfDictionary;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class AlisaServer2 {
    static HashSet<String> prepositions;
    static ArrayList<EntryOfDictionary> listOfAssociationEntriesD;
    static boolean prepositionsAreReady;
    static boolean associationsAreReady;


    static {
        try {
            prepositions = readPrepositions("src\\AlisaFalloutServer\\prepositions.txt");
            if ((prepositions == null) || (prepositions.size() == 0))
                System.out.println("The dictionary is has not been read!");
            else {
                prepositionsAreReady = true;
                System.out.println("AlisaServer: Prepositions were downloaded successfully.");
            }
        } catch (IOException ioe) {
            System.out.println("Can't read prepositions");
        }

        listOfAssociationEntriesD = (ArrayList<EntryOfDictionary>) DictionaryReader.readDictionary("src\\GameTheAssociations\\Русский Региональный Ассоциативный СловарьТезаурус.txt");
        if ((listOfAssociationEntriesD == null) || (listOfAssociationEntriesD.size() == 0))
            System.out.println("The dictionary is has not been read!");
        else {
            associationsAreReady = true;
            System.out.println("AlisaServer: Associations were downloaded successfully.");
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        args = Arrays.asList("4322").toArray(new String[0]);

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
        }

        int portNumber = Integer.parseInt(args[0]);

        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
        ExecutorService executorService = Executors.newCachedThreadPool();
        // for (int i = 0; i < 3; i++) {

        executorService.submit(() -> {
            try (
                    Socket clientSocket = serverSocket.accept();
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()))
            ) {
                String inputLine = "none";

                Object objectInput= null;
                //System.out.println(in.read());

                while ((objectInput = in.readObject()) != null) {

                    if(objectInput instanceof String) {
                        System.out.println("Is String");
                        inputLine = (String) objectInput;
                    } else {
                        System.out.println("Not String");
                    }

                    if (inputLine.equals("close"))
                        break;
                    out.println(toFormAnswer(inputLine));
                }
                //out.println("Closing connection with server");
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException ec){
                System.out.println("ClassNotFoundException");
                System.out.println(ec.getMessage());
            }
        });
        //}
        executorService.awaitTermination(5, TimeUnit.MINUTES);
        serverSocket.close();
    }



    private static String toFormAnswer(String receivedRequest) {
        String response = receivedRequest;
        if(receivedRequest==null||receivedRequest.length()==0)
            return response;

        // splitting the question
        String[] words = (String[])receivedRequest.split("[-\\t,'`\"\";.?!&:@\\[\\](){}_*/\\s+]+");
        ArrayList<String> receivedArrayRequest = new ArrayList<String>();
        for (String w: words)
            if(w.length()>0)
                receivedArrayRequest.add(w);
        if (receivedArrayRequest.size() == 0)
            return response = "What?";

        // filtering prepositions
        if(prepositionsAreReady){
            HashSet<String> notPrepositions = new HashSet<>();
            for(String word : receivedArrayRequest)
                if (!prepositions.contains(word))
                    notPrepositions.add(word);

            if (notPrepositions.size() > 0) {
                Object[] objects = notPrepositions.toArray();
                receivedArrayRequest.clear();
                for (Object o : objects)
                    receivedArrayRequest.add(o.toString());
            }
        }

        // choosing one word for the response
        response = receivedArrayRequest.get((int)(Math.random()*(receivedArrayRequest.size())));

        // adding word from the association dictionary
        if(associationsAreReady){
            for (EntryOfDictionary entryOfDictionary : listOfAssociationEntriesD){
                if(entryOfDictionary.getMainWord().equalsIgnoreCase(response)) {
                    HashMap<String, Integer> associations_strong = entryOfDictionary.getAssociations_strong();
                    HashMap<String, Integer> associations_medium = entryOfDictionary.getAssociations_medium();
                    HashMap<String, Integer> associations_weak = entryOfDictionary.getAssociations_weak();

                    String association = "";
                    if (associations_strong.size() > 0)
                        association = new ArrayList<String>(
                                associations_strong.keySet())
                                .get((int) (Math.random() * associations_strong.size()));
                    else if (associations_medium.size() > 0)
                        association = new ArrayList<String>(
                                associations_medium.keySet())
                                .get((int) (Math.random() * associations_medium.size()));
                    else
                        association = new ArrayList<String>(
                                associations_weak.keySet())
                                .get((int) (Math.random() * associations_weak.size() ));
                    response = association + " " + response;
                }
            }
        }

        // forming the answer with first capital letter and question mark
        response = response.substring(0,1).toUpperCase()+response.substring(1,response.length())+"?";
        return response;
    }



    private static HashSet readPrepositions(String readFrom) throws IOException {
        HashSet<String> prepositions;
        try (final Stream<String> lines =
                     Files.lines(
                             Paths.get(readFrom))
                             .map(line -> line.split("[-\\t,;.?!:@\\[\\](){}_*/\\s+]+"))  // delimiters
                             .flatMap(Arrays::stream)) {
            List<String> prepositionsList = lines
                    .filter(x -> x.length() != 0)
                    .filter(x -> x.matches("[А-Я]"))
                    .collect(Collectors.toList());

            prepositions = new HashSet(prepositionsList);
        }
        return prepositions;
    }




}