package com.jpmorgan.event.service;

import com.jpmorgan.event.constant.EventConstants;

public class BuyerService {

    public static void executeCommand(EventService eventService, String[] buyerCommand) {
        int showNumber;

        if (buyerCommand == null || buyerCommand.length < 1) {
            System.out.println(EventConstants.INVALID_BUYER_COMMAND);
            return;
        }

        switch (buyerCommand[0].toLowerCase()) {
            case "availability" -> {
                showNumber = Integer.parseInt(buyerCommand[1]);
                eventService.showAvailability(showNumber);
            }
            case "book" -> {
                showNumber = Integer.parseInt(buyerCommand[1]);
                String phone = buyerCommand[2];
                String seatNumbers = buyerCommand[3];
                eventService.bookTicket(showNumber, phone, seatNumbers);
            }
            case "cancel" -> {
                showNumber = Integer.parseInt(buyerCommand[1]);
                String ticketNumber = buyerCommand[2];
                String phone = buyerCommand[3];
                eventService.cancelTicket(showNumber, ticketNumber, phone);
            }
            default -> System.out.println(EventConstants.INVALID_BUYER_COMMAND);
        }
    }

}