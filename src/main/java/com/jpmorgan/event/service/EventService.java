package com.jpmorgan.event.service;

import com.jpmorgan.event.constant.EventConstants;
import com.jpmorgan.event.entity.Show;
import com.jpmorgan.event.helper.EventHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EventService {

    private Map<Integer, Show> shows;

    public EventService() {
        this.shows = new HashMap<>();
    }

    public Map<Integer, Show> getShows() {
        return shows;
    }

    public void setShows(Map<Integer, Show> shows) {
        this.shows = shows;
    }

    public void reset() {
        this.shows.clear();
    }

    public void setupShow(int showNumber, int numRows, int seatsPerRow, int cancellationWindow) {

        if (!EventHelper.areShowParamsValid(shows, showNumber, numRows, seatsPerRow)) {
            System.out.println(EventConstants.INVALID_SETUP_PARAMETERS);
            return;
        }

        Show show = new Show(showNumber, numRows, seatsPerRow);
        show.setCancellationWindow(cancellationWindow);
        shows.put(showNumber, show);
        System.out.println(EventConstants.SHOW_SETUP_SUCCESS);

        EventHelper.displayShowSetupDetails(shows);
    }

    public void viewShow(int showNumber) {
        if (shows.containsKey(showNumber)) {
            shows.get(showNumber).view();
        } else {
            System.out.println(EventConstants.SHOW_NOT_FOUND);
        }
    }

    public void changeCancellationWindow(int showNumber, int newCancellationWindow) {
        if (!shows.containsKey(showNumber)) {
            System.out.println(EventConstants.SHOW_NOT_FOUND);
            System.out.println("Modification of cancellation window");
            return;
        }

        Show show = shows.get(showNumber);
        int oldCancellationWindow = show.getCancellationWindow();
        show.setCancellationWindow(newCancellationWindow);

        System.out.println(String.format(
                EventConstants.CANCELLATION_WINDOW_CHANGED_SUCCESS,
                showNumber,
                oldCancellationWindow,
                newCancellationWindow
        ));
    }

    public void showAvailability(int showNumber) {
        if (shows.containsKey(showNumber)) {
            shows.get(showNumber).showAvailability();
        } else {
            System.out.println(EventConstants.SHOW_NOT_FOUND);
        }
    }

    public void bookTicket(int showNumber, String phone, String seats) {
        Show show = shows.getOrDefault(showNumber, null);

        if (show == null) {
            System.out.println(EventConstants.SHOW_NOT_FOUND);
            return;
        }

        if (EventHelper.isPhoneNumberBooked(show, phone)) {
            System.out.println(EventConstants.ONE_BOOKING_PER_PHONE);
            return;
        }

        String[] requestedSeats = seats.split(",");
        if (!EventHelper.areSeatsAvailable(show, requestedSeats)) {
            System.out.println(EventConstants.SELECTED_SEATS_NOT_AVAILABLE);
            return;
        }

        String ticketNumber = show.generateTicketNumber();
        Arrays.stream(requestedSeats).forEach(seat -> show.getSeatsAvailability().put(seat, false));

        Map<String, String> bookingDetails = new HashMap<>();
        bookingDetails.put("buyerPhone", phone);
        bookingDetails.put("seatNumbers", String.join(",", seats));
        show.getBookedSeats().put(ticketNumber, bookingDetails);

        show.getBookedTimestamp().put(ticketNumber, System.currentTimeMillis());
        System.out.println(String.format(EventConstants.TICKET_BOOKED_SUCCESS, ticketNumber));
    }

    public void cancelTicket(int showNumber, String ticketNumber, String phone) {
        if (!shows.containsKey(showNumber)) {
            System.out.println(EventConstants.SHOW_NOT_FOUND);
            return;
        }

        Show show = shows.get(showNumber);

        if (show == null || !show.isValidCancellation(ticketNumber, phone)) {
            System.out.println("Invalid ticket cancellation");
            return;
        }

        Arrays.stream(show.getBookedSeats().get(ticketNumber).get("seatNumbers").split(","))
                .forEach(seat -> show.getSeatsAvailability().put(seat, true));

        show.getBookedSeats().remove(ticketNumber);
        show.getBookedTimestamp().remove(ticketNumber);
        System.out.println(EventConstants.TICKET_CANCELED_SUCCESS);
    }

}
