package com.jpmorgan.event.service;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AdminServiceTest {

    @Test
    void testExecuteCommandWithSetup() {
        EventService mockEventService = mock(EventService.class);
        AdminService.executeCommand(mockEventService, new String[]{"setup", "1", "10", "5", "15"});
        verify(mockEventService).setupShow(1, 10, 5, 15);
    }

    @Test
    void testExecuteCommandWithView() {
        EventService mockEventService = mock(EventService.class);
        AdminService.executeCommand(mockEventService, new String[]{"view", "1"});
        verify(mockEventService).viewShow(1);
    }

    @Test
    void testExecuteCommandWithChangeCancellationDuration() {
        EventService mockEventService = mock(EventService.class);
        AdminService.executeCommand(mockEventService, new String[]{"change-cancellation-duration", "1", "20"});
        verify(mockEventService).changeCancellationWindow(1, 20);
    }

    @Test
    void testExecuteCommandWithInvalidCommand() {
        EventService mockEventService = mock(EventService.class);
        AdminService.executeCommand(mockEventService, new String[]{"invalid-command"});
    }

    @Test
    void testExecuteCommandWithNullCommand() {
        EventService mockEventService = mock(EventService.class);
        AdminService.executeCommand(mockEventService, null);
    }

    @Test
    void testExecuteCommandWithEmptyCommand() {
        EventService mockEventService = mock(EventService.class);
        AdminService.executeCommand(mockEventService, new String[]{});
    }

}
