# **Hotel Reservation System**

A Java-based hotel reservation system with a graphical user interface (GUI) built using JavaFX. This project allows users to search for available rooms, make reservations, view booking details, and cancel reservations. The application demonstrates object-oriented programming principles and GUI-based user interaction.

---

## **Features**
- **Room Management**:
    - Add rooms with details like room number, type (Single, Double, Suite), price per night, and availability.
    - Categorize rooms based on their type.
- **Reservation System**:
    - Make reservations by selecting available rooms.
    - Calculate the total cost based on the number of nights.
    - Process payments (simulated within the system).
- **Cancellation**:
    - Cancel existing reservations and update room availability.
- **User-Friendly Interface**:
    - GUI built using JavaFX for easy navigation.
    - Dropdowns, forms, and buttons for seamless interaction.

---

## **Technologies Used**
- **Programming Language**: Java
- **Framework**: JavaFX for the GUI
- **IDE**: IntelliJ IDEA

---

## **Requirements**
- **Java Development Kit (JDK)**: Version 11 or later.
- **JavaFX SDK**: Ensure the JavaFX SDK is downloaded and configured.
- **IDE**: IntelliJ IDEA or any IDE supporting JavaFX projects.

---

## **Setup and Installation**

### Step 1: Clone the Repository
```bash
git clone https://github.com/mahingaRodin/Hotel-Reservation-System--Java.git
```

### Step 2: Open in IntelliJ IDEA
1. Open IntelliJ IDEA.
2. Select **File > Open** and navigate to the project folder.
3. Click **Open**.

### Step 3: Configure JavaFX SDK
1. Download the [JavaFX SDK](https://openjfx.io/).
2. Go to **File > Project Structure** in IntelliJ.
3. Add the JavaFX library:
    - Navigate to the extracted `lib` folder of the JavaFX SDK.
    - Select all `.jar` files in the folder and add them as a library.

### Step 4: Add VM Options
1. Go to **Run > Edit Configurations** in IntelliJ.
2. Add the following to the **VM Options** field:
   ```bash
   --module-path <path-to-javafx-sdk>/lib --add-modules javafx.controls,javafx.fxml
   ```
   Replace `<path-to-javafx-sdk>` with the path to your JavaFX SDK.

### Step 5: Run the Application
- Run the main class `HotelReservationSystem` to start the application.

---

## **Usage**
1. **Start the Application**: Launch the app to see the main menu.
2. **Search for Available Rooms**:
    - Select a room type (Single, Double, Suite) to see available options.
3. **Make a Reservation**:
    - Choose a room, enter guest details, and confirm payment.
4. **View Reservations**:
    - See all active reservations with details like guest name, room number, and total cost.
5. **Cancel a Reservation**:
    - Enter guest details to cancel an existing booking.

---

## **Screenshots**
(Screenshots of the application would go here to demonstrate the GUI.)

---

## **Future Improvements**
- Integration with a database (e.g., MySQL) for data persistence.
- Advanced payment gateway integration (e.g., PayPal or Stripe).
- Add user authentication for admins and customers.
- Enhance UI with more styling and animations.

---

## **Contributing**
Contributions are welcome! Please fork the repository and create a pull request with your proposed changes.

---

## **License**
This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## **Contact**
- **Author**: Mahinga Rodin
- **GitHub**: [mahingaRodin](https://github.com/mahingaRodin)
