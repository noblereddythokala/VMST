package org.example.vmst;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class VehicleMaintenanceTracker extends Application {

    @Override
    public void start(Stage stage) {
        // Main Menu
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
        welcomeLabel.setFont(Font.font("Arial", 20));

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
        loginTitle.setFont(Font.font("Arial", 18));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");
        usernameField.setStyle("-fx-padding: 10; -fx-border-radius: 5; -fx-background-color: #E5E5E5;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setStyle("-fx-padding: 10; -fx-border-radius: 5; -fx-background-color: #E5E5E5;");

        Button loginButton = createStyledButton("Login");
        Button backButton = createStyledButton("Back");

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

        backButton.setOnAction(event -> stage.setScene(new Scene(createMainMenu(stage), 600, 450)));

        loginScene.getChildren().addAll(loginTitle, usernameField, passwordField, loginButton, backButton);
        return loginScene;
    }

    private VBox createOwnerDashboard(Stage stage) {
        VBox ownerDashboard = new VBox(20);
        ownerDashboard.setPadding(new Insets(20));
        ownerDashboard.setStyle("-fx-alignment: center; -fx-spacing: 20; -fx-background-color: #F1F1F1;");

        Label title = new Label("Vehicle Owner Dashboard");
        title.setFont(Font.font("Arial", 18));

        Button registerVehicleButton = createStyledButton("Register Vehicle");
        Button logMaintenanceButton = createStyledButton("Log Maintenance");
        Button viewHistoryButton = createStyledButton("View Maintenance History");
        Button backButton = createStyledButton("Back to Main Menu");

        registerVehicleButton.setOnAction(event -> stage.setScene(new Scene(createVehicleRegistrationForm(stage), 600, 450)));
        logMaintenanceButton.setOnAction(event -> showAlert("Feature", "Log Maintenance Activity Form"));
        viewHistoryButton.setOnAction(event -> showAlert("Feature", "Displaying Maintenance History Table"));

        backButton.setOnAction(event -> stage.setScene(new Scene(createMainMenu(stage), 600, 450)));

        ownerDashboard.getChildren().addAll(title, registerVehicleButton, logMaintenanceButton, viewHistoryButton, backButton);
        return ownerDashboard;
    }

    private VBox createVehicleRegistrationForm(Stage stage) {
        VBox registrationForm = new VBox(15);
        registrationForm.setPadding(new Insets(20));
        registrationForm.setStyle("-fx-alignment: center; -fx-spacing: 15; -fx-background-color: #EAEAEA;");

        Label formTitle = new Label("Enter Vehicle Details");
        formTitle.setFont(Font.font("Arial", 16));

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

        Button saveButton = createStyledButton("Save Vehicle");
        Button backButton = createStyledButton("Back");

        saveButton.setOnAction(event -> {
            // Placeholder for saving the vehicle, can later save to a file or database
            showAlert("Vehicle Saved", "The vehicle has been registered successfully.");
        });

        backButton.setOnAction(event -> {
            stage.setScene(new Scene(createOwnerDashboard(stage), 600, 450));
        });

        registrationForm.getChildren().addAll(formTitle, makeField, modelField, yearField, vinField, licensePlateField, saveButton, backButton);
        return registrationForm;
    }

    private VBox createTechnicianDashboard(Stage stage) {
        VBox technicianDashboard = new VBox(20);
        technicianDashboard.setPadding(new Insets(20));
        technicianDashboard.setStyle("-fx-alignment: center; -fx-spacing: 20; -fx-background-color: #F1F1F1;");

        Label title = new Label("Service Technician Dashboard");
        title.setFont(Font.font("Arial", 18));

        Button assignedVehiclesButton = createStyledButton("View Assigned Vehicles");
        Button logMaintenanceButton = createStyledButton("Log Maintenance");
        Button updateStatusButton = createStyledButton("Update Vehicle Status");
        Button scheduleMaintenanceButton = createStyledButton("Schedule Maintenance");
        Button backButton = createStyledButton("Back to Main Menu");

        assignedVehiclesButton.setOnAction(event -> showAlert("Feature", "Viewing Assigned Vehicles"));
        logMaintenanceButton.setOnAction(event -> showAlert("Feature", "Logging Maintenance Activity"));
        updateStatusButton.setOnAction(event -> showAlert("Feature", "Updating Vehicle Status"));
        scheduleMaintenanceButton.setOnAction(event -> showAlert("Feature", "Scheduling Maintenance"));

        backButton.setOnAction(event -> stage.setScene(new Scene(createMainMenu(stage), 600, 450)));

        technicianDashboard.getChildren().addAll(title, assignedVehiclesButton, logMaintenanceButton, updateStatusButton, scheduleMaintenanceButton, backButton);
        return technicianDashboard;
    }

    private VBox createAdminDashboard(Stage stage) {
        VBox adminDashboard = new VBox(20);
        adminDashboard.setPadding(new Insets(20));
        adminDashboard.setStyle("-fx-alignment: center; -fx-spacing: 20; -fx-background-color: #F1F1F1;");

        Label title = new Label("Administrator Dashboard");
        title.setFont(Font.font("Arial", 18));

        Button manageUsersButton = createStyledButton("Manage Users");
        Button overseeActivitiesButton = createStyledButton("Oversee System Activities");
        Button generateReportsButton = createStyledButton("Generate Reports");
        Button backButton = createStyledButton("Back to Main Menu");

        manageUsersButton.setOnAction(event -> showAlert("Feature", "Managing Users"));
        overseeActivitiesButton.setOnAction(event -> showAlert("Feature", "Overseeing System Activities"));
        generateReportsButton.setOnAction(event -> showAlert("Feature", "Generating Reports"));

        backButton.setOnAction(event -> stage.setScene(new Scene(createMainMenu(stage), 600, 450)));

        adminDashboard.getChildren().addAll(title, manageUsersButton, overseeActivitiesButton, generateReportsButton, backButton);
        return adminDashboard;
    }

    private boolean isValidLogin(String username, String password) {
        //return username.equals("admin") && password.equals("admin");
        return true;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", 14));
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
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
