package com.jpmorgan.event.handler;

import com.jpmorgan.event.service.EventService;

public interface CommandHandler {

    void executeCommand(EventService eventService, String[] command);

}