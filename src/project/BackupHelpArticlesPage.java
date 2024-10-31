package project;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BackupHelpArticlesPage extends VBox {
    private HelpArticleDatabase helpArticleDatabase;
    private File backupFile = null;

    public BackupHelpArticlesPage(Stage stage, HelpArticleDatabase helpArticleDatabase) {
        this.helpArticleDatabase = helpArticleDatabase; // Store the database instance
        stage.setTitle("Backup Help Articles");

        // Create a TextField for the file name
        //TextField fileNameField = new TextField();
        //fileNameField.setPromptText("Enter backup file name (e.g., backup.txt)");
        //fileNameField.setFocusTraversable(false); // does not focus textbox on opening so that prompt is shown

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As...");
        fileChooser.getExtensionFilters().addAll(
        	new ExtensionFilter("Text Files", "*.txt"));

        Button openFileChooserButton = new Button();
        openFileChooserButton.setText("Save as...");
        openFileChooserButton.setOnAction(event -> {
            // if the file doesn't exist, the file will remain null
            // there are checks in other functions for this (or there should be)
            backupFile = fileChooser.showSaveDialog(stage);
        });

        // Create a Button to trigger the backup
        Button backupButton = new Button("Backup Articles");
        backupButton.setOnAction(event -> {
            //String fileName = fileNameField.getText();
            if (backupFile == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a file.", ButtonType.OK);
                alert.showAndWait();
            } else {
                backupArticles(backupFile);
            }
        });

        // Add components to the VBox
        getChildren().addAll(new Label("Backup Help Articles"), openFileChooserButton, backupButton);

        // Set the scene with the current VBox
        stage.setScene(new Scene(this, 400, 200));
        stage.show();
    }

    private void backupArticles(File backupFile) {
        try {
            List<HelpArticle> articles = helpArticleDatabase.getAllArticles(); // Fetch all articles
            FileWriter writer = new FileWriter(backupFile);

            for (HelpArticle article : articles) {
                writer.write(articleToString(article) + System.lineSeparator());
            }

            writer.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Backup completed successfully!", ButtonType.OK);
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error fetching articles: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error writing to file: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    private String articleToString(HelpArticle article) {
        // Convert the HelpArticle object to a String representation
        String keywordsString = Arrays.stream(article.getKeywords())
                                      .map(Object::toString)
                                      .collect(Collectors.joining(", "));
        String referenceLinksString = Arrays.stream(article.getReferenceLinks())
                                            .map(Object::toString)
                                            .collect(Collectors.joining(", "));
        
        return String.format("ID: %d; Title: %s; Level: %s; Group Identifier: %s; Access: %s; Short Description: %s; " +
                             "Keywords: %s; Body: %s; Reference Links: %s; Sensitive Title: %s; " +
                             "Sensitive Description: %s; Created Date: %s; Updated Date: %s", // all fields seperated by semicolons
                article.getId(),
                article.getTitle(),
                article.getLevel(),
                article.getGroupIdentifier(),
                article.getAccess(),
                article.getShortDescription(),
                keywordsString,
                article.getBody(),
                referenceLinksString,
                article.getSensitiveTitle(),
                article.getSensitiveDescription(),
                article.getCreatedDate().toString(), // Assuming createdDate is not null
                article.getUpdatedDate().toString()  // Assuming updatedDate is not null
        );
    }

}
