package com.jpmorgan.event.service;

import com.jpmorgan.event.constant.EventConstants;
import com.jpmorgan.event.entity.Show;
import com.jpmorgan.event.helper.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {

    private EventService eventService;

    @BeforeEach
    void setUp() {
        eventService = new EventService();
    }

    @Test
    void setupShow_ValidConfig_Success() {
        eventService.reset();
        eventService.setupShow(1, 5, 10, 30);
        assertNotNull(eventService.getShows().get(1), "Show should be added");
        assertEquals(5, eventService.getShows().get(1).getNumRows(), "Number of rows should be 5");
        assertEquals(10, eventService.getShows().get(1).getSeatsPerRow(), "Seats per row should be 10");
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
        eventService.setupShow(showNumber, numRows, seatsPerRow, cancellationWindow);
        assertEquals(0, eventService.getShows().size(), "Show should not be added");
    }

    @Test
    void viewShow_ShowExists_DisplayDetails() {
        eventService.setupShow(1, 5, 10, 30);
        TestUtils.redirectSystemOut(() -> {
            eventService.viewShow(1);
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains("Show Number: 1"), "Show details should be displayed");
        });
    }

    @Test
    void viewShow_ShowNotExists_DisplayError() {
        TestUtils.redirectSystemOut(() -> {
            eventService.viewShow(1);
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains("Show not found"), "Error message should be displayed");
        });
    }

    @Test
    void changeCancellationWindow_ShowExists_Success() {
        eventService.setupShow(1, 5, 10, 30);
        eventService.changeCancellationWindow(1, 20);
        assertEquals(20, eventService.getShows().get(1).getCancellationWindow(), "Cancellation window should be changed");
    }

    @Test
    void changeCancellationWindow_ShowNotExists_DisplayError() {
        TestUtils.redirectSystemOut(() -> {
            eventService.changeCancellationWindow(1, 20);
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains("Show not found"), "Error message should be displayed");
        });
    }

    @Test
    void showAvailability_ShowExists_DisplayAvailability() {
        eventService.setupShow(1, 5, 10, 30);
        TestUtils.redirectSystemOut(() -> {
            eventService.showAvailability(1);
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains("Seat Availability for Show 1"), "Show availability should be displayed");
        });
    }

    @Test
    void showAvailability_ShowNotExists_DisplayError() {
        TestUtils.redirectSystemOut(() -> {
            eventService.showAvailability(1);
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains("Show not found"), "Error message should be displayed");
        });
    }

    @Test
    void bookTicket_ValidInput_Success() {
        eventService.setupShow(1, 5, 10, 30);
        eventService.showAvailability(1);  // Ensure seats are available

        TestUtils.redirectSystemOut(() -> {
            eventService.bookTicket(1, "1234567890", "A1,A2,A3");
            Show show = eventService.getShows().get(1);
            String ticket = show.getBookedTimestamp().keySet().stream().findFirst().orElse("");
            assertNotNull(show.getBookedSeats().get(ticket), "Ticket should be booked");
            assertEquals("1234567890", show.getBookedSeats().get(ticket).get("buyerPhone"),
                    "Buyer phone should match");
            assertEquals("A1,A2,A3", show.getBookedSeats().get(ticket).get("seatNumbers"),
                    "Seat numbers should match");
        });
    }

    @Test
    void bookTicket_NonexistentSeats_DisplayError() {
        eventService.setupShow(1, 5, 10, 30);
        eventService.showAvailability(1);  // ensure seats are available

        TestUtils.redirectSystemOut(() -> {
            eventService.bookTicket(1, "1234567890", "H1,H2,H3");
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains(EventConstants.SELECTED_SEATS_NOT_AVAILABLE), "Error message should be displayed");
        });
    }

    @Test
    void bookTicket_SeatsAlreadyBooked_DisplayError() {
        eventService.setupShow(1, 5, 10, 30);
        eventService.showAvailability(1);  // ensure seats are available

        // book some seats to make them unavailable
        eventService.bookTicket(1, "1234567890", "A1,A2,A3");

        // attempt to book the same seats again
        TestUtils.redirectSystemOut(() -> {
            eventService.bookTicket(1, "9876543210", "A1,A2,A3");
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains(EventConstants.SELECTED_SEATS_NOT_AVAILABLE), "Error message should be displayed");
        });
    }

    @Test
    void cancelTicket_ValidInput_Success() {
        eventService.setupShow(1, 5, 10, 30);
        eventService.bookTicket(1, "1234567890", "A1,A2,A3");
        String ticketNumber = eventService.getShows().get(1).getBookedSeats().keySet().iterator().next();
        eventService.cancelTicket(1, ticketNumber, "1234567890");
        assertNull(eventService.getShows().get(1).getBookedSeats().get(ticketNumber), "Ticket should be canceled");
    }

    @Test
    void cancelTicket_InvalidInput_DisplayError() {
        Show show = new Show();
        show.setBookedSeats(Map.of("ticket1", Map.of("buyerPhone", "123", "seatNumbers", "A1,A2")));
        show.setBookedTimestamp(Map.of("123", 120L));
        eventService.setShows(Map.of(1, show));

        TestUtils.redirectSystemOut(() -> {
            eventService.cancelTicket(1, "invalidTicketNumber", "1234567890");
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains("Invalid ticket cancellation"), "Error message should be displayed");
        });
    }

    @Test
    void cancelTicket_InvalidPhone_DisplayError() {
        Show show = new Show();
        show.setBookedSeats(Map.of("ticket1", Map.of("buyerPhone", "123", "seatNumbers", "A1,A2")));
        show.setBookedTimestamp(Map.of("ticket1", 120L));
        eventService.setShows(Map.of(1, show));

        TestUtils.redirectSystemOut(() -> {
            eventService.cancelTicket(1, "ticket1", "invalidPhoneNumber");
            String output = TestUtils.getSystemOut();
            assertTrue(output.contains(EventConstants.INVALID_TICKET_PHONE), "Error message should be displayed");
        });
    }

}