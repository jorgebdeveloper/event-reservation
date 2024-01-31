package com.jpmorgan.event.handler;

import com.jpmorgan.event.constant.EventConstants;
import com.jpmorgan.event.entity.EventParams;
import com.jpmorgan.event.service.EventService;

public class BuyerHandler implements CommandHandler {

    @Override
    public void executeCommand(EventService eventService, String[] buyerCommand) {

        if (buyerCommand == null || buyerCommand.length < 1) {
            System.out.println(EventConstants.INVALID_BUYER_COMMAND);
            return;
        }

        switch (buyerCommand[0].toLowerCase()) {
            case "availability" -> eventService.showAvailability(buildParamsForAvailability(buyerCommand));
            case "book" -> eventService.bookTicket(buildParamsForBook(buyerCommand));
            case "cancel" -> eventService.cancelTicket(buildParamsForCancel(buyerCommand));
            default -> System.out.println(EventConstants.INVALID_BUYER_COMMAND);
        }
    }

    private EventParams buildParamsForAvailability(String[] buyerCommand) {
        return new EventParams.Builder()
                .showNumber(Integer.parseInt(buyerCommand[1]))
                .build();
    }

    private EventParams buildParamsForBook(String[] buyerCommand) {
        return new EventParams.Builder()
                .showNumber(Integer.parseInt(buyerCommand[1]))
                .phone(buyerCommand[2])
                .seats(buyerCommand[3])
                .build();
    }

    private EventParams buildParamsForCancel(String[] buyerCommand) {
        return new EventParams.Builder()
                .showNumber(Integer.parseInt(buyerCommand[1]))
                .ticketNumber(buyerCommand[2])
                .phone(buyerCommand[3])
                .build();
    }
}