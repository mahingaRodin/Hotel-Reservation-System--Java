public class Room {
    private int roomNumber;
    private String roomType;
    private double pricePerNight;
    private boolean isAvailable;

    public Room(int roomNumber, String roomType, double pricePerNight, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + roomType + ") - $" + pricePerNight + " per night, Available: " + isAvailable;
    }
}
