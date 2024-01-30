package com.jpmorgan.event.service;

import com.jpmorgan.event.constant.EventConstants;
import com.jpmorgan.event.entity.EventParams;
import com.jpmorgan.event.entity.Show;
import com.jpmorgan.event.entity.Ticket;
import com.jpmorgan.event.entity.Venue;
import com.jpmorgan.event.helper.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {

    private EventService eventService = new EventService();
    private EventParams eventParams;

    @BeforeEach
    void setUp() {
        eventService.clearShows();
        eventParams = new EventParams.Builder()
                            .showNumber(1).numRows(5).seatsPerRow(10)
                            .cancellationWindow(120).phone("1234567890").seats("A1,A2,A3")
                            .build();
    }

    @Test
    void setupShow_ValidConfig_Success() {
        eventService.setupShow(eventParams);
        assertNotNull(eventService.getShows().get(1), "Show should be added");
        assertEquals(5, eventService.getShows().get(1).getVenue().getRows(), "Number of rows should be 5");
        assertEquals(10, eventService.getShows().get(1).getVenue().getSeatsPerRow(), "Seats per row should be 10");
    }

    @ParameterizedTest
    @CsvSource({
            "0, 4, 10, 30",
            "1, 0, 10, 30",
            "2, 27, 10, 30",
            "3, 4, 0, 30",
            "4, 4, 11, 30"
    })
    void setupShow_InvalidConfig_DisplayError(int showNumber, int numRows, int seatsPerRow, int cancellationWindow) {
        EventParams params = new EventParams.Builder()
                                    .showNumber(showNumber)
                                    .numRows(numRows)
                                    .seatsPerRow(seatsPerRow)
                                    .cancellationWindow(cancellationWindow)
                                    .build();
        eventService.setupShow(params);
        assertEquals(0, eventService.getShows().size(), "Show should not be added");
    }

    @Test
    void viewShow_ShowExists_DisplayDetails() {
        eventService.addShows(1, new Show(1, new Venue(4, 7), 120));
        TestUtils.redirectSystemOut(() -> {
            eventService.viewShow(eventParams);
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains("Show Number: 1"), "Show details should be displayed");
        });
    }

    @Test
    void viewShow_ShowNotExists_DisplayError() {
        TestUtils.redirectSystemOut(() -> {
            eventService.viewShow(new EventParams.Builder().showNumber(2).build());
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains("Show not found"), "Error message should be displayed");
        });
    }

    @Test
    void changeCancellationWindow_ShowExists_Success() {
        eventService.addShows(1, new Show(1, new Venue(4, 7), 120));
        eventService.changeCancellationWindow(new EventParams.Builder().showNumber(1).newCancellationWindow(20).build());
        assertEquals(20, eventService.getShows().get(1).getCancellationWindow(), "Cancellation window should be changed");
    }

    @Test
    void changeCancellationWindow_ShowNotExists_DisplayError() {
        TestUtils.redirectSystemOut(() -> {
            eventService.changeCancellationWindow(new EventParams.Builder().showNumber(2).newCancellationWindow(20).build());
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains("Show not found"), "Error message should be displayed");
        });
    }

    @Test
    void showAvailability_ShowExists_DisplayAvailability() {
        eventService.addShows(1, new Show(1, new Venue(4, 7), 120));
        TestUtils.redirectSystemOut(() -> {
            eventService.showAvailability(eventParams);
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains("Available seats:"), "Show availability should be displayed");
        });
    }

    @Test
    void showAvailability_ShowNotExists_DisplayError() {
        TestUtils.redirectSystemOut(() -> {
            eventService.showAvailability(new EventParams.Builder().showNumber(2).build());
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains("Show not found"), "Error message should be displayed");
        });
    }
//
    @Test
    void bookTicket_ValidInput_Success() {
        eventService.addShows(1, new Show(1, new Venue(4, 7), 120));

        eventService.bookTicket(eventParams);
        Show show = eventService.getShows().get(1);
        Ticket ticket = show.getTicketMap().values().stream().findAny().get();
        assertNotNull(ticket, "Ticket should be booked");
        assertEquals(eventParams.getPhone(), ticket.getPhoneNumber(), "Buyer phone should match");
        assertEquals(eventParams.getSeats(), ticket.getSeats(), "Seat numbers should match");
    }

    @Test
    void bookTicket_NonexistentSeats_DisplayError() {
        Show show = new Show(1, new Venue(4, 7), 120);
        eventService.addShows(1, show);

        EventParams params = eventParams;
        params.setSeats("Z1");

        TestUtils.redirectSystemOut(() -> {
            eventService.bookTicket(params);
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains(EventConstants.SELECTED_SEATS_NOT_AVAILABLE), "Error message should be displayed");
        });
    }

    @Test
    void bookTicket_SeatsAlreadyBooked_DisplayError() {
        Show show = new Show(1, new Venue(4, 7), 120);
        eventService.addShows(1, show);

        // book some seats to make them unavailable
        eventService.bookTicket(eventParams);

        EventParams params = eventParams;
        params.setPhone("987");

        // attempt to book the same seats again
        TestUtils.redirectSystemOut(() -> {
            eventService.bookTicket(params);
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains(EventConstants.SELECTED_SEATS_NOT_AVAILABLE), "Error message should be displayed");
        });
    }
//
    @Test
    void cancelTicket_ValidInput_Success() {
        Show show = new Show(1, new Venue(4, 7), 120);
        show.addTicket("123", new Ticket("123", "A1,A2,A3", "1234567890"));
        eventService.addShows(1, show);

        EventParams params = eventParams;
        params.setTicketNumber("123");

        eventService.cancelTicket(eventParams);
        Show showResult = eventService.getShows().get(1);
        assertEquals(0, showResult.getTicketMap().size(), "Ticket should be canceled");
        assertTrue(showResult.getVenue().isSeatAvailable("A1"), "Seat should not be available");
    }
//
    @Test
    void cancelTicket_InvalidInput_DisplayError() {
        Show show = new Show(1, new Venue(4, 7), 120);
        show.addTicket("123", new Ticket("123", "A1,A2,A3", "1234567890"));
        eventService.addShows(1, show);

        TestUtils.redirectSystemOut(() -> {
            eventService.cancelTicket(eventParams);
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains(EventConstants.TICKET_CANCELLATION_DENIED), "Error message should be displayed");
        });
    }

    @Test
    void cancelTicket_InvalidPhone_DisplayError() {
        Show show = new Show(1, new Venue(4, 7), 120);
        show.addTicket("123", new Ticket("123", "A1,A2,A3", "987"));
        eventService.addShows(1, show);

        TestUtils.redirectSystemOut(() -> {
            eventService.cancelTicket(eventParams);
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains(EventConstants.INVALID_TICKET_PHONE), "Error message should be displayed");
        });
    }

}