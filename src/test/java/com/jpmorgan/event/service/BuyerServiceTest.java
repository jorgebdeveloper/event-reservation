package com.jpmorgan.event.service;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BuyerServiceTest {

    @Test
    void testExecuteCommandWithAvailability() {
        EventService mockEventService = mock(EventService.class);
        BuyerService.executeCommand(mockEventService, new String[]{"availability", "1"});
        verify(mockEventService).showAvailability(1);
    }

    @Test
    void testExecuteCommandWithBook() {
        EventService mockEventService = mock(EventService.class);
        BuyerService.executeCommand(mockEventService, new String[]{"book", "1", "1234567890", "A1,A2"});
        verify(mockEventService).bookTicket(1, "1234567890", "A1,A2");
    }

    @Test
    void testExecuteCommandWithCancel() {
        EventService mockEventService = mock(EventService.class);
        BuyerService.executeCommand(mockEventService, new String[]{"cancel", "1", "ABC123", "1234567890"});
        verify(mockEventService).cancelTicket(1, "ABC123", "1234567890");
    }

    @Test
    void testExecuteCommandWithInvalidCommand() {
        EventService mockEventService = mock(EventService.class);
        BuyerService.executeCommand(mockEventService, new String[]{"invalid-command"});
    }

    @Test
    void testExecuteCommandWithNullCommand() {
        EventService mockEventService = mock(EventService.class);
        BuyerService.executeCommand(mockEventService, null);
    }

    @Test
    void testExecuteCommandWithEmptyCommand() {
        EventService mockEventService = mock(EventService.class);
        BuyerService.executeCommand(mockEventService, new String[]{});
    }

}
