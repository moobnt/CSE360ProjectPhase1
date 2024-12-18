package project.article.backup;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.article.HelpArticleDatabase;
import project.util.Back;

import java.io.File;
import java.sql.SQLException;

/**
 * <p> RemoveExistingAndRestorePage class </p>
 * 
 * <p> Description: Handles the remove and restore page that specific users may use to alter existing articles</p>
 * 
 * @version 1.00 2024-10-30 Initial baseline
 */

public class RemoveExistingAndRestorePage extends VBox {
    public RemoveExistingAndRestorePage(Stage stage, HelpArticleDatabase helpArticleDatabase, File filename) {
        stage.setTitle("Remove Existing Articles and Restore");

        // Confirmation alert
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove all existing articles. Do you want to continue?", ButtonType.YES, ButtonType.NO);
        confirmationAlert.setHeaderText("Confirm Restoration");
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    helpArticleDatabase.removeAllArticles(); // Clear all existing articles
                    helpArticleDatabase.restoreArticlesFromBackup(filename); // Restore from the backup
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Articles restored successfully!", ButtonType.OK);
                    alert.showAndWait();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error restoring articles: " + e.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                }
            } else {
                stage.close(); // Close if the user selects NO
            }
        });

        Button back = new Button("Back");
        back.setOnAction(event -> {
        	Back.back(stage);
        	
        });

        // Add a loading message while the restoration is happening
        getChildren().addAll(new Label("Articles restored!"), back);

        Scene s = new Scene(this, 400, 200);
        Back.pushBack(s, "Remove Existing Articles and Restore");
        stage.setScene(s);
    }
}
 