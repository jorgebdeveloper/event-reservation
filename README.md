# Event Reservation Application
Demonstration of plain java implementation with unit testing and clean code practices. 

This Java application allows users to book and manage seats for shows. 
The program supports two types of users: Admin and Buyer. 
Admins can set up shows and view show details, 
while Buyers can check seat availability, book tickets, and cancel bookings.

## Usage
* User Option
  * Admin - Setup show parameters such as seat numbers for a venue and cancellation window expiration.
  * Buyer - Check available seats, book a reservation, cancel a ticket booking.
  * Exit - Exits the application.
  
* Admin Commands
  * Setup Show
    * Sets up the number of seats per show. 
      * setup [Show Number] [Number of Rows] [Number of Seats per Row] [Cancellation Window in Seconds]
      * Example: setup 1 26 10 120
  * View Show Details
    * Displays Show Number, Ticket#, Buyer Phone#, and Seat Numbers allocated to the buyer.
        * view [Show Number]
        * Example: view 1
* Buyer Commands
  * Check Seat Availability
      * Lists all available seat numbers for a show.
          * availability [Show Number]
          * Example: availability 1
  * Book Tickets
      * Books a ticket and generates a unique ticket number.
          * book [Show Number] [Phone#] [Comma Separated List of Seats]
          * Example: book 1 1234567890 A1,A2,A3
  * Cancel Tickets
      * Cancels a booked ticket within the configurable cancellation window.
          * cancel [Show Number] [Ticket#] [Phone#]
          * Example: cancel 1 24ab49b8-830e-4bd9-865c-d06d44d55815 1234567890

## Constraints
* Max seats per row: 10, Max rows: 26
* Cancellation window: 2 minutes (configurable per show)
