package com.jpmorgan.event.entity;

import java.util.Map;
import java.util.TreeMap;

public class Venue {

    private Long id;
    private Map<String, Boolean> seatOccupancyMap;
    private int rows;
    private int seatsPerRow;

    public Venue() {}

    public Venue(int rows, int seatsPerRow) {
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
        seatOccupancyMap = new TreeMap<>();
        initializeSeats(rows, seatsPerRow);
    }

    private void initializeSeats(int rows, int seatsPerRow) {
        for (int seatIndex = 1; seatIndex <= rows * seatsPerRow; seatIndex++) {
            char row = (char) ('A' + (seatIndex - 1) / seatsPerRow);
            int seatInRow = (seatIndex - 1) % seatsPerRow + 1;
            String seatNumber = row + Integer.toString(seatInRow);
            seatOccupancyMap.put(seatNumber, false);
        }
    }

    public boolean isSeatAvailable(String seatId) {
        return seatOccupancyMap.containsKey(seatId) && !seatOccupancyMap.get(seatId);
    }

    public void occupySeat(String seatId) {
        seatOccupancyMap.put(seatId, true);
    }

    public void vacateSeat(String seatId) {
        seatOccupancyMap.put(seatId, false);
    }

    public void displayAvailableSeats() {
        System.out.println("\nAvailable seats:");
        seatOccupancyMap.forEach((seatNumber, isOccupied) -> {
            if (!isOccupied) {
                System.out.print(seatNumber + ",");
            }
        });
        System.out.println();
    }

    public int getRows() {
        return rows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

}
