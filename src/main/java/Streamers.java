interface Streamers {//am folosit pentru Streamer design pattern-ul factory

    //interfata are urmatoarele 3 metode
    int getId();

    String getName();

    String getType();


}

//fiecare clasa implementeaza interfata Streamer, cu proprietatile id si name initializate prin constructor
class Musician implements Streamers {
    private int id;
    private String name;


    public Musician(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() { //returneaza un string ce identifica tipul streamer-ului
        return "Musician";
    }
}

class PodcastHost implements Streamers {
    private int id;
    private String name;

    public PodcastHost(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {//returneaza un string ce identifica tipul streamer-ului.
        return "Podcast Host";
    }
}

class AudiobookAuthor implements Streamers {
    private int id;
    private String name;

    public AudiobookAuthor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {//returneaza un string ce identifica tipul streamer-ului.
        return "Audiobook Author";
    }
}

class StreamerFactory {
    private static StreamerFactory instance;

    private StreamerFactory() {
    }

    public static StreamerFactory getInstance() {
        if (instance == null) {
            instance = new StreamerFactory();
        }
        return instance;
    }
    //creeaza un obiect "Streamer" in functie de tipul streamer-ului

    public static Streamers createStreamer(int streamerType, int id, String name) {
        switch (streamerType) {
            case 1:
                return new Musician(id, name);
            case 2:
                return new PodcastHost(id, name);
            case 3:
                return new AudiobookAuthor(id, name);
            default:
                return null;//daca "streamerType" nu corespunde niciunei valori,returnam null.
        }
    }
}