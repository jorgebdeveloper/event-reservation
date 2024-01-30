package com.jpmorgan.event.entity;

import com.jpmorgan.event.constant.EventConstants;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Show {

    private Integer id;
    private Venue venue;
    private Map<String, Ticket> ticketMap;
    private long cancellationWindow;

    public Show(Integer id, Venue venue, long cancellationWindow) {
        this.id = id;
        this.venue = venue;
        this.cancellationWindow = cancellationWindow;
        this.ticketMap = new HashMap<>();
    }

    public Integer getShowNumber() {
        return id;
    }

    public Venue getVenue() {
        return venue;
    }

    public Map<String, Ticket> getTicketMap() {
        return ticketMap;
    }

    public void addTicket(String ticketNumber, Ticket ticket) {
        ticketMap.put(ticketNumber, ticket);
    }

    public long getCancellationWindow() {
        return cancellationWindow;
    }

    public void setCancellationWindow(long cancellationWindow) {
        this.cancellationWindow = cancellationWindow;
    }

    public void view() {
        System.out.println("\nShow Number: " + id);

        if (ticketMap.isEmpty()) {
            System.out.println("No seats booked so far.");
            return;
        }

        String headerFormat = "%-20s%-40s%-15s%-15s%n";
        String rowFormat = "%-20s%-40s%-15s%-15s%n";

        System.out.printf(headerFormat, "Booked At", "Ticket#", "Phone#", "Seats");
        ticketMap.forEach((key, value) -> {
            String formattedDate = value.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            System.out.printf(rowFormat, formattedDate, key, value.getPhoneNumber(), value.getSeats());
        });
        System.out.println();
    }

    public boolean isValidCancellation(String ticketNumber, String phone) {
        Ticket ticket = ticketMap.get(ticketNumber);

        if (ticket == null || !ticket.getPhoneNumber().equals(phone)) {
            System.out.println(EventConstants.INVALID_TICKET_PHONE);
            return false;
        }

        Instant ticketCreatedOn = ticket.getCreatedOn().atZone(ZoneOffset.systemDefault()).toInstant();
        long minutes = Duration.between(ticketCreatedOn, Instant.now()).toSeconds();
        return minutes <= cancellationWindow;
    }

}