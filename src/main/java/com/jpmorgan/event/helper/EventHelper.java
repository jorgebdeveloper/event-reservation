package com.jpmorgan.event.helper;

import com.jpmorgan.event.constant.EventConstants;
import com.jpmorgan.event.entity.Show;

import java.util.Arrays;
import java.util.Map;

public class EventHelper {

    public static void displayShowSetupDetails(Map<Integer, Show> shows) {
        System.out.println("\nShows saved so far:");
        String headerFormat = "%-15s %-15s %-15s %-20s%n";
        String rowFormat = "%-15d %-15d %-15d %-20d%n";

        System.out.printf(headerFormat, "Show Number", "Num Rows", "Seats per Row", "Cancellation Window");
        shows.forEach((key, savedShow) -> System.out.printf(
                rowFormat, key, savedShow.getNumRows(), savedShow.getSeatsPerRow(), savedShow.getCancellationWindow()));
        System.out.println();
    }

    public static boolean areShowParamsValid(Map<Integer, Show> shows, int showNumber, int numRows, int seatsPerRow) {
        return showNumber > 0
                && !shows.containsKey(showNumber)
                && (numRows > 0 && numRows <= EventConstants.MAX_ROWS)
                && (seatsPerRow > 0 && seatsPerRow <= EventConstants.MAX_SEATS_PER_ROW);
    }

    public static boolean isPhoneNumberBooked(Show show, String phone) {
        return show.getBookedSeats().values().stream().anyMatch(details -> details.get("buyerPhone").equals(phone));
    }

    public static boolean areSeatsAvailable(Show show, String[] requestedSeats) {
        return Arrays.stream(requestedSeats)
                     .allMatch(seat -> show.getSeatsAvailability().getOrDefault(seat, false));
    }

}
