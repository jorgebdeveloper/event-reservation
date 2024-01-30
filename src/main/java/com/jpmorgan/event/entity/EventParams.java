package com.jpmorgan.event.entity;

public class EventParams {

    private int showNumber;
    private int numRows;
    private int seatsPerRow;
    private int cancellationWindow;
    private String phone;
    private String seats;
    private String ticketNumber;
    private int newCancellationWindow;

    private EventParams(Builder builder) {
        this.showNumber = builder.showNumber;
        this.numRows = builder.numRows;
        this.seatsPerRow = builder.seatsPerRow;
        this.cancellationWindow = builder.cancellationWindow;
        this.phone = builder.phone;
        this.seats = builder.seats;
        this.ticketNumber = builder.ticketNumber;
        this.newCancellationWindow = builder.newCancellationWindow;
    }

    public void setShowNumber(int showNumber) {
        this.showNumber = showNumber;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    public void setCancellationWindow(int cancellationWindow) {
        this.cancellationWindow = cancellationWindow;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public void setNewCancellationWindow(int newCancellationWindow) {
        this.newCancellationWindow = newCancellationWindow;
    }

    public int getShowNumber() {
        return showNumber;
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

    public String getPhone() {
        return phone;
    }

    public String getSeats() {
        return seats;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public int getNewCancellationWindow() {
        return newCancellationWindow;
    }

    public static class Builder {
        private int showNumber;
        private int numRows;
        private int seatsPerRow;
        private int cancellationWindow;
        private String phone;
        private String seats;
        private String ticketNumber;
        private int newCancellationWindow;

        public Builder() {
        }

        public Builder showNumber(int showNumber) {
            this.showNumber = showNumber;
            return this;
        }

        public Builder numRows(int numRows) {
            this.numRows = numRows;
            return this;
        }

        public Builder seatsPerRow(int seatsPerRow) {
            this.seatsPerRow = seatsPerRow;
            return this;
        }

        public Builder cancellationWindow(int cancellationWindow) {
            this.cancellationWindow = cancellationWindow;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder seats(String seats) {
            this.seats = seats;
            return this;
        }

        public Builder ticketNumber(String ticketNumber) {
            this.ticketNumber = ticketNumber;
            return this;
        }

        public Builder newCancellationWindow(int newCancellationWindow) {
            this.newCancellationWindow = newCancellationWindow;
            return this;
        }

        public EventParams build() {
            return new EventParams(this);
        }
    }

}