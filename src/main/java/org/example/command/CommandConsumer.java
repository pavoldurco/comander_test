package org.example.command;

import org.example.domain.User;
import org.example.service.UserService;

import java.util.Queue;

public class CommandConsumer implements Runnable {
    private Queue<Command> commandQueue;
    private UserService userService;

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
                        userService.addUser((User) command.getData());
                        break;
                    case DELETE:
                        userService.deleteAllUsers();
                        break;
                    case PRINT:
                        userService.printAllUsers();
                        break;
                }
            }
        }
    }
}