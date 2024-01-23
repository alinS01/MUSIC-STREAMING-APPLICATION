interface Streams {
    int getId();

    int getStreamGenre();

    String getName();

    long getNoOfStreams();

    int getStreamerId();

    long getLength();

    long getDateAdded();

    long incrementNoOfStreams();

    int getStreamType();

}

class MusicStream implements Streams {
    private final int streamType;
    private final int id;
    private final int streamGenre;
    private long noOfStreams;

    public int getStreamType() {
        return streamType;
    }

    public int getId() {
        return id;
    }

    public int getStreamGenre() {
        return streamGenre;
    }

    public long getNoOfStreams() {
        return noOfStreams;
    }

    public long incrementNoOfStreams() {
        return noOfStreams++;
    }

    public int getStreamerId() {
        return streamerId;
    }

    public long getLength() {
        return length;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public String getName() {
        return name;
    }

    private final int streamerId;
    private final long length;
    private final long dateAdded;
    private final String name;

    MusicStream(int streamType, int id, int streamGenre, long noOfStreams, int streamerId, long length, long dateAdded, String name) {
        this.streamType = streamType;
        this.id = id;
        this.streamGenre = streamGenre;
        this.noOfStreams = noOfStreams;
        this.streamerId = streamerId;
        this.length = length;
        this.dateAdded = dateAdded;
        this.name = name;
    }
}

class PodcastStream implements Streams {
    private final int streamType;
    private final int id;

    public int getStreamType() {
        return streamType;
    }

    private final int streamGenre;

    public int getId() {
        return id;
    }

    public int getStreamGenre() {
        return streamGenre;
    }

    public long getNoOfStreams() {
        return noOfStreams;
    }

    public long incrementNoOfStreams() {
        return noOfStreams++;
    }

    public int getStreamerId() {
        return streamerId;
    }

    public long getLength() {
        return length;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public String getName() {
        return name;
    }

    private long noOfStreams;
    private final int streamerId;
    private final long length;
    private final long dateAdded;
    private final String name;

    PodcastStream(int streamType, int id, int streamGenre, long noOfStreams, int streamerId, long length, long dateAdded, String name) {
        this.streamType = streamType;
        this.id = id;
        this.streamGenre = streamGenre;
        this.noOfStreams = noOfStreams;
        this.streamerId = streamerId;
        this.length = length;
        this.dateAdded = dateAdded;
        this.name = name;
    }
}

class AudiobookStream implements Streams {
    private final int streamType;
    private final int id;

    public int getStreamType() {
        return streamType;
    }

    private final int streamGenre;

    public int getId() {
        return id;
    }

    public int getStreamGenre() {
        return streamGenre;
    }

    public long getNoOfStreams() {
        return noOfStreams;
    }

    public long incrementNoOfStreams() {
        return noOfStreams++;
    }

    public int getStreamerId() {
        return streamerId;
    }

    public long getLength() {
        return length;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public String getName() {
        return name;
    }

    private long noOfStreams;
    private final int streamerId;
    private final long length;
    private final long dateAdded;
    private final String name;

    AudiobookStream(int streamType, int id, int streamGenre, long noOfStreams, int streamerId, long length, long dateAdded, String name) {
        this.streamType = streamType;
        this.id = id;
        this.streamGenre = streamGenre;
        this.noOfStreams = noOfStreams;
        this.streamerId = streamerId;
        this.length = length;
        this.dateAdded = dateAdded;
        this.name = name;
    }
}

class StreamFactory {
    private static StreamFactory instance;

    private StreamFactory() {
    }

    public static StreamFactory getInstance() {
        if (instance == null) {
            instance = new StreamFactory();
        }
        return instance;
    }

    public static Streams createStream(int streamType, int id, int streamGenre, long noOfStreams, int streamerId, long length, long dateAdded, String name) {
        switch (streamType) {
            case 1:
                return new MusicStream(streamType, id, streamGenre, noOfStreams, streamerId, length, dateAdded, name);
            case 2:
                return new PodcastStream(streamType, id, streamGenre, noOfStreams, streamerId, length, dateAdded, name);
            case 3:
                return new AudiobookStream(streamType, id, streamGenre, noOfStreams, streamerId, length, dateAdded, name);
            default:
                throw new IllegalArgumentException("Invalid stream type");
        }
    }
}
