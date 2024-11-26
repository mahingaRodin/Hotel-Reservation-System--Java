import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HotelReservationSystem extends Application {
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Sample rooms
        rooms.add(new Room(101, "Single", 50.0));
        rooms.add(new Room(102, "Double", 75.0));
        rooms.add(new Room(103, "Suite", 120.0));

        // Main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));

        // Buttons
        Button searchButton = new Button("Search Rooms");
        Button reserveButton = new Button("Make a Reservation");
        Button viewReservationsButton = new Button("View Reservations");
        Button cancelReservationButton = new Button("Cancel a Reservation");

        // Add buttons to layout
        mainLayout.getChildren().addAll(searchButton, reserveButton, viewReservationsButton, cancelReservationButton);

        // Set event handlers
        searchButton.setOnAction(e -> searchRooms(primaryStage));
        reserveButton.setOnAction(e -> makeReservation(primaryStage));
        viewReservationsButton.setOnAction(e -> viewReservations(primaryStage));
        cancelReservationButton.setOnAction(e -> cancelReservation(primaryStage));

        // Set the scene
        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setTitle("Hotel Reservation System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Search Rooms
    private void searchRooms(Stage primaryStage) {
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
            for (Room room : rooms) {
                if (room.isAvailable() && room.getRoomType().equalsIgnoreCase(roomType)) {
                    resultArea.appendText(room + "\n");
                }
            }
            if (resultArea.getText().isEmpty()) {
                resultArea.setText("No available rooms of this type.");
            }
        });

        layout.getChildren().addAll(label, roomTypeField, searchButton, resultArea);
        Scene scene = new Scene(layout, 400, 300);
        searchStage.setTitle("Search Rooms");
        searchStage.setScene(scene);
        searchStage.show();
    }

    // Make a Reservation
    private void makeReservation(Stage primaryStage) {
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

            for (Room room : rooms) {
                if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                    double totalCost = nights * room.getPricePerNight();
                    reservations.add(new Reservation(guestName, roomNumber, nights, totalCost));
                    room.setAvailability(false);
                    resultLabel.setText("Reservation successful for " + guestName + "!");
                    return;
                }
            }
            resultLabel.setText("Room not available or does not exist.");
        });

        layout.getChildren().addAll(guestLabel, guestField, roomLabel, roomField, nightsLabel, nightsField, reserveButton, resultLabel);
        Scene scene = new Scene(layout, 400, 300);
        reservationStage.setTitle("Make a Reservation");
        reservationStage.setScene(scene);
        reservationStage.show();
    }

    // View Reservations
    private void viewReservations(Stage primaryStage) {
        Stage viewStage = new Stage();
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        TextArea reservationArea = new TextArea();
        reservationArea.setEditable(false);

        for (Reservation reservation : reservations) {
            reservationArea.appendText(reservation + "\n-------------------\n");
        }

        if (reservations.isEmpty()) {
            reservationArea.setText("No reservations made yet.");
        }

        layout.getChildren().add(reservationArea);
        Scene scene = new Scene(layout, 400, 300);
        viewStage.setTitle("View Reservations");
        viewStage.setScene(scene);
        viewStage.show();
    }

    // Cancel a Reservation
    private void cancelReservation(Stage primaryStage) {
        Stage cancelStage = new Stage();
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label guestLabel = new Label("Enter guest name to cancel reservation:");
        TextField guestField = new TextField();
        Button cancelButton = new Button("Cancel Reservation");
        Label resultLabel = new Label();

        cancelButton.setOnAction(e -> {
            String guestName = guestField.getText();
            for (int i = 0; i < reservations.size(); i++) {
                if (reservations.get(i).getGuestName().equalsIgnoreCase(guestName)) {
                    int roomNumber = reservations.get(i).getRoomNumber();
                    for (Room room : rooms) {
                        if (room.getRoomNumber() == roomNumber) {
                            room.setAvailability(true);
                            reservations.remove(i);
                            resultLabel.setText("Reservation canceled for " + guestName);
                            return;
                        }
                    }
                }
            }
            resultLabel.setText("No reservation found for " + guestName);
        });

        layout.getChildren().addAll(guestLabel, guestField, cancelButton, resultLabel);
        Scene scene = new Scene(layout, 400, 200);
        cancelStage.setTitle("Cancel Reservation");
        cancelStage.setScene(scene);
        cancelStage.show();
    }
}
