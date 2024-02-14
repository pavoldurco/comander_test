package org.example.command;

public class Command {
    private CommandType type;
    private Object data;

    public Command(CommandType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public CommandType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}