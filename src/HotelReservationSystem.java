import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HotelReservationSystem {
private final List<Room> rooms;
private final List<Reservation> reservations;

    public HotelReservationSystem() {
            rooms = new ArrayList<>();
            reservations = new ArrayList<>();
                }
                public void addRoom(Room room) {
                rooms.add(room);
                }

                public void searchAvailableRooms(String roomType) {
                    System.out.println("\nAvailable Rooms (" + roomType + "):");
                    boolean found = false;

                    for(Room room : rooms) {
                        if(room.isAvailable() && room.getRoomType().equalsIgnoreCase(roomType)) {
                            System.out.println(room);
                            found = true;
                        }
                    }
                    if(!found) {
                        System.out.println("No available rooms of this type.");
                    }
                }

                public boolean processPayment(String guestName ,  double amount) {
        Scanner scanner = new Scanner(System.in);
                    System.out.println("\nProcessing payment for " + guestName);
                    System.out.println("Total Amount: $" + amount);
                    System.out.print("Enter payment amount: $");
                    double payment = scanner.nextDouble();

                    if (payment >= amount) {
                        System.out.println("Payment successful! Change: $" + (payment - amount));
                        return true;
                    } else {
                        System.out.println("Payment failed. Insufficient amount.");
                        return false;
                    }
                }

    public void makeReservation(String guestName, int roomNumber, int nights) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                double totalCost = nights * room.getPricePerNight();

                if (processPayment(guestName, totalCost)) {
                    reservations.add(new Reservation(guestName, roomNumber, nights, totalCost));
                    room.setAvailability(false);
                    System.out.println("Reservation successful!");
                } else {
                    System.out.println("Reservation not completed due to failed payment.");
                }
                return;
            }
        }
        System.out.println("Room not available or does not exist.");
    }

    public void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations made yet.");
        } else {
            System.out.println("\n--- Reservations ---");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
                System.out.println("-------------------");
            }
        }
    }

    public void cancelReservation(String guestName) {
        for (int i=0 ; i<reservations.size();i++) {
            Reservation res = reservations.get(i);

            if(res.getGuestName().equalsIgnoreCase(guestName)) {
                int roomNumber = res.getRoomNumber();
                for(Room room : rooms) {
                    if(room.getRoomNumber() == roomNumber) {
                        room.setAvailability(true);
                        reservations.remove(i);
                        System.out.println("Reservation for " + guestName + " canceled successfully!");
                        return;
                    }
                }
            }
        }
        System.out.println("No reservation found for " + guestName);
    }
    public static void main(String[] args ) {
        HotelReservationSystem system = new HotelReservationSystem();
        Scanner scanner = new Scanner(System.in);
        system.addRoom(new Room(101, "Single", 50.0));
        system.addRoom(new Room(102, "Double", 75.0));
        system.addRoom(new Room(103, "Suite", 120.0));

        while (true) {
            System.out.println("\n=== Hotel Reservation System ===");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Reservations");
            System.out.println("4. Cancel a Reservation");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter room type (Single/Double/Suite): ");
                    String roomType = scanner.nextLine();
                    system.searchAvailableRooms(roomType);
                    break;

                case 2:
                    System.out.print("Enter your name: ");
                    String guestName = scanner.nextLine();
                    System.out.print("Enter room number: ");
                    int roomNumber = scanner.nextInt();
                    System.out.print("Enter number of nights: ");
                    int nights = scanner.nextInt();
                    system.makeReservation(guestName, roomNumber, nights);
                    break;

                case 3:
                    system.viewReservations();
                    break;

                case 4:
                    System.out.print("Enter guest name to cancel reservation: ");
                    String cancelGuestName = scanner.nextLine();
                    system.cancelReservation(cancelGuestName);
                    break;

                case 5:
                    System.out.println("Thank you for using the Hotel Reservation System. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    }


