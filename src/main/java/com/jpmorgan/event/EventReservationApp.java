package com.jpmorgan.event;

import com.jpmorgan.event.constant.EventConstants;
import com.jpmorgan.event.service.AdminService;
import com.jpmorgan.event.service.BuyerService;
import com.jpmorgan.event.service.EventService;

import java.util.Scanner;

public class EventReservationApp {

    public static void main(String[] args) {
        System.out.print(EventConstants.HEADER);
        initializeEventReservation();
    }

    private static void initializeEventReservation() {
        EventService eventService = new EventService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.print(EventConstants.LANDING_PROMPT);
            System.out.print("Enter number option: ");
            String userType = scanner.nextLine().toLowerCase();

            if (userType.equals("3")) {
                System.out.println(EventConstants.EXIT_COMMAND_PROMPT);
                break;
            }

            switch (userType) {
                case "1" -> processAdminCommand(scanner, eventService);
                case "2" -> processBuyerCommand(scanner, eventService);
                default -> System.out.println(EventConstants.INVALID_USER_TYPE);
            }
        }

        scanner.close();
    }

    private static void processAdminCommand(Scanner scanner, EventService eventService) {
        System.out.print(EventConstants.ADMIN_COMMAND_PROMPT);
        AdminService.executeCommand(eventService, scanner.nextLine().split("\\s+"));
    }

    private static void processBuyerCommand(Scanner scanner, EventService eventService) {
        System.out.print(EventConstants.BUYER_COMMAND_PROMPT);
        BuyerService.executeCommand(eventService, scanner.nextLine().split("\\s+"));
    }

}
