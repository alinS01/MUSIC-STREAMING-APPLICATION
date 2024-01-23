import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

interface Command {
    void execute() throws IOException;
}

class ListStreamsCommand implements Command {
    private Streamers streamer;

    public ListStreamsCommand(Streamers streamer, HashMap<Integer, Streams> streams) {
        this.streamer = streamer;
        this.streams = streams;

    }

    @Override
    public void execute() {
        try {
            listStreams(streamer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HashMap<Integer, Streams> streams = new HashMap<>();
    private static HashMap<Integer, Streamers> streamers = new HashMap<>();
    private static HashMap<Integer, User> users = new HashMap<>();

    public static void listStreams(Streamers streamer) throws IOException {
        JsonArray allStreams = new JsonArray();
        for (Map.Entry<Integer, Streams> entry : streams.entrySet()) {
            Streams stream2 = entry.getValue();
            JsonObject stream = new JsonObject();

            long timestamp = stream2.getDateAdded();
            Date date = new Date(timestamp * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone("London"));
            String dateAdded = sdf.format(date);

            String length = ProiectPOO.convertLengthToMinutesAndSeconds(stream2.getLength());

            if (stream2.getStreamerId() == streamer.getId()) {
                stream.addProperty("id", Integer.toString(stream2.getId()));
                stream.addProperty("name", stream2.getName());
                stream.addProperty("streamerName", streamer.getName());
                stream.addProperty("noOfListenings", Long.toString(stream2.getNoOfStreams()));
                stream.addProperty("length", length);
                stream.addProperty("dateAdded", dateAdded);
                allStreams.add(stream);
            }
        }
        System.out.println((allStreams));
    }
}

class ListUsersCommand implements Command {
    private final User user;

    public ListUsersCommand(User user, HashMap<Integer, Streamers> streamers, HashMap<Integer, Streams> streams) {
        this.streamers = streamers;
        this.streams = streams;
        this.user = user;
    }

    @Override
    public void execute() {
        try {
            listUsers(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HashMap<Integer, Streamers> streamers = new HashMap<>();
    private static HashMap<Integer, Streams> streams = new HashMap<>();


    public static void listUsers(User user1) throws IOException {
        JsonArray allUsers = new JsonArray();
        for (int i = 0; i < user1.getStreams().size(); i++) {
            JsonObject stream = new JsonObject();
            Streams stream1 = streams.get(user1.getStreams().get(i));

            String length = ProiectPOO.convertLengthToMinutesAndSeconds(stream1.getLength());
            long timestamp = stream1.getDateAdded();
            Date date = new Date(timestamp * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone("London"));
            String dateAdded = sdf.format(date);

            stream.addProperty("id", Integer.toString(stream1.getId()));
            stream.addProperty("name", stream1.getName());
            stream.addProperty("streamerName", streamers.get(stream1.getStreamerId()).getName());
            stream.addProperty("noOfListenings", Long.toString(stream1.getNoOfStreams()));
            stream.addProperty("length", length);
            stream.addProperty("dateAdded", dateAdded);

            allUsers.add(stream);
        }
        System.out.println(allUsers);
    }
}

class addStreamCommand implements Command {
    private String[] values;

    public addStreamCommand(String[] values, HashMap<Integer, Streams> streams) {
        this.streams = streams;
        this.values = values;
    }

    @Override
    public void execute() {
        addStream(values);
    }

    private static HashMap<Integer, Streams> streams = new HashMap<>();

    public static void addStream(String[] values) {
        int streamerId = Integer.parseInt(values[0]);
        int streamType = Integer.parseInt(values[2]);
        int id = Integer.parseInt(values[3]);
        int streamGenre = Integer.parseInt(values[4]);
        long noOfStreams = 0;//este nou creat
        long length = Long.parseLong(values[5]);
        long dateAdded = System.currentTimeMillis() / 1000L;
        String name = "";
        for (int i = 6; i < values.length; i++) {
            name = name + values[i] + " ";
        }
        name = name.substring(0, name.length() - 1);
        Streams stream = StreamFactory.createStream(streamType, id, streamGenre, noOfStreams, streamerId, length, dateAdded, name);
        streams.put(id, stream);//se instantiaza un nou obiect de tip Streams si se adauga in HashMap
    }
}

class deleteStreamCommand implements Command {
    private String[] values;

    public deleteStreamCommand(String[] values, HashMap<Integer, Streams> streams) {
        this.streams = streams;
        this.values = values;
    }

    @Override
    public void execute() {
        deleteStream(values);
    }

    private static HashMap<Integer, Streams> streams = new HashMap<>();

    public static void deleteStream(String[] values) {
        int streamerId = Integer.parseInt(values[0]);
        int streamId = Integer.parseInt(values[2]);
        streams.remove(streamId);
    }
}

class listenStreamCommand implements Command {
    private String[] values;

    public listenStreamCommand(String[] values, HashMap<Integer, Streams> streams, HashMap<Integer, User> users) {
        this.streams = streams;
        this.users = users;
        this.values = values;
    }

    @Override
    public void execute() {
        listenStream(values);
    }

    private static HashMap<Integer, Streams> streams = new HashMap<>();
    private static HashMap<Integer, User> users = new HashMap<>();

    public static void listenStream(String[] values) {
        int userId = Integer.parseInt(values[0]);
        int streamId = Integer.parseInt(values[2]);
        if (streams.containsKey(streamId)) {
            streams.get(streamId).incrementNoOfStreams();
        }

        if (users.containsKey(userId)) {
            users.get(userId).streams.add(streamId);
        }

    }
}

class recommendStreamCommand implements Command {
    private String[] values;

    public recommendStreamCommand(String[] values, HashMap<Integer, Streamers> streamers, HashMap<Integer, Streams> streams, HashMap<Integer, User> users) {
        this.values = values;
        this.streamers = streamers;
        this.streams = streams;
        this.users = users;
    }

    @Override
    public void execute() {
        try {
            recommendStream(values);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static HashMap<Integer, Streamers> streamers = new HashMap<>();
    private static HashMap<Integer, Streams> streams = new HashMap<>();
    private static HashMap<Integer, User> users = new HashMap<>();

    public static void recommendStream(String[] values) throws IOException {
        int userId = Integer.parseInt(values[0]);
        String type = values[2];
        int streamType = 0;
        if (type.equals("SONG")) {
            streamType = 1;
        }
        if (type.equals("PODCAST")) {
            streamType = 2;
        }
        if (type.equals("AUDIOBOOK")) {
            streamType = 3;
        }
        ArrayList<Streams> recommendedStreams = new ArrayList<>();
        ArrayList<Integer> streamersId = new ArrayList<>();
        User user = users.get(userId);
        JsonArray allStreams = new JsonArray();
        for (int i = 0; i < user.streams.size(); i++) {
            streamersId.add(streams.get(user.streams.get(i)).getStreamerId());
        }
        for (Map.Entry<Integer, Streams> entry : streams.entrySet()) {
            if (streamersId.contains(entry.getValue().getStreamerId()) &&// au streamerul în lista de ID-uri de streamer
                    entry.getValue().getStreamType() == streamType &&//verifica tipul SONG=1 etc
                    !user.getStreams().contains(entry.getValue().getId())//daca streamul nu e ascutlat sa mi l puna in recommended streams
            ) {
                recommendedStreams.add(entry.getValue());
            }
            recommendedStreams.sort(new Comparator<Streams>() {
                @Override
                public int compare(Streams o1, Streams o2) {
                    return (int) (o2.getNoOfStreams() - o1.getNoOfStreams());//se sorteaza in ordine descrescatoare
                }
            });
        }
        for (int i = 0; i < recommendedStreams.size(); i++) {
            if (i >= 5) {
                break;
            }
            String name = streamers.get(recommendedStreams.get(i).getStreamerId()).getName();
            JsonObject stream = new JsonObject();
            long timestamp = recommendedStreams.get(i).getDateAdded();
            Date date = new Date(timestamp * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone("London"));
            String dateAdded = sdf.format(date);

            String length = ProiectPOO.convertLengthToMinutesAndSeconds(recommendedStreams.get(i).getLength());
            stream.addProperty("id", Integer.toString(recommendedStreams.get(i).getId()));
            stream.addProperty("name", recommendedStreams.get(i).getName());
            stream.addProperty("streamerName", name);
            stream.addProperty("noOfListenings", Long.toString(recommendedStreams.get(i).getNoOfStreams()));
            stream.addProperty("length", length);
            stream.addProperty("dateAdded", dateAdded);
            allStreams.add(stream);
        }
        System.out.println(allStreams);
    }
}

class recommend3surpriseStreamCommand implements Command {
    private String[] values;

    public recommend3surpriseStreamCommand(String[] values, HashMap<Integer, Streamers> streamers, HashMap<Integer, Streams> streams, HashMap<Integer, User> users) {
        this.values = values;
        this.streamers = streamers;
        this.streams = streams;
        this.users = users;
    }

    @Override
    public void execute() {
        try {
            recommend3surpriseStream(values);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static HashMap<Integer, Streamers> streamers = new HashMap<>();
    private static HashMap<Integer, Streams> streams = new HashMap<>();
    private static HashMap<Integer, User> users = new HashMap<>();

    public static void recommend3surpriseStream(String[] values) throws IOException {
        int userId = Integer.parseInt(values[0]);
        String type = values[2];
        int streamType = 0;
        if (type.equals("SONG")) {
            streamType = 1;
        }
        if (type.equals("PODCAST")) {
            streamType = 2;
        }
        if (type.equals("AUDIOBOOK")) {
            streamType = 3;
        }
        ArrayList<Streams> recommendedStreams = new ArrayList<>();
        ArrayList<Integer> streamersId = new ArrayList<>();
        User user = users.get(userId);
        JsonArray allStreams = new JsonArray();
        for (int i = 0; i < user.streams.size(); i++) {
            streamersId.add(streams.get(user.streams.get(i)).getStreamerId());
        }
        for (Map.Entry<Integer, Streams> entry : streams.entrySet()) {
            if (!streamersId.contains(entry.getValue().getStreamerId()) &&// au streamerul în lista de ID-uri de streamer
                    entry.getValue().getStreamType() == streamType//verifica tipul SONG=1 etc
                // )//daca streamul nu e ascutlat sa mi l puna in recommended streams
            ) {
                recommendedStreams.add(entry.getValue());
            }
        }

        recommendedStreams.sort((o1, o2) -> {
            long result = o2.getDateAdded() - (o1.getDateAdded());
            if (result == 0) {
                result = (int) (o2.getNoOfStreams() - o1.getNoOfStreams());
            }
            return (int) result;
        });


        for (int i = 0; i < 3 && i < recommendedStreams.size(); i++) {

            String length = ProiectPOO.convertLengthToMinutesAndSeconds(recommendedStreams.get(i).getLength());
            long timestamp = recommendedStreams.get(i).getDateAdded();
            Date date = new Date(timestamp * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone("London"));
            String dateAdded = sdf.format(date);

            String name = streamers.get(recommendedStreams.get(i).getStreamerId()).getName();
            JsonObject stream = new JsonObject();
            stream.addProperty("id", Integer.toString(recommendedStreams.get(i).getId()));
            stream.addProperty("name", recommendedStreams.get(i).getName());
            stream.addProperty("streamerName", name);
            stream.addProperty("noOfListenings", Long.toString(recommendedStreams.get(i).getNoOfStreams()));
            stream.addProperty("length", length);
            stream.addProperty("dateAdded", dateAdded);
            allStreams.add(stream);
        }
        System.out.println(allStreams);
    }

}








