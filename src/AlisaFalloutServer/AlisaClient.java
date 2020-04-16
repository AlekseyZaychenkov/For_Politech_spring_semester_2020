package AlisaFalloutServer;

/*"Сервер работает по протоколу Алиса-fallout4:
клиент задает вопрос Алисе в виде вопросительного предложения.
Сервер Алисы принимает строку и выбирает произвольное слово из предложения
в качестве вопроса, возвращаемого клиенту.
Пример.
К: Алиса, скакая погода будет завтра?
А: Погода?
Транспорт - cокеты. Взаимодействие заканчивается, если клиент отправляет
строку close. Строки клиент считывает из консоли.
[использование словаря ассоциаций +20 баллов, искусственного интеллекта +100 баллов]"*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;


public class AlisaClient {
        public static void main(String[] args) throws IOException {
            String[] address = Arrays.asList("127.0.0.1", "4322").toArray(new String[0]);

            if (address.length != 2) {
                System.err.println(
                        "Usage: java EchoClient <host name> <port number>");
//            System.exit(1);
            }

            String hostName = address[0];
            int portNumber = Integer.parseInt(address[1]);

            try (  Socket echoSocket = new Socket(hostName, portNumber);
                    PrintWriter out =
                            new PrintWriter(echoSocket.getOutputStream(), true);
                    BufferedReader in =
                            new BufferedReader(
                                    new InputStreamReader(echoSocket.getInputStream()));
                    BufferedReader stdIn =
                            new BufferedReader(
                                    new InputStreamReader(System.in))
            ) {
                String userInput;
                while ((userInput = stdIn.readLine()) != null) {
                    out.println(userInput);
                    String receivedWord = in.readLine();
                    System.out.println("echo: " + receivedWord);
                    if (receivedWord.equals("close"))
                        break;
                }
                System.out.println("The communication finished");
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host " + hostName);
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Couldn't get I/O for the connection to " +
                        hostName);
                System.exit(1);
            }
        }
    }

