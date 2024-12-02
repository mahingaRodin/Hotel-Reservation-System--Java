public class Reservation {
    private String guestName;
    private int roomNumber;
    private int numberOfNights;
    private double totalCost;

    public Reservation(String guestName, int roomNumber, int numberOfNights, double totalCost) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.numberOfNights = numberOfNights;
        this.totalCost = totalCost;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Room: " + roomNumber + ", Nights: " + numberOfNights + ", Total Cost: $" + totalCost;
    }
}
