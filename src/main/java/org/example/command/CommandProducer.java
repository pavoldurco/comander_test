package org.example.command;

import org.example.domain.User;
import org.example.service.UserService;

import java.util.Queue;
import java.util.Scanner;

public class CommandProducer implements Runnable {
    private final Queue<Command> commandQueue;
    private final Scanner scanner;

    public CommandProducer(Queue<Command> commandQueue, UserService userService) {
        this.commandQueue = commandQueue;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Enter command (add, delete, print, exit):");
            String command = scanner.nextLine();

            switch (command) {
                case "add":
                    User user = new User();
                    String userGuid;
                    do {
                        System.out.println("Enter user guid:");
                        userGuid = scanner.nextLine();
                        if (userGuid.isEmpty()) {
                            System.out.println("User guid cannot be empty.");
                        }
                    } while (userGuid.isEmpty());
                    user.setUserGuid(userGuid);

                    String userName;
                    do {
                        System.out.println("Enter user name:");
                        userName = scanner.nextLine();
                        if (userName.isEmpty()) {
                            System.out.println("User name cannot be empty.");
                        }
                    } while (userName.isEmpty());
                    user.setUserName(userName);

                    commandQueue.add(new Command(CommandType.ADD, user));
                    break;
                case "delete":
                    commandQueue.add(new Command(CommandType.DELETE, null));
                    break;
                case "print":
                    commandQueue.add(new Command(CommandType.PRINT, null));
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }
}