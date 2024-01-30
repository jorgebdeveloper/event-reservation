package com.jpmorgan.event.helper;

import com.jpmorgan.event.constant.EventConstants;
import com.jpmorgan.event.entity.*;

import java.util.Arrays;
import java.util.Map;

public class EventServiceHelper {

    public static void displayShowSetupDetails(Map<Integer, Show> shows) {
        System.out.println("\nShows saved so far:");
        String headerFormat = "%-15s %-15s %-15s %-20s%n";
        String rowFormat = "%-15d %-15d %-15d %-20d%n";

        System.out.printf(headerFormat, "Show Number", "Num Rows", "Seats per Row", "Cancellation Window");
        shows.forEach((key, savedShow) -> {
            Venue venue = savedShow.getVenue();
            System.out.printf(rowFormat,
                    key, venue.getRows(), venue.getSeatsPerRow(), savedShow.getCancellationWindow());
        });
        System.out.println();
    }

    public static boolean areShowParamsValid(Map<Integer, Show> shows, EventParams eventParams) {
        return eventParams.getShowNumber() > 0
                && !shows.containsKey(eventParams.getShowNumber())
                && (eventParams.getNumRows() > 0 && eventParams.getNumRows() <= EventConstants.MAX_ROWS)
                && (eventParams.getSeatsPerRow() > 0 && eventParams.getSeatsPerRow() <= EventConstants.MAX_SEATS_PER_ROW);
    }

    public static boolean isPhoneNumberBooked(Show show, String phone) {
        return show.getTicketMap()
                .values().stream()
                .map(Ticket::getPhoneNumber)
                .anyMatch(e -> e.equals(phone));
    }

    public static boolean areSeatsAvailable(Venue venue, String[] requestedSeats) {
        return Arrays.stream(requestedSeats).allMatch(venue::isSeatAvailable);
    }

}
