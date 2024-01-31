package com.jpmorgan.event.entity;

import java.time.LocalDateTime;

public class Ticket {

    private String id;
    private String ticketNumber;
    private String seats;
    private String phoneNumber;
    private LocalDateTime createdOn;

    public Ticket(String ticketNumber, String seats, String phoneNumber) {
        this.ticketNumber = ticketNumber;
        this.seats = seats;
        this.phoneNumber = phoneNumber;
        this.createdOn = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

}