package project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginService extends BorderPane {

    public LoginService(Stage stage, User user, DatabaseModel database) {
        stage.setTitle("Login Page");

        Label usernameLabel = new Label("Enter Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Enter Password:");
        TextField passwordField = new TextField();
        Button loginButton = new Button("Log In");
        Button useInvitationCodeButton = new Button("Use Invitation Code"); // Button for invitation code
        Button resetAccountButton = new Button("Reset Account"); // New button for reset account
        
        GridPane gridPane = new GridPane(); // Spacing between elements
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 0, 2);

        // Check if the users table is empty to decide on Admin setup
        if (database.isDatabaseEmpty()) {
            Label adminSetupLabel = new Label("Setting up the first Admin account");
            loginButton.setText("Create Admin Account");

            loginButton.setOnAction(event -> {
                String initialUsername = usernameField.getText().trim();
                String initialPassword = passwordField.getText().trim();

                if (!initialUsername.isEmpty() && !initialPassword.isEmpty()) {
                    // For first-time admin setup
                    new AdminCreateAccount(stage, database, initialUsername, initialPassword);
                } else {
                    System.out.println("Username and password cannot be empty.");
                }
            });

            gridPane.add(adminSetupLabel, 0, 3, 2, 1);

        } else {
            // Standard login process for existing users
            loginButton.setOnAction(event -> {
                user.username = usernameField.getText();
                user.password = passwordField.getText();

                // Check if the user is flagged for reset
                if (database.isUserReset(user.username)) {
                    new ResetAccountPage(stage, user, database); // Redirect to ResetAccountPage
                } else if(database.doesUserExist(user.username) 
                		&& database.getUserField(user.username, "password").equals(user.password)){
                    // Check user's roles
                    String[] roles = database.getUserRoles(user.username); // Assume this method retrieves user roles
                    if (roles.length > 1) {
                        // More than one role, redirect to role selection page
                        new RoleSelectionPage(stage, user, database, roles);
                    } else if (roles.length == 1) {
                        // Redirect to home page based on the single role
                        if ("Admin".equals(roles[0])) {
                            new Admin(stage, user, database); // Redirect to Admin page if admin
                        } else {
                            new UserHome(stage, user, database); // Redirect to user home page
                        }
                    } else {
                        System.out.println("User has no roles assigned.");
                    }
                }
            });

            // Handle invitation code button click to open InvitationCodePage
            useInvitationCodeButton.setOnAction(event -> new InvitationCodePage(stage, user, database));

            // Handle reset account button click to open ResetAccountPage
            resetAccountButton.setOnAction(event -> new ResetAccountPage(stage, user, database));

            // Add the additional buttons only for non-admin setup
            gridPane.add(useInvitationCodeButton, 0, 3);
            gridPane.add(resetAccountButton, 1, 3);

        }
        this.setCenter(gridPane);
        Scene s = new Scene(this, 300, 200);
        Back.pushBack(s);
        stage.setScene(s);
        stage.show();
    }
}
