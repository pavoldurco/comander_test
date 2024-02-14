package org.example.command;

import org.example.domain.User;
import org.example.service.UserService;

import java.util.Queue;
import java.util.Scanner;

public class CommandProducer implements Runnable {
    private Queue<Command> commandQueue;
    private UserService userService;
    private Scanner scanner;

    public CommandProducer(Queue<Command> commandQueue, UserService userService) {
        this.commandQueue = commandQueue;
        this.userService = userService;
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
                    System.out.println("Enter user guid:");
                    user.setUserGuid(scanner.nextLine());
                    System.out.println("Enter user name:");
                    user.setUserName(scanner.nextLine());
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