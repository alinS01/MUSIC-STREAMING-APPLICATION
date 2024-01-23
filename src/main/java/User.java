import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class User {
    int id;
    String name;
    List<Integer> streams;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", streams=" + streams +
                '}';
    }


    public User(int id, String name, List<Integer> streams) {
        this.id = id;
        this.name = name;
        this.streams = streams;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getStreams() {
        return streams;
    }


}
