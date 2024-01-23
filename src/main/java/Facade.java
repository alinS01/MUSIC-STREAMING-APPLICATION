import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Facade {
    public static void readStreamersFile(String streamersFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(streamersFile))) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                line = line.replace("\"", "");
                String[] values = line.split(",");
                int streamerType = Integer.parseInt(values[0]);
                int id = Integer.parseInt(values[1]);
                String streamerName = values[2];
                Streamers streamer = StreamerFactory.createStreamer(streamerType, id, streamerName);
                streamers.put(id, streamer);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul " + streamersFile + " nu a putut fi gasit.");
        } catch (IOException e) {
            System.out.println("Eroare la citirea fisierului " + streamersFile + ".");
        }
    }

    public static void readStreamsFile(String streamsFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(streamsFile))) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                line = line.replace("\"", "");
                String[] values = line.split(",");
                int streamType = Integer.parseInt(values[0]);
                int id = Integer.parseInt(values[1]);
                int streamGenre = Integer.parseInt(values[2]);
                long noOfStreams = Long.parseLong(values[3]);
                int streamerId = Integer.parseInt(values[4]);
                long length = Long.parseLong(values[5]);
                long dateAdded = Long.parseLong(values[6]);
                String name = "";
                for (int i = 7; i < values.length; i++) {
                    name = name + values[i] + ",";
                }
                name = name.substring(0, name.length() - 1);
                Streams stream = StreamFactory.createStream(streamType, id, streamGenre, noOfStreams, streamerId, length, dateAdded, name);
                streams.put(id, stream);//se instantiaza un nou obiect de tip Streams si se adauga in HashMap
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul " + streamsFile + " nu a putut fi gasit.");
        } catch (IOException e) {
            System.out.println("Eroare la citirea fisierului " + streamsFile + ".");
        }
    }

    public static void readUsersFile(String usersFile) {

        try (BufferedReader br = new BufferedReader(new FileReader(usersFile))) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                line = line.replace("\"", "");
                String[] values = line.split(",");
                int id = Integer.parseInt(values[0]);
                String userName = values[1];

                ArrayList<Integer> userStreams = new ArrayList<>();
                String[] streamsValues = values[2].split(" ");
                for (String streamsValue : streamsValues) {
                    userStreams.add(Integer.parseInt(streamsValue));


                }
                //  userStreams.add(streamsValues);
                //  System.out.println(userStreams);

                User user = new User(id, userName, userStreams);//se instantiaza un nou obiect de tip User si se adauga in HashMap


                users.put(id, user);
                //  System.out.println(users.get(id));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul " + usersFile + " nu a putut fi gasit.");
        } catch (IOException e) {
            System.out.println("Eroare la citirea fisierului " + usersFile + ".");
        }
    }

    public void readInputFiles(String[] args) {
        String streamersFile = ("src/main/resources/" + args[0]);
        String streamsFile = ("src/main/resources/" + args[1]);
        String usersFile = ("src/main/resources/" + args[2]);

        readStreamersFile(streamersFile);
        readStreamsFile(streamsFile);
        readUsersFile(usersFile);

    }

    private static HashMap<Integer, Streamers> streamers = new HashMap<>();
    private static HashMap<Integer, Streams> streams = new HashMap<>();
    private static HashMap<Integer, User> users = new HashMap<>();

    public Facade(HashMap<Integer, Streamers> streamers, HashMap<Integer, Streams> streams, HashMap<Integer, User> users) {
        this.streamers = streamers;
        this.streams = streams;
        this.users = users;
    }

    public HashMap<Integer, Streamers> getStreamers() {
        return streamers;
    }

    public HashMap<Integer, Streams> getStreams() {
        return streams;
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }
}
