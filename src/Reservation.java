public class Reservation {
    private final String guestName;
    private final int roomNumber;
    private final int nights;
    private final double totalCost;

    public Reservation(String guestName, int roomNumber, int nights, double totalCost) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.nights = nights;
        this.totalCost = totalCost;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getNights() {
        return nights;
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return "Reservation for " + guestName + "\nRoom Number: " + roomNumber +
                "\nNights: " + nights + "\nTotal Cost: $" + totalCost;
    }
}
