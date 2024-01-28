package com.jpmorgan.event.entity;

import com.jpmorgan.event.constant.EventConstants;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.IntStream;

public class Show {

    private int showNumber;
    private int numRows;
    private int seatsPerRow;
    private int cancellationWindow;
    private Map<String, Boolean> seatsAvailability;
    private Map<String, Map<String, String>> bookedSeats;
    private Map<String, Long> bookedTimestamp;

    public Show() {}

    public Show(int showNumber, int numRows, int seatsPerRow) {
        this.showNumber = showNumber;
        this.numRows = numRows;
        this.seatsPerRow = seatsPerRow;
        this.cancellationWindow = 120; // default in seconds
        this.seatsAvailability = new HashMap<>();
        this.bookedSeats = new HashMap<>();
        this.bookedTimestamp = new HashMap<>();

        initializeSeats();
    }

    public int getNumRows() {
        return numRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public int getCancellationWindow() {
        return cancellationWindow;
    }

    public void setCancellationWindow(int cancellationWindow) {
        this.cancellationWindow = cancellationWindow;
    }

    public void setBookedSeats(Map<String, Map<String, String>> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public void setBookedTimestamp(Map<String, Long> bookedTimestamp) {
        this.bookedTimestamp = bookedTimestamp;
    }

    public Map<String, Boolean> getSeatsAvailability() {
        return seatsAvailability;
    }

    public Map<String, Map<String, String>> getBookedSeats() {
        return bookedSeats;
    }

    public Map<String, Long> getBookedTimestamp() {
        return bookedTimestamp;
    }

    private void initializeSeats() {
        for (int seatIndex = 1; seatIndex <= numRows * seatsPerRow; seatIndex++) {
            char row = (char) ('A' + (seatIndex - 1) / seatsPerRow);
            int seatInRow = (seatIndex - 1) % seatsPerRow + 1;
            String seatNumber = row + Integer.toString(seatInRow);
            seatsAvailability.put(seatNumber, true);
        }
    }

    public void view() {
        System.out.println("Show Number: " + showNumber);
        if (bookedSeats.size() < 1) {
            System.out.println("No seats booked so far.");
            return;
        }

        for (Map.Entry<String, Map<String, String>> entry : bookedSeats.entrySet()) {
            String ticketNumber = entry.getKey();
            String buyerPhone = entry.getValue().get("buyerPhone");
            Set<String> seatNumbers = Collections.singleton(entry.getValue().get("seatNumbers"));

            System.out.println("Ticket#: " + ticketNumber +
                    ", Buyer Phone#: " + buyerPhone +
                    ", Seat Numbers allocated to the buyer: " + String.join(", ", seatNumbers));
        }
    }

    public void showAvailability() {
        System.out.println();
        System.out.println("Seat Availability for Show " + showNumber + ":");
        System.out.println("Vacant [O] Occupied [X]:");
        System.out.println();

        // print column numbers
        System.out.print("  ");
        IntStream.rangeClosed(1, seatsPerRow)
                 .forEach(j -> System.out.print(j + " "));
        System.out.println();

        // print seat availability matrix
        IntStream.rangeClosed(1, numRows)
                 .forEach(i -> {
                    System.out.print((char) ('A' + i - 1) + " ");
                    IntStream.rangeClosed(1, seatsPerRow)
                             .mapToObj(seat -> (char) ('A' + i - 1) + Integer.toString(seat))
                             .map(seatNumber -> seatsAvailability.get(seatNumber) ? "O " : "X ")
                             .forEach(System.out::print);
                    System.out.println();
                 });
    }

    public boolean isValidCancellation(String ticketNumber, String phone) {
        if (!bookedSeats.containsKey(ticketNumber)) {
            System.out.println(EventConstants.INVALID_TICKET_PHONE);
            return false;
        }

        String buyerPhone = bookedSeats.get(ticketNumber).get("buyerPhone");

        if (!buyerPhone.equals(phone)) {
            System.out.println(EventConstants.INVALID_TICKET_PHONE);
            return false;
        }

        Instant bookingInstant = Instant.ofEpochMilli(bookedTimestamp.get(ticketNumber));
        LocalDateTime bookingTime = LocalDateTime.ofInstant(bookingInstant, ZoneOffset.systemDefault());

        long minutes = java.time.Duration.between(bookingTime, LocalDateTime.now()).toMinutes();
        return minutes <= cancellationWindow;
    }

    public String generateTicketNumber() {
        return UUID.randomUUID().toString();
    }

}