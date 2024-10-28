package org.example._311_regex_javadoc;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.regex.Pattern;

public class RegistrationForm extends Application {

    // Regular expressions for validations
    private static final String NAME_REGEX = "^[a-zA-Z]{2,25}$";
    private static final String DOB_REGEX = "^\\d{2}/\\d{2}/\\d{4}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@farmingdale\\.edu$";
    private static final String ZIP_REGEX = "^\\d{5}$";

    private TextField firstNameField = new TextField();
    private TextField lastNameField = new TextField();
    private TextField emailField = new TextField();
    private TextField dobField = new TextField();
    private TextField zipField = new TextField();
    private Button addButton = new Button("Add");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Registration Form");


        // Creating the layout grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        // Labels
        Label firstNameLabel = new Label("First Name:");
        Label lastNameLabel = new Label("Last Name:");
        Label emailLabel = new Label("Email:");
        Label dobLabel = new Label("Date of Birth (MM/DD/YYYY):");
        Label zipLabel = new Label("Zip Code:");

        // Positioning Labels and Fields
        grid.add(firstNameLabel, 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(lastNameLabel, 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(dobLabel, 0, 3);
        grid.add(dobField, 1, 3);
        grid.add(zipLabel, 0, 4);
        grid.add(zipField, 1, 4);
        grid.add(addButton, 1, 5);

        // Disable the button initially
        addButton.setDisable(true);

        // Add input validation listeners
        validateFields();

        Scene scene = new Scene(grid, 400, 300);
        grid.setStyle("-fx-background-color: lightblue;");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void validateFields() {
        // Bind button disabled state to validity of input fields
        addButton.disableProperty().bind(
                Bindings.createBooleanBinding(() -> !(
                                isValid(firstNameField.getText(), NAME_REGEX) &&
                                        isValid(lastNameField.getText(), NAME_REGEX) &&
                                        isValid(emailField.getText(), EMAIL_REGEX) &&
                                        isValid(dobField.getText(), DOB_REGEX) &&
                                        isValid(zipField.getText(), ZIP_REGEX)
                        ), firstNameField.textProperty(),
                        lastNameField.textProperty(),
                        emailField.textProperty(),
                        dobField.textProperty(),
                        zipField.textProperty()
                ));

        // Adding focus listeners to display validation messages
        addValidationListener(firstNameField, NAME_REGEX, "First Name should be 2-25 letters.");
        addValidationListener(lastNameField, NAME_REGEX, "Last Name should be 2-25 letters.");
        addValidationListener(emailField, EMAIL_REGEX, "Enter a valid Farmingdale email.");
        addValidationListener(dobField, DOB_REGEX, "Date should be in MM/DD/YYYY format.");
        addValidationListener(zipField, ZIP_REGEX, "Zip code should be 5 digits.");
    }

    private boolean isValid(String input, String regex) {
        return input != null && Pattern.matches(regex, input);
    }

    private void addValidationListener(TextField field, String regex, String errorMessage) {
        field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // On focus lost
                if (!isValid(field.getText(), regex)) {
                    showAlert(errorMessage);
                }
            }
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Validation Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Main entry point for the JavaFX application.
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
