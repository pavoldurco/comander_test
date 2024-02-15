package org.example;

import org.example.command.Command;
import org.example.command.CommandConsumer;
import org.example.command.CommandProducer;
import org.example.repository.UserRepository;
import org.example.service.UserService;

import java.util.concurrent.LinkedBlockingQueue;

public class ApplicationRunner {
    public void run() {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        LinkedBlockingQueue<Command> commandQueue = new LinkedBlockingQueue<>();

        CommandProducer producer = new CommandProducer(commandQueue, userService);
        CommandConsumer consumer = new CommandConsumer(commandQueue, userService);

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}