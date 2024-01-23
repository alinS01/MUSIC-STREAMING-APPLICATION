import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import java.io.*;
import java.util.*;

public class ProiectPOO {

    private static HashMap<Integer, Streamers> streamers = new HashMap<>();
    private static HashMap<Integer, Streams> streams = new HashMap<>();
    private static HashMap<Integer, User> users = new HashMap<>();

    public static String convertLengthToMinutesAndSeconds(long length) {
        long minutes = length / 60;
        long seconds = length % 60;
        long hours = minutes / 60;
        minutes = minutes % 60;
        String time = "";
        if (hours > 0) {
            if (hours <= 9) {
                time = "0" + hours + ":";
            } else {
                time = hours + ":";
            }
        }
        if (minutes <= 9) {
            time = time + "0" + minutes + ":";
        } else {
            time = time + minutes + ":";
        }
        if (seconds <= 9) {
            time = time + "0" + seconds;
        } else {
            time = time + seconds;
        }
        return time;
    }

    public static void main(String[] args) throws IOException {
        if (args == null) {
            System.out.println("Nothing to read here");
            return;
        }
        // Verifica daca primeste argumente
        if (args.length != 4) {
            System.out.println("Trebuie sa furnizati 3 fisiere CSV si un fisier text de comenzi.");
            return;
        }

        streamers = new HashMap<>();
        streams = new HashMap<>();
        users = new HashMap<>();

        Facade facade = new Facade(streamers, streams, users);
        facade.readInputFiles(args);
        String path = ("src/main/resources/" + args[3]);
        processCommands("src/main/resources/" + args[3]);


    }

    public static void processCommands(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            // line = br.readLine();
            Invoker invoker = new Invoker();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(" ");
                String commandName = values[1];

                Command command = null;
                if (commandName.equals("LIST")) {
                    if (streamers.containsKey(Integer.parseInt(values[0]))) {
                        command = new ListStreamsCommand(streamers.get(Integer.parseInt(values[0])), streams);
                        // listStreams(streamers.get(Integer.parseInt(values[0])));
                    } else {
                        command = new ListUsersCommand(users.get(Integer.parseInt(values[0])), streamers, streams);
                        //   listUsers(users.get(Integer.parseInt(values[0])));
                    }
                }

                if (commandName.equals("ADD")) {
                    command = new addStreamCommand(values, streams);
                    // addStream(values);
                }

                if (commandName.equals("DELETE")) {
                    command = new deleteStreamCommand(values, streams);
                    //  deleteStream(values);
                }

                if (commandName.equals("LISTEN")) {
                    command = new listenStreamCommand(values, streams, users);
                    //  listenStream(values);
                }

                if (commandName.equals("RECOMMEND")) {
                    command = new recommendStreamCommand(values, streamers, streams, users);
                    //  recommendStream(values);
                }

                if (commandName.equals("SURPRISE")) {
                    command = new recommend3surpriseStreamCommand(values, streamers, streams, users);
                    //   recommend3surpriseStream(values);
                }
                invoker.addCommand(command);
            }
            invoker.executeCommands();
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul " + path + " nu a putut fi gasit.");
        } catch (IOException e) {
            System.out.println("Eroare la citirea fisierului " + path + ".");
        }


    }
}
