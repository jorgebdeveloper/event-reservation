package com.jpmorgan.event.handler;

import com.jpmorgan.event.constant.EventConstants;
import com.jpmorgan.event.entity.EventParams;
import com.jpmorgan.event.service.EventService;

public class AdminHandler implements CommandHandler {

    @Override
    public void executeCommand(EventService eventService, String[] adminCommand) {

        if (adminCommand == null || adminCommand.length < 1) {
            System.out.println(EventConstants.INVALID_ADMIN_COMMAND);
            return;
        }

        switch (adminCommand[0].toLowerCase()) {
            case "setup" -> eventService.setupShow(buildParamsForSetup(adminCommand));
            case "view" -> eventService.viewShow(buildParamsForView(adminCommand));
            case "change-cancellation-duration" ->
                    eventService.changeCancellationWindow(buildParamsForCancellation(adminCommand));
            default -> System.out.println(EventConstants.INVALID_ADMIN_COMMAND);
        }
    }

    private EventParams buildParamsForSetup(String[] adminCommand) {
        return new EventParams.Builder()
                .showNumber(Integer.parseInt(adminCommand[1]))
                .numRows(Integer.parseInt(adminCommand[2]))
                .seatsPerRow(Integer.parseInt(adminCommand[3]))
                .cancellationWindow(Integer.parseInt(adminCommand[4]))
                .build();
    }
    private EventParams buildParamsForView(String[] adminCommand) {
        return new EventParams.Builder()
                .showNumber(Integer.parseInt(adminCommand[1]))
                .build();
    }

    private EventParams buildParamsForCancellation(String[] adminCommand) {
        return new EventParams.Builder()
                .showNumber(Integer.parseInt(adminCommand[1]))
                .newCancellationWindow(Integer.parseInt(adminCommand[2]))
                .build();
    }


}