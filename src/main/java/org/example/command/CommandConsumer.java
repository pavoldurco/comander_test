package org.example.command;

import org.example.domain.User;
import org.example.exceptions.DatabaseUnavailableException;
import org.example.service.UserService;

import java.util.Queue;

public class CommandConsumer implements Runnable {
    private final Queue<Command> commandQueue;
    private final UserService userService;

    public CommandConsumer(Queue<Command> commandQueue, UserService userService) {
        this.commandQueue = commandQueue;
        this.userService = userService;
    }

    @Override
    public void run() {
        while (true) {
            Command command = commandQueue.poll();
            if (command != null) {
                switch (command.getType()) {
                    case ADD:
                        try {
                            userService.addUser((User) command.getData());
                        } catch (DatabaseUnavailableException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case DELETE:
                        try {
                            userService.deleteAllUsers();
                        } catch (DatabaseUnavailableException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case PRINT:
                        try {
                            userService.printAllUsers();
                        } catch (DatabaseUnavailableException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                }
            }
        }
    }
}