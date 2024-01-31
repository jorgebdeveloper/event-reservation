package com.jpmorgan.event.service;

import com.jpmorgan.event.repository.ShowRepository;
import com.jpmorgan.event.constant.EventConstants;
import com.jpmorgan.event.entity.EventParams;
import com.jpmorgan.event.entity.Show;
import com.jpmorgan.event.entity.Ticket;
import com.jpmorgan.event.entity.Venue;
import com.jpmorgan.event.helper.EventServiceHelper;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class EventService {

    private final ShowRepository showRepository = ShowRepository.getInstance();

    Map<Integer, Show> getShows() {
        return this.showRepository.getShows();
    }

    void addShows(Integer key, Show show) {
        this.showRepository.addShow(show);
    }

    void clearShows() {
        this.showRepository.clearShows();
    }

    public void setupShow(EventParams params) {

        if (!EventServiceHelper.areShowParamsValid(getShows(), params)) {
            System.out.println(EventConstants.INVALID_SETUP_PARAMETERS);
            return;
        }

        Show show = new Show(params.getShowNumber(),
                                new Venue(params.getNumRows(),
                                params.getSeatsPerRow()),
                                params.getCancellationWindow());
        show.setCancellationWindow(params.getCancellationWindow());
        showRepository.addShow(show);
        System.out.println(EventConstants.SHOW_SETUP_SUCCESS);

        EventServiceHelper.displayShowSetupDetails(getShows());
    }

    public void viewShow(EventParams params) {
        Map<Integer, Show> shows = getShows();
        if (!shows.containsKey(params.getShowNumber())) {
            System.out.println(EventConstants.SHOW_NOT_FOUND);
            return;
        }
        shows.get(params.getShowNumber()).view();
    }

    public void changeCancellationWindow(EventParams params) {
        Map<Integer, Show> shows = getShows();
        if (!shows.containsKey(params.getShowNumber())) {
            System.out.println(EventConstants.SHOW_NOT_FOUND);
            return;
        }

        System.out.println("Modification of cancellation window");

        Show show = shows.get(params.getShowNumber());
        long oldCancellationWindow = show.getCancellationWindow();
        show.setCancellationWindow(params.getNewCancellationWindow());

        System.out.println(String.format(
                EventConstants.CANCELLATION_WINDOW_CHANGED_SUCCESS,
                params.getShowNumber(),
                oldCancellationWindow,
                params.getNewCancellationWindow()
        ));
    }

    public void showAvailability(EventParams params) {
        Map<Integer, Show> shows = getShows();
        if (0 >= params.getShowNumber() || !shows.containsKey(params.getShowNumber())) {
            System.out.println(EventConstants.SHOW_NOT_FOUND);
            return;
        }
        shows.get(params.getShowNumber()).getVenue().displayAvailableSeats();
    }

    public void bookTicket(EventParams params) {
        Show show = getShows().getOrDefault(params.getShowNumber(), null);

        if (show == null) {
            System.out.println(EventConstants.SHOW_NOT_FOUND);
            return;
        }

        if (EventServiceHelper.isPhoneNumberBooked(show, params.getPhone())) {
            System.out.println(EventConstants.ONE_BOOKING_PER_PHONE);
            return;
        }

        String[] requestedSeats = params.getSeats().split(",");
        if (!EventServiceHelper.areSeatsAvailable(show.getVenue(), requestedSeats)) {
            System.out.println(EventConstants.SELECTED_SEATS_NOT_AVAILABLE);
            return;
        }

        Arrays.stream(requestedSeats).forEach(seat -> show.getVenue().occupySeat(seat));

        String ticketNumber = UUID.randomUUID().toString();
        show.addTicket(ticketNumber, new Ticket(ticketNumber, params.getSeats(), params.getPhone()));
        System.out.println(String.format(EventConstants.TICKET_BOOKED_SUCCESS, ticketNumber));
    }

    public void cancelTicket(EventParams params) {
        Map<Integer, Show> shows = getShows();
        if (!shows.containsKey(params.getShowNumber())) {
            System.out.println(EventConstants.SHOW_NOT_FOUND);
            return;
        }

        Show show = shows.get(params.getShowNumber());

        if (!show.isValidCancellation(params.getTicketNumber(), params.getPhone())) {
            System.out.println(EventConstants.TICKET_CANCELLATION_DENIED);
            return;
        }

        Arrays.stream(show.getTicketMap().get(params.getTicketNumber()).getSeats().split(","))
                .forEach(seat -> show.getVenue().vacateSeat(seat));

        show.getTicketMap().remove(params.getTicketNumber());
        System.out.println(EventConstants.TICKET_CANCELLATION_SUCCESS);
    }

}
