package com.jpmorgan.event.helper;

import com.jpmorgan.event.constant.EventConstants;
import com.jpmorgan.event.handler.CommandHandlerFactory;
import com.jpmorgan.event.service.EventService;

import java.util.Scanner;

public class EventInitializerHelper {
    public static void initializeEventReservation(Scanner scanner) {

        EventService eventService = new EventService();

        while (true) {
            System.out.println();
            System.out.print(EventConstants.LANDING_PROMPT);
            System.out.print("Enter number option: ");
            String option = scanner.nextLine();

            if (option.equals("3")) {
                System.out.println(EventConstants.EXIT_COMMAND_PROMPT);
                break;
            }
            processCommand(scanner, eventService, option);
        }

    }

    private static void processCommand(Scanner scanner, EventService eventService, String userType) {
        try {
            System.out.print(getCommandPrompt(userType));
            CommandHandlerFactory.createService(userType)
                                 .executeCommand(eventService, scanner.nextLine().split("\\s+"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getCommandPrompt(String userType) {
        return switch (userType) {
            case "1" -> EventConstants.ADMIN_COMMAND_PROMPT;
            case "2" -> EventConstants.BUYER_COMMAND_PROMPT;
            default -> throw new IllegalArgumentException(EventConstants.INVALID_USER_TYPE);
        };
    }
}
