package org.example.vmst;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VehicleMaintenanceTracker extends Application {

    @Override
    public void start(Stage stage) {
        // Main Menu
        VBox mainMenu = createMainMenu(stage);
        Scene mainScene = new Scene(mainMenu, 500, 400);

        stage.setTitle("Vehicle Maintenance and Service Tracker");
        stage.setScene(mainScene);
        stage.show();
    }

    private VBox createMainMenu(Stage stage) {
        VBox mainMenu = new VBox(10);
        mainMenu.setPadding(new Insets(20));
        mainMenu.setStyle("-fx-alignment: center; -fx-spacing: 15;");

        Button ownerButton = new Button("Vehicle Owner");
        Button technicianButton = new Button("Service Technician");
        Button adminButton = new Button("Administrator");

        ownerButton.setOnAction(event -> stage.setScene(new Scene(createLoginScene("Vehicle Owner", stage), 500, 400)));
        technicianButton.setOnAction(event -> stage.setScene(new Scene(createLoginScene("Service Technician", stage), 500, 400)));
        adminButton.setOnAction(event -> stage.setScene(new Scene(createLoginScene("Administrator", stage), 500, 400)));

        mainMenu.getChildren().addAll(new Label("Login as:"), ownerButton, technicianButton, adminButton);
        return mainMenu;
    }

    private VBox createLoginScene(String title, Stage stage) {
        VBox loginScene = new VBox(10);
        loginScene.setPadding(new Insets(20));
        loginScene.setStyle("-fx-alignment: center; -fx-spacing: 15;");

        Label loginTitle = new Label(title + " Login");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        loginButton.setOnAction(event -> {
            if (isValidLogin(usernameField.getText(), passwordField.getText())) {
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
        });

        // Back Button goes back to the main menu
        backButton.setOnAction(event -> stage.setScene(new Scene(createMainMenu(stage), 500, 400)));

        loginScene.getChildren().addAll(loginTitle, usernameField, passwordField, loginButton, backButton);
        return loginScene;
    }

    private VBox createOwnerDashboard(Stage stage) {
        VBox ownerDashboard = new VBox(10);
        ownerDashboard.setPadding(new Insets(20));
        ownerDashboard.setStyle("-fx-alignment: top-center; -fx-spacing: 15;");

        Label title = new Label("Vehicle Owner Dashboard");
        Button registerVehicleButton = new Button("Register Vehicle");
        Button logMaintenanceButton = new Button("Log Maintenance");
        Button viewHistoryButton = new Button("View Maintenance History");
        Button backButton = new Button("Back to Main Menu");

        registerVehicleButton.setOnAction(event -> {
            stage.setScene(new Scene(createVehicleRegistrationForm(stage), 500, 400));
        });
        logMaintenanceButton.setOnAction(event -> showAlert("Feature", "Log Maintenance Activity Form"));
        viewHistoryButton.setOnAction(event -> showAlert("Feature", "Displaying Maintenance History Table"));

        // Back Button goes to the main menu
        backButton.setOnAction(event -> stage.setScene(new Scene(createMainMenu(stage), 500, 400)));

        ownerDashboard.getChildren().addAll(title, registerVehicleButton, logMaintenanceButton, viewHistoryButton, backButton);
        return ownerDashboard;
    }

    private VBox createVehicleRegistrationForm(Stage stage) {
        VBox registrationForm = new VBox(10);
        registrationForm.setPadding(new Insets(20));
        registrationForm.setStyle("-fx-alignment: center; -fx-spacing: 15;");

        Label formTitle = new Label("Enter Vehicle Details");

        TextField makeField = new TextField();
        makeField.setPromptText("Enter Vehicle Make");

        TextField modelField = new TextField();
        modelField.setPromptText("Enter Vehicle Model");

        TextField yearField = new TextField();
        yearField.setPromptText("Enter Vehicle Year");

        TextField vinField = new TextField();
        vinField.setPromptText("Enter Vehicle VIN");

        TextField licensePlateField = new TextField();
        licensePlateField.setPromptText("Enter Vehicle License Plate");

        Button saveButton = new Button("Save Vehicle");
        Button backButton = new Button("Back");

        saveButton.setOnAction(event -> {
            // Placeholder for saving the vehicle, can later save to a file or database
            showAlert("Vehicle Saved", "The vehicle has been registered successfully.");
        });

        backButton.setOnAction(event -> {
            stage.setScene(new Scene(createOwnerDashboard(stage), 600, 500));
        });

        registrationForm.getChildren().addAll(formTitle, makeField, modelField, yearField, vinField, licensePlateField, saveButton, backButton);
        return registrationForm;
    }

    private VBox createTechnicianDashboard(Stage stage) {
        VBox technicianDashboard = new VBox(10);
        technicianDashboard.setPadding(new Insets(20));
        technicianDashboard.setStyle("-fx-alignment: top-center; -fx-spacing: 15;");

        Label title = new Label("Service Technician Dashboard");
        Button assignedVehiclesButton = new Button("View Assigned Vehicles");
        Button logMaintenanceButton = new Button("Log Maintenance");
        Button updateStatusButton = new Button("Update Vehicle Status");
        Button scheduleMaintenanceButton = new Button("Schedule Maintenance");
        Button backButton = new Button("Back to Main Menu");

        assignedVehiclesButton.setOnAction(event -> showAlert("Feature", "Viewing Assigned Vehicles"));
        logMaintenanceButton.setOnAction(event -> showAlert("Feature", "Logging Maintenance Activity"));
        updateStatusButton.setOnAction(event -> showAlert("Feature", "Updating Vehicle Status"));
        scheduleMaintenanceButton.setOnAction(event -> showAlert("Feature", "Scheduling Maintenance"));

        // Back Button goes to the main menu
        backButton.setOnAction(event -> stage.setScene(new Scene(createMainMenu(stage), 500, 400)));

        technicianDashboard.getChildren().addAll(title, assignedVehiclesButton, logMaintenanceButton, updateStatusButton, scheduleMaintenanceButton, backButton);
        return technicianDashboard;
    }

    private VBox createAdminDashboard(Stage stage) {
        VBox adminDashboard = new VBox(10);
        adminDashboard.setPadding(new Insets(20));
        adminDashboard.setStyle("-fx-alignment: top-center; -fx-spacing: 15;");

        Label title = new Label("Administrator Dashboard");
        Button manageUsersButton = new Button("Manage Users");
        Button overseeActivitiesButton = new Button("Oversee System Activities");
        Button generateReportsButton = new Button("Generate Reports");
        Button backButton = new Button("Back to Main Menu");

        manageUsersButton.setOnAction(event -> showAlert("Feature", "Managing Users"));
        overseeActivitiesButton.setOnAction(event -> showAlert("Feature", "Overseeing System Activities"));
        generateReportsButton.setOnAction(event -> showAlert("Feature", "Generating Reports"));

        // Back Button goes to the main menu
        backButton.setOnAction(event -> stage.setScene(new Scene(createMainMenu(stage), 500, 400)));

        adminDashboard.getChildren().addAll(title, manageUsersButton, overseeActivitiesButton, generateReportsButton, backButton);
        return adminDashboard;
    }

    private boolean isValidLogin(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
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
