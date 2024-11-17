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

        logMaintenanceButton.setOnAction(event -> stage.setScene(new Scene(createLogMaintenanceForm(stage), 600, 450)));

        viewHistoryButton.setOnAction(event -> stage.setScene(new Scene(createViewMaintenanceHistoryScene(stage), 600, 450)));

        backButton.setOnAction(event -> stage.setScene(new Scene(createMainMenu(stage), 600, 450)));

        ownerDashboard.getChildren().addAll(title, registerVehicleButton, logMaintenanceButton, viewHistoryButton, backButton);
        return ownerDashboard;
    }

    private VBox createLogMaintenanceForm(Stage stage) {
        VBox maintenanceForm = new VBox(15);
        maintenanceForm.setPadding(new Insets(20));
        maintenanceForm.setStyle("-fx-alignment: center; -fx-spacing: 15; -fx-background-color: #EAEAEA;");

        Label formTitle = new Label("Log Vehicle Maintenance");
        formTitle.setFont(Font.font("Arial", 16));

        TextField vehicleIdField = new TextField();
        vehicleIdField.setPromptText("Enter Vehicle ID");

        TextField maintenanceDateField = new TextField();
        maintenanceDateField.setPromptText("Enter Maintenance Date (YYYY-MM-DD)");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Enter Maintenance Description");

        Button saveButton = createStyledButton("Save Maintenance");
        Button backButton = createStyledButton("Back");

        saveButton.setOnAction(event -> showAlert("Maintenance Logged", "Maintenance activity has been logged successfully."));

        backButton.setOnAction(event -> stage.setScene(new Scene(createOwnerDashboard(stage), 600, 450)));

        maintenanceForm.getChildren().addAll(formTitle, vehicleIdField, maintenanceDateField, descriptionField, saveButton, backButton);
        return maintenanceForm;
    }

    private VBox createViewMaintenanceHistoryScene(Stage stage) {
        VBox historyScene = new VBox(15);
        historyScene.setPadding(new Insets(20));
        historyScene.setStyle("-fx-alignment: center; -fx-spacing: 15; -fx-background-color: #EAEAEA;");

        Label title = new Label("Maintenance History");
        title.setFont(Font.font("Arial", 16));

        ListView<String> maintenanceHistoryList = new ListView<>();
        maintenanceHistoryList.getItems().addAll(
                "Vehicle ID: 123 - Maintenance Date: 2024-11-01 - Description: Oil Change",
                "Vehicle ID: 124 - Maintenance Date: 2024-11-02 - Description: Tire Rotation"
        );

        Button backButton = createStyledButton("Back to Owner Dashboard");
        backButton.setOnAction(event -> stage.setScene(new Scene(createOwnerDashboard(stage), 600, 450)));

        historyScene.getChildren().addAll(title, maintenanceHistoryList, backButton);
        return historyScene;
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

        assignedVehiclesButton.setOnAction(event -> stage.setScene(new Scene(createAssignedVehiclesScene(stage), 600, 450)));
        logMaintenanceButton.setOnAction(event -> stage.setScene(new Scene(createLogMaintenanceScene(stage), 600, 450)));
        updateStatusButton.setOnAction(event -> stage.setScene(new Scene(createUpdateVehicleStatusScene(stage), 600, 450)));
        scheduleMaintenanceButton.setOnAction(event -> stage.setScene(new Scene(createScheduleMaintenanceScene(stage), 600, 450)));

        backButton.setOnAction(event -> stage.setScene(new Scene(createMainMenu(stage), 600, 450)));

        technicianDashboard.getChildren().addAll(title, assignedVehiclesButton, logMaintenanceButton, updateStatusButton, scheduleMaintenanceButton, backButton);
        return technicianDashboard;
    }

    private VBox createAssignedVehiclesScene(Stage stage) {
        VBox layout = createBaseScene("Assigned Vehicles");

        Label instructions = new Label("List of assigned vehicles for the technician:");
        instructions.setFont(Font.font("Arial", 14));

        ListView<String> vehicleList = new ListView<>();
        vehicleList.getItems().addAll(
                "Tesla Model 3 - VIN: 5YJ3E1EA7LF000000",
                "Toyota Corolla - VIN: JTDBU4EE9BJ123456",
                "Ford Mustang - VIN: 1FA6P8CF2K1234567"
        );

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(event -> stage.setScene(new Scene(createTechnicianDashboard(stage), 600, 450)));

        layout.getChildren().addAll(instructions, vehicleList, backButton);
        return layout;
    }

    private VBox createLogMaintenanceScene(Stage stage) {
        VBox layout = createBaseScene("Log Maintenance Activity");

        Label instructions = new Label("Enter maintenance details:");
        instructions.setFont(Font.font("Arial", 14));

        TextField vinField = new TextField();
        vinField.setPromptText("Enter Vehicle VIN");

        TextArea maintenanceDetails = new TextArea();
        maintenanceDetails.setPromptText("Enter maintenance details");
        maintenanceDetails.setPrefRowCount(4);

        Button saveButton = createStyledButton("Save");
        saveButton.setOnAction(event -> showAlert("Success", "Maintenance details logged successfully."));

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(event -> stage.setScene(new Scene(createTechnicianDashboard(stage), 600, 450)));

        layout.getChildren().addAll(instructions, vinField, maintenanceDetails, saveButton, backButton);
        return layout;
    }

    private VBox createUpdateVehicleStatusScene(Stage stage) {
        VBox layout = createBaseScene("Update Vehicle Status");

        Label instructions = new Label("Update vehicle status:");
        instructions.setFont(Font.font("Arial", 14));

        TextField vinField = new TextField();
        vinField.setPromptText("Enter Vehicle VIN");

        TextField statusField = new TextField();
        statusField.setPromptText("Enter New Status");

        Button updateButton = createStyledButton("Update");
        updateButton.setOnAction(event -> showAlert("Success", "Vehicle status updated successfully."));

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(event -> stage.setScene(new Scene(createTechnicianDashboard(stage), 600, 450)));

        layout.getChildren().addAll(instructions, vinField, statusField, updateButton, backButton);
        return layout;
    }

    private VBox createScheduleMaintenanceScene(Stage stage) {
        VBox layout = createBaseScene("Schedule Maintenance");

        Label instructions = new Label("Schedule maintenance for a vehicle:");
        instructions.setFont(Font.font("Arial", 14));

        TextField vinField = new TextField();
        vinField.setPromptText("Enter Vehicle VIN");

        DatePicker maintenanceDate = new DatePicker();
        maintenanceDate.setPromptText("Select Maintenance Date");

        Button scheduleButton = createStyledButton("Schedule");
        scheduleButton.setOnAction(event -> showAlert("Success", "Maintenance scheduled successfully."));

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(event -> stage.setScene(new Scene(createTechnicianDashboard(stage), 600, 450)));

        layout.getChildren().addAll(instructions, vinField, maintenanceDate, scheduleButton, backButton);
        return layout;
    }

    private VBox createBaseScene(String titleText) {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-alignment: center; -fx-spacing: 15; -fx-background-color: #EAEAEA;");

        Label title = new Label(titleText);
        title.setFont(Font.font("Arial", 18));
        layout.getChildren().add(title);
        return layout;
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

        manageUsersButton.setOnAction(event -> stage.setScene(new Scene(createManageUsersScene(stage), 600, 450)));
        overseeActivitiesButton.setOnAction(event -> stage.setScene(new Scene(createOverseeActivitiesScene(stage), 600, 450)));
        generateReportsButton.setOnAction(event -> stage.setScene(new Scene(createGenerateReportsScene(stage), 600, 450)));

        backButton.setOnAction(event -> stage.setScene(new Scene(createMainMenu(stage), 600, 450)));

        adminDashboard.getChildren().addAll(title, manageUsersButton, overseeActivitiesButton, generateReportsButton, backButton);
        return adminDashboard;
    }
    private VBox createManageUsersScene(Stage stage) {
        VBox layout = createBaseScene("Manage Users");

        Label instructions = new Label("Manage user accounts and roles:");
        instructions.setFont(Font.font("Arial", 14));

        Button addUserButton = createStyledButton("Add New User");
        Button editUserButton = createStyledButton("Edit User Details");
        Button deleteUserButton = createStyledButton("Delete User");

        addUserButton.setOnAction(event -> showAlert("Feature", "Add New User"));
        editUserButton.setOnAction(event -> showAlert("Feature", "Edit User Details"));
        deleteUserButton.setOnAction(event -> showAlert("Feature", "Delete User"));

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(event -> stage.setScene(new Scene(createAdminDashboard(stage), 600, 450)));

        layout.getChildren().addAll(instructions, addUserButton, editUserButton, deleteUserButton, backButton);
        return layout;
    }
    private VBox createOverseeActivitiesScene(Stage stage) {
        VBox layout = createBaseScene("Oversee System Activities");

        Label instructions = new Label("View and monitor system activities:");
        instructions.setFont(Font.font("Arial", 14));

        Button activityLogButton = createStyledButton("View Activity Log");
        Button systemStatusButton = createStyledButton("Check System Status");

        activityLogButton.setOnAction(event -> showAlert("Feature", "View Activity Log"));
        systemStatusButton.setOnAction(event -> showAlert("Feature", "Check System Status"));

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(event -> stage.setScene(new Scene(createAdminDashboard(stage), 600, 450)));

        layout.getChildren().addAll(instructions, activityLogButton, systemStatusButton, backButton);
        return layout;
    }
    private VBox createGenerateReportsScene(Stage stage) {
        VBox layout = createBaseScene("Generate Reports");

        Label instructions = new Label("Generate system reports:");
        instructions.setFont(Font.font("Arial", 14));

        Button salesReportButton = createStyledButton("Generate Sales Report");
        Button maintenanceReportButton = createStyledButton("Generate Maintenance Report");
        Button userActivityReportButton = createStyledButton("Generate User Activity Report");

        salesReportButton.setOnAction(event -> showAlert("Feature", "Generate Sales Report"));
        maintenanceReportButton.setOnAction(event -> showAlert("Feature", "Generate Maintenance Report"));
        userActivityReportButton.setOnAction(event -> showAlert("Feature", "Generate User Activity Report"));

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(event -> stage.setScene(new Scene(createAdminDashboard(stage), 600, 450)));

        layout.getChildren().addAll(instructions, salesReportButton, maintenanceReportButton, userActivityReportButton, backButton);
        return layout;
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
