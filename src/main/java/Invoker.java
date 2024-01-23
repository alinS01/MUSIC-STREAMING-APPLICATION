import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Invoker {
    private List<Command> commands = new ArrayList<>();

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void executeCommands() throws IOException {
        for (Command command : commands) {
            command.execute();
        }
    }
}
