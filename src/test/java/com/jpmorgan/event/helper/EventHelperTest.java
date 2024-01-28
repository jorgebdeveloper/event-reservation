package com.jpmorgan.event.helper;

import com.jpmorgan.event.entity.Show;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventHelperTest {

    @Test
    void areShowParamsValid_ValidParams_ReturnsTrue() {
        assertTrue(EventHelper.areShowParamsValid(new HashMap<>(), 1, 5, 10));
    }

    @Test
    void isPhoneNumberBooked_PhoneNumberBooked() {
        Show show = new Show(1, 5, 10);
        Map<String, String> bookingDetails = new HashMap<>();
        bookingDetails.put("buyerPhone", "1234567890");
        bookingDetails.put("seatNumbers", "A1,A2,A3");
        show.getBookedSeats().put("ticket1", bookingDetails);

        assertTrue(EventHelper.isPhoneNumberBooked(show, "1234567890"));
    }

    @Test
    void isPhoneNumberBooked_PhoneNumberNotBooked() {
        Show show = new Show(1, 5, 10);
        Map<String, String> bookingDetails = new HashMap<>();
        bookingDetails.put("buyerPhone", "9876543210");
        bookingDetails.put("seatNumbers", "B1,B2,B3");
        show.getBookedSeats().put("ticket1", bookingDetails);

        assertFalse(EventHelper.isPhoneNumberBooked(show, "1234567890"));
    }

    @Test
    void areSeatsAvailable_AllSeatsAvailable() {
        Show show = new Show(1, 5, 10);
        show.getSeatsAvailability().put("A1", true);
        show.getSeatsAvailability().put("A2", true);
        show.getSeatsAvailability().put("A3", true);

        assertTrue(EventHelper.areSeatsAvailable(show, new String[]{"A1", "A2", "A3"}));
    }

    @Test
    void areSeatsAvailable_SomeSeatsUnavailable() {
        Show show = new Show(1, 5, 10);
        show.getSeatsAvailability().put("A1", true);
        show.getSeatsAvailability().put("A2", false);
        show.getSeatsAvailability().put("A3", true);

        assertFalse(EventHelper.areSeatsAvailable(show, new String[]{"A1", "A2", "A3"}));
    }
}
