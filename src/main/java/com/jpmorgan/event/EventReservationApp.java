package com.jpmorgan.event;

import com.jpmorgan.event.constant.EventConstants;
import com.jpmorgan.event.helper.EventInitializerHelper;

import java.util.Scanner;

public class EventReservationApp {

    public static void main(String[] args) {
        System.out.print(EventConstants.HEADER);
        Scanner scanner = new Scanner(System.in);
        EventInitializerHelper.initializeEventReservation(scanner);
        scanner.close();
    }

}
