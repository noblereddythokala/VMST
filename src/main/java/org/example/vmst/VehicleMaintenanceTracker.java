package org.example.vmst;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class VehicleMaintenanceTracker extends Application {

    private User currentUser; // Logged-in user

    @Override
    public void start(Stage stage) {
        VBox mainMenu = createMainMenu(stage);
        Scene mainScene = new Scene(mainMenu, 600, 450);

        stage.setTitle("Vehicle Maintenance and Service Tracker");
        stage.setScene(mainScene);
        stage.show();
    }

    private VBox createMainMenu(Stage stage) {
        VBox mainMenu = new VBox(20);
        mainMenu.setPadding(new Insets(30));
        mainMenu.setStyle("-fx-alignment: center; -fx-spacing: 20; -fx-background-color: #F1F1F1;");

        Label welcomeLabel = new Label("Welcome to the Vehicle Maintenance Tracker");
        welcomeLabel.setStyle("-fx-font-size: 20px;");

        Button ownerButton = createStyledButton("Vehicle Owner");
        Button technicianButton = createStyledButton("Service Technician");
        Button adminButton = createStyledButton("Administrator");

        ownerButton.setOnAction(event -> stage.setScene(new Scene(createLoginScene("Vehicle Owner", stage), 600, 450)));
        technicianButton.setOnAction(event -> stage.setScene(new Scene(createLoginScene("Service Technician", stage), 600, 450)));
        adminButton.setOnAction(event -> stage.setScene(new Scene(createLoginScene("Administrator", stage), 600, 450)));

        mainMenu.getChildren().addAll(welcomeLabel, ownerButton, technicianButton, adminButton);
        return mainMenu;
    }

    private VBox createLoginScene(String title, Stage stage) {
        VBox loginScene = new VBox(20);
        loginScene.setPadding(new Insets(20));
        loginScene.setStyle("-fx-alignment: center; -fx-spacing: 20; -fx-background-color: #FAFAFA;");

        Label loginTitle = new Label(title + " Login");
        loginTitle.setStyle("-fx-font-size: 18px;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        Button loginButton = createStyledButton("Login");
        Button backButton = createStyledButton("Back");

        loginButton.setOnAction(event -> {
            String username = usernameField.getText().trim();

            // Input validation: Check if username is empty
            if (username.isEmpty()) {
                showAlert("Input Error", "Username cannot be empty.");
                return;
            }

            try {
                UserDAO userDAO = new UserDAO();
                User user = userDAO.authenticateUser(username, null);  // Password is not used

                if (user != null && user.getRole().equalsIgnoreCase(title.toLowerCase())) {
                    currentUser = user;  // Store current user

                    // Navigate to respective dashboard based on the role
                    switch (title) {
                        case "Vehicle Owner":
                            stage.setScene(new Scene(createOwnerDashboard(stage), 600, 500));
                            break;
                        case "Service Technician":
                            stage.setScene(new Scene(createTechnicianDashboard(stage), 600, 500));
                            break;
                        case "Administrator":
                            stage.setScene(new Scene(createAdminDashboard(stage), 600, 500));
                            break;
                    }
                } else {
                    showAlert("Login Failed", "Invalid credentials for " + title + ".");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred while trying to log in. Please try again.");
            }
        });

        backButton.setOnAction(event -> stage.setScene(new Scene(createMainMenu(stage), 600, 450)));

        loginScene.getChildren().addAll(loginTitle, usernameField, passwordField, loginButton, backButton);
        return loginScene;
    }



    private VBox createOwnerDashboard(Stage stage) {
        VBox ownerDashboard = new VBox(20);
        ownerDashboard.setPadding(new Insets(20));
        ownerDashboard.setStyle("-fx-alignment: center; -fx-spacing: 20; -fx-background-color: #F1F1F1;");

        Label title = new Label("Vehicle Owner Dashboard");
        title.setStyle("-fx-font-size: 18px;");

        Button registerVehicleButton = createStyledButton("Register Vehicle");
        Button viewHistoryButton = createStyledButton("View Maintenance History");
        Button backButton = createStyledButton("Back to Main Menu");

        registerVehicleButton.setOnAction(event -> stage.setScene(new Scene(createVehicleRegistrationForm(stage), 600, 450)));
        viewHistoryButton.setOnAction(event -> stage.setScene(new Scene(createViewMaintenanceHistoryScene(stage), 600, 450)));
        backButton.setOnAction(event -> stage.setScene(new Scene(createMainMenu(stage), 600, 450)));

        ownerDashboard.getChildren().addAll(title, registerVehicleButton, viewHistoryButton, backButton);
        return ownerDashboard;
    }

    private VBox createVehicleRegistrationForm(Stage stage) {
        VBox registrationForm = new VBox(15);
        registrationForm.setPadding(new Insets(20));
        registrationForm.setStyle("-fx-alignment: center; -fx-spacing: 15; -fx-background-color: #EAEAEA;");

        Label formTitle = new Label("Enter Vehicle Details");
        formTitle.setStyle("-fx-font-size: 16px;");

        TextField vinField = new TextField();
        vinField.setPromptText("Enter Vehicle VIN");

        TextField makeField = new TextField();
        makeField.setPromptText("Enter Vehicle Make");

        TextField modelField = new TextField();
        modelField.setPromptText("Enter Vehicle Model");

        TextField yearField = new TextField();
        yearField.setPromptText("Enter Vehicle Year");

        TextField licensePlateField = new TextField();
        licensePlateField.setPromptText("Enter License Plate");

        ComboBox<User> technicianComboBox = new ComboBox<>();
        technicianComboBox.setPromptText("Select Technician");

        try {
            TechnicianDAO technicianDAO = new TechnicianDAO();
            List<User> technicians = technicianDAO.getAllTechnicians();
            technicianComboBox.setItems(FXCollections.observableArrayList(technicians));
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load technicians.");
        }

        Button saveButton = createStyledButton("Save Vehicle");
        saveButton.setOnAction(event -> {
            Vehicle vehicle = new Vehicle(
                    vinField.getText(),
                    makeField.getText(),
                    modelField.getText(),
                    Integer.parseInt(yearField.getText()),
                    licensePlateField.getText(),
                    currentUser.getId(),
                    technicianComboBox.getValue() != null ? technicianComboBox.getValue().getId() : null
            );

            try {
                VehicleDAO vehicleDAO = new VehicleDAO();
                vehicleDAO.addVehicle(vehicle);
                showAlert("Success", "Vehicle registered successfully.");
                vinField.clear();
                makeField.clear();
                modelField.clear();
                yearField.clear();
                licensePlateField.clear();
                technicianComboBox.getSelectionModel().clearSelection();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to register vehicle.");
            }
        });

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(event -> stage.setScene(new Scene(createOwnerDashboard(stage), 600, 450)));

        registrationForm.getChildren().addAll(formTitle, vinField, makeField, modelField, yearField, licensePlateField, technicianComboBox, saveButton, backButton);
        return registrationForm;
    }

    private VBox createViewMaintenanceHistoryScene(Stage stage) {
        VBox historyScene = new VBox(15);
        historyScene.setPadding(new Insets(20));
        historyScene.setStyle("-fx-alignment: center; -fx-spacing: 15; -fx-background-color: #EAEAEA;");

        Label title = new Label("Maintenance History");
        title.setStyle("-fx-font-size: 16px;");

        TextField vinField = new TextField();
        vinField.setPromptText("Enter Vehicle VIN");

        ListView<String> historyList = new ListView<>();

        Button fetchButton = createStyledButton("Fetch History");
        fetchButton.setOnAction(event -> {
            try {
                MaintenanceLogDAO logDAO = new MaintenanceLogDAO();
                List<MaintenanceLog> logs = logDAO.getLogsByVehicle(vinField.getText());
                historyList.getItems().clear();
                for (MaintenanceLog log : logs) {
                    historyList.getItems().add(log.getMaintenanceDate() + ": " + log.getDescription());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to fetch history.");
            }
        });

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(event -> stage.setScene(new Scene(createOwnerDashboard(stage), 600, 450)));

        historyScene.getChildren().addAll(title, vinField, fetchButton, historyList, backButton);
        return historyScene;
    }

    private VBox createTechnicianDashboard(Stage stage) {
        VBox technicianDashboard = new VBox(20);
        technicianDashboard.setPadding(new Insets(20));
        technicianDashboard.setStyle("-fx-alignment: center; -fx-spacing: 20; -fx-background-color: #F1F1F1;");

        Label title = new Label("Technician Dashboard");
        title.setStyle("-fx-font-size: 18px;");

        Button logMaintenanceButton = createStyledButton("Log Maintenance");
        Button viewAssignedVehiclesButton = createStyledButton("View Assigned Vehicles");
        Button backButton = createStyledButton("Back to Main Menu");

        logMaintenanceButton.setOnAction(event -> stage.setScene(new Scene(createLogMaintenanceForm(stage), 600, 450)));
        viewAssignedVehiclesButton.setOnAction(event -> stage.setScene(new Scene(createAssignedVehiclesScene(stage), 600, 450)));
        backButton.setOnAction(event -> stage.setScene(new Scene(createMainMenu(stage), 600, 450)));

        technicianDashboard.getChildren().addAll(title, logMaintenanceButton, viewAssignedVehiclesButton, backButton);
        return technicianDashboard;
    }

    private VBox createLogMaintenanceForm(Stage stage) {
        VBox logForm = new VBox(15);
        logForm.setPadding(new Insets(20));
        logForm.setStyle("-fx-alignment: center; -fx-spacing: 15; -fx-background-color: #EAEAEA;");

        Label title = new Label("Log Maintenance");
        title.setStyle("-fx-font-size: 16px;");

        TextField vinField = new TextField();
        vinField.setPromptText("Enter Vehicle VIN");

        DatePicker dateField = new DatePicker();

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Enter Description");

        Button saveButton = createStyledButton("Save");
        saveButton.setOnAction(event -> {
            try {
                MaintenanceLog log = new MaintenanceLog();
                log.setVehicleVin(vinField.getText());
                log.setMaintenanceDate(dateField.getValue());
                log.setDescription(descriptionField.getText());
                log.setTechnicianId(currentUser.getId());

                MaintenanceLogDAO logDAO = new MaintenanceLogDAO();
                logDAO.logMaintenance(log);

                showAlert("Success", "Maintenance logged successfully.");
                vinField.clear();
                dateField.setValue(null);
                descriptionField.clear();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to log maintenance.");
            }
        });

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(event -> stage.setScene(new Scene(createTechnicianDashboard(stage), 600, 450)));

        logForm.getChildren().addAll(title, vinField, dateField, descriptionField, saveButton, backButton);
        return logForm;
    }

    private VBox createAssignedVehiclesScene(Stage stage) {
        VBox assignedVehicles = new VBox(15);
        assignedVehicles.setPadding(new Insets(20));
        assignedVehicles.setStyle("-fx-alignment: center; -fx-spacing: 15; -fx-background-color: #EAEAEA;");

        Label title = new Label("Assigned Vehicles");
        title.setStyle("-fx-font-size: 16px;");

        ListView<String> vehicleList = new ListView<>();

        Button loadButton = createStyledButton("Load Assigned Vehicles");
        loadButton.setOnAction(event -> {
            try {
                VehicleDAO vehicleDAO = new VehicleDAO();
                List<Vehicle> vehicles = vehicleDAO.getVehiclesByOwner(currentUser.getId());
                vehicleList.getItems().clear();
                for (Vehicle v : vehicles) {
                    vehicleList.getItems().add(v.getMake() + " " + v.getModel() + " (" + v.getVin() + ")");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load vehicles.");
            }
        });

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(event -> stage.setScene(new Scene(createTechnicianDashboard(stage), 600, 450)));

        assignedVehicles.getChildren().addAll(title, loadButton, vehicleList, backButton);
        return assignedVehicles;
    }

    private VBox createAdminDashboard(Stage stage) {
        VBox adminDashboard = new VBox(20);
        adminDashboard.setPadding(new Insets(20));
        adminDashboard.setStyle("-fx-alignment: center; -fx-spacing: 20; -fx-background-color: #F1F1F1;");

        Label title = new Label("Administrator Dashboard");
        title.setStyle("-fx-font-size: 18px;");

        Button manageUsersButton = createStyledButton("Manage Users");
        Button generateReportsButton = createStyledButton("Generate Reports");
        Button backButton = createStyledButton("Back to Main Menu");

        manageUsersButton.setOnAction(event -> showAlert("Feature", "Manage Users"));
        generateReportsButton.setOnAction(event -> showAlert("Feature", "Generate Reports"));
        backButton.setOnAction(event -> stage.setScene(new Scene(createMainMenu(stage), 600, 450)));

        adminDashboard.getChildren().addAll(title, manageUsersButton, generateReportsButton, backButton);
        return adminDashboard;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        return button;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
