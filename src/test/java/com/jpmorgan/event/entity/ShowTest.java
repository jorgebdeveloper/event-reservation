package com.jpmorgan.event.entity;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShowTest {

    @Test
    void view_NoSeatsBooked_DisplayNoSeatsMessage() {
        Show show = new Show(1, 5, 10);
        show.view(); // should not throw an exception
    }

    @Test
    void view_SomeSeatsBooked_DisplaySeatsInformation() {
        Show show = new Show(1, 5, 10);
        Map<String, String> bookingDetails = Map.of("buyerPhone", "1234567890", "seatNumbers", "A1,A2,A3");
        show.getBookedSeats().put("ticket1", bookingDetails);

        assertDoesNotThrow(show::view);
    }

    @Test
    void showAvailability_SeatsAvailable_DisplayAvailabilityMatrix() {
        Show show = new Show(1, 5, 10);
        show.showAvailability(); // This should not throw an exception
    }

    @Test
    void isValidCancellation_ValidCancellation_ReturnsTrue() {
        Show show = new Show(1, 5, 10);
        String ticketNumber = show.generateTicketNumber();
        Map<String, String> bookingDetails = Map.of("buyerPhone", "1234567890", "seatNumbers", "A1,A2,A3");
        show.getBookedSeats().put(ticketNumber, bookingDetails);
        show.getBookedTimestamp().put(ticketNumber, System.currentTimeMillis());

        assertTrue(show.isValidCancellation(ticketNumber, "1234567890"));
    }

    @Test
    void isValidCancellation_InvalidTicket_ReturnsFalse() {
        Show show = new Show(1, 5, 10);
        assertFalse(show.isValidCancellation("invalidTicketNumber", "1234567890"));
    }

    @Test
    void isValidCancellation_InvalidPhone_ReturnsFalse() {
        Show show = new Show(1, 5, 10);
        String ticketNumber = show.generateTicketNumber();
        Map<String, String> bookingDetails = Map.of("buyerPhone", "1234567890", "seatNumbers", "A1,A2,A3");
        show.getBookedSeats().put(ticketNumber, bookingDetails);
        show.getBookedTimestamp().put(ticketNumber, System.currentTimeMillis());

        assertFalse(show.isValidCancellation(ticketNumber, "invalidPhoneNumber"));
    }

    @Test
    void generateTicketNumber_UniqueTicketNumbers() {
        Show show = new Show(1, 5, 10);
        String ticketNumber1 = show.generateTicketNumber();
        String ticketNumber2 = show.generateTicketNumber();

        assertNotEquals(ticketNumber1, ticketNumber2);
    }
}