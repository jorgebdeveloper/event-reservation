package com.jpmorgan.event.service;

import com.jpmorgan.event.constant.EventConstants;

public class AdminService {

    public static void executeCommand(EventService eventService, String[] adminCommand) {
        int showNumber;

        if (adminCommand == null || adminCommand.length < 1) {
            System.out.println(EventConstants.INVALID_BUYER_COMMAND);
            return;
        }

        switch (adminCommand[0].toLowerCase()) {
            case "setup" -> {
                showNumber = Integer.parseInt(adminCommand[1]);
                int numRows = Integer.parseInt(adminCommand[2]);
                int seatsPerRow = Integer.parseInt(adminCommand[3]);
                int cancellationWindow = Integer.parseInt(adminCommand[4]);
                eventService.setupShow(showNumber, numRows, seatsPerRow, cancellationWindow);
            }
            case "view" -> {
                showNumber = Integer.parseInt(adminCommand[1]);
                eventService.viewShow(showNumber);
            }
            case "change-cancellation-duration" -> {
                showNumber = Integer.parseInt(adminCommand[1]);
                int newCancellationWindow = Integer.parseInt(adminCommand[2]);
                eventService.changeCancellationWindow(showNumber, newCancellationWindow);
            }
            default -> System.out.println(EventConstants.INVALID_ADMIN_COMMAND);
        }
    }
}
