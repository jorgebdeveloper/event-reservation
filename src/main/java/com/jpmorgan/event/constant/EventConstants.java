package com.jpmorgan.event.constant;

public class EventConstants {

    public static final int MAX_ROWS = 26;
    public static final int MAX_SEATS_PER_ROW = 10;

    public static final String HEADER = """
                                            
                                            ***********************************************
                                                     Event Reservation Application         \s
                                            ***********************************************
                                            Welcome to the Event Reservation Application!
                                            You can use this application to setup shows,
                                            view seat availability, book tickets, and more.
                                            ***********************************************
                                        """;

    public static final String LANDING_PROMPT = """
                                                [Choose user type or exit]
                                                (1) Admin
                                                (2) Buyer
                                                (3) Exit
                                                """;
    public static final String ADMIN_COMMAND_PROMPT = "Enter Admin Command: ";
    public static final String BUYER_COMMAND_PROMPT = "Enter Buyer Command: ";
    public static final String INVALID_USER_TYPE = "Invalid user type.";
    public static final String SHOW_NOT_FOUND = "\nShow not found.";
    public static final String SHOW_SETUP_SUCCESS = "Show setup successfully.";
    public static final String INVALID_SETUP_PARAMETERS = "Invalid show setup parameters or show already exists.";
    public static final String TICKET_BOOKED_SUCCESS = "\nTicket booked successfully. Ticket#: %s";
    public static final String SELECTED_SEATS_NOT_AVAILABLE = "Selected seats are not available.";
    public static final String ONE_BOOKING_PER_PHONE = "Only one booking per phone# is allowed per show.";
    public static final String TICKET_CANCELLATION_DENIED = "The window for canceling the ticket has elapsed.";
    public static final String TICKET_CANCELLATION_SUCCESS = "Ticket canceled successfully.";
    public static final String INVALID_TICKET_PHONE = "No matches for ticket number or phone number.";
    public static final String INVALID_ADMIN_COMMAND = "Invalid admin command.";
    public static final String INVALID_BUYER_COMMAND = "Invalid buyer command.";
    public static final String EXIT_COMMAND_PROMPT = "Application exited successfully.";
    public static final String CANCELLATION_WINDOW_CHANGED_SUCCESS = "Cancellation window changed for show %d. Old window: %d, New window: %d";
}