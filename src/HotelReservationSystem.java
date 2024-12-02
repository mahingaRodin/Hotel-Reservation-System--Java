import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HotelReservationSystem extends Application {
    private final List<Room> rooms = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));

        Button searchButton = new Button("Search Rooms");
        Button reserveButton = new Button("Make a Reservation");
        Button viewReservationsButton = new Button("View Reservations");
        Button cancelReservationButton = new Button("Cancel a Reservation");

        mainLayout.getChildren().addAll(searchButton, reserveButton, viewReservationsButton, cancelReservationButton);

        searchButton.setOnAction(e -> searchRooms());
        reserveButton.setOnAction(e -> makeReservation());
        viewReservationsButton.setOnAction(e -> viewReservations());
        cancelReservationButton.setOnAction(e -> cancelReservation(primaryStage));

        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setTitle("Hotel Reservation System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Search Rooms
    private void searchRooms() {
        Stage searchStage = new Stage();
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label label = new Label("Enter room type (Single/Double/Suite):");
        TextField roomTypeField = new TextField();
        Button searchButton = new Button("Search");
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        searchButton.setOnAction(e -> {
            String roomType = roomTypeField.getText();
            resultArea.clear();

            try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "SELECT * FROM rooms WHERE room_type = ? AND is_available = TRUE";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, roomType);

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int roomNumber = rs.getInt("room_number");
                    String type = rs.getString("room_type");
                    double price = rs.getDouble("price_per_night");
                    boolean isAvailable = rs.getBoolean("is_available");
                    Room room = new Room(roomNumber, type, price, isAvailable);
                    resultArea.appendText(room + "\n");
                }

                if (resultArea.getText().isEmpty()) {
                    resultArea.setText("No available rooms of this type.");
                }
            } catch (Exception ex) {
                resultArea.setText("Error: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(label, roomTypeField, searchButton, resultArea);
        Scene scene = new Scene(layout, 400, 300);
        searchStage.setTitle("Search Rooms");
        searchStage.setScene(scene);
        searchStage.show();
    }

    // Make Reservation
    private void makeReservation() {
        Stage reservationStage = new Stage();
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label guestLabel = new Label("Guest Name:");
        TextField guestField = new TextField();

        Label roomLabel = new Label("Room Number:");
        TextField roomField = new TextField();

        Label nightsLabel = new Label("Number of Nights:");
        TextField nightsField = new TextField();

        Button reserveButton = new Button("Reserve");
        Label resultLabel = new Label();

        reserveButton.setOnAction(e -> {
            String guestName = guestField.getText();
            int roomNumber = Integer.parseInt(roomField.getText());
            int nights = Integer.parseInt(nightsField.getText());

            try (Connection conn = DatabaseConnection.getConnection()) {
                String checkRoomQuery = "SELECT is_available, price_per_night FROM rooms WHERE room_number = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkRoomQuery);
                checkStmt.setInt(1, roomNumber);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next() && rs.getBoolean("is_available")) {
                    double pricePerNight = rs.getDouble("price_per_night");
                    double totalCost = nights * pricePerNight;

                    String reserveQuery = "INSERT INTO reservations (guest_name, room_number, number_of_nights, total_cost) VALUES (?, ?, ?, ?)";
                    PreparedStatement reserveStmt = conn.prepareStatement(reserveQuery);
                    reserveStmt.setString(1, guestName);
                    reserveStmt.setInt(2, roomNumber);
                    reserveStmt.setInt(3, nights);
                    reserveStmt.setDouble(4, totalCost);
                    reserveStmt.executeUpdate();

                    String updateRoomQuery = "UPDATE rooms SET is_available = FALSE WHERE room_number = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateRoomQuery);
                    updateStmt.setInt(1, roomNumber);
                    updateStmt.executeUpdate();

                    resultLabel.setText("Reservation successful for " + guestName + "!");
                } else {
                    resultLabel.setText("Room not available.");
                }
            } catch (Exception ex) {
                resultLabel.setText("Error: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(guestLabel, guestField, roomLabel, roomField, nightsLabel, nightsField, reserveButton, resultLabel);
        Scene scene = new Scene(layout, 400, 300);
        reservationStage.setTitle("Make a Reservation");
        reservationStage.setScene(scene);
        reservationStage.show();
    }

    // View Reservations
    private void viewReservations() {
        Stage viewStage = new Stage();
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        TextArea reservationArea = new TextArea();
        reservationArea.setEditable(false);

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM reservations";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String guestName = rs.getString("guest_name");
                int roomNumber = rs.getInt("room_number");
                int numberOfNights = rs.getInt("number_of_nights");
                double totalCost = rs.getDouble("total_cost");

                Reservation reservation = new Reservation(guestName, roomNumber, numberOfNights, totalCost);
                reservationArea.appendText(reservation + "\n-------------------\n");
            }

            if (reservationArea.getText().isEmpty()) {
                reservationArea.setText("No reservations made yet.");
            }
        } catch (Exception ex) {
            reservationArea.setText("Error: " + ex.getMessage());
        }

        layout.getChildren().add(reservationArea);
        Scene scene = new Scene(layout, 400, 300);
        viewStage.setTitle("View Reservations");
        viewStage.setScene(scene);
        viewStage.show();
    }

//    Cancel reservations
// Cancel a Reservation by ID
private void cancelReservation(Stage primaryStage) {
    Stage cancelStage = new Stage();
    VBox layout = new VBox(10);
    layout.setPadding(new Insets(20));

    Label idLabel = new Label("Enter reservation ID to cancel:");
    TextField idField = new TextField();
    Button cancelButton = new Button("Cancel Reservation");
    Label resultLabel = new Label();

    cancelButton.setOnAction(e -> {
        try {
            // Parse reservation ID
            int reservationId = Integer.parseInt(idField.getText());

            // Attempt to cancel reservation in the database
            boolean isCanceled = cancelReservationInDB(reservationId);

            // Update result label based on success or failure
            if (isCanceled) {
                resultLabel.setText("Reservation with ID " + reservationId + " has been canceled successfully.");
            } else {
                resultLabel.setText("Reservation with ID " + reservationId + " not found.");
            }
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid ID format. Please enter a valid numeric ID.");
        } catch (Exception ex) {
            resultLabel.setText("Error occurred: " + ex.getMessage());
        }
    });

    layout.getChildren().addAll(idLabel, idField, cancelButton, resultLabel);
    Scene scene = new Scene(layout, 400, 200);
    cancelStage.setTitle("Cancel Reservation");
    cancelStage.setScene(scene);
    cancelStage.show();
}
    // Cancel reservation in the database using the reservation ID
    private boolean cancelReservationInDB(int reservationId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if the reservation exists
            String selectQuery = "SELECT room_number FROM reservations WHERE reservation_id = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setInt(1, reservationId);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                // Fetch room number for the reservation
                int roomNumber = resultSet.getInt("room_number");

                // Delete the reservation
                String deleteQuery = "DELETE FROM reservations WHERE reservation_id = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
                deleteStmt.setInt(1, reservationId);
                deleteStmt.executeUpdate();

                // Mark the room as available
                String updateRoomQuery = "UPDATE rooms SET is_available = true WHERE room_number = ?";
                PreparedStatement updateRoomStmt = conn.prepareStatement(updateRoomQuery);
                updateRoomStmt.setInt(1, roomNumber);
                updateRoomStmt.executeUpdate();

                return true; // Successfully canceled the reservation
            }
        } catch (Exception ex) {
            System.out.println("Error while canceling reservation: " + ex.getMessage());
        }
        return false; // Failed to cancel
    }


}
