
package by.bsuir.serko.bettingapp.command;


public class CommandFactory {

    public Command createCommand(String commandName) {
        Command command;
        CommandType type = CommandType.forValue(commandName);
        if(type != null) {
            command = type.getCommand();
        } else {
            command = new EmptyCommand();
        }
        return command;
    }
    
}