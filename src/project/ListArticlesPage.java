package project;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class ListArticlesPage extends TilePane {
    private HelpArticleDatabase helpArticleDatabase;

    public ListArticlesPage(Stage stage, HelpArticleDatabase helpArticleDatabase) {
        this.helpArticleDatabase = helpArticleDatabase; // Store the database instance
        stage.setTitle("List Articles");

        // Create buttons for different article listing options
        Button listAllArticlesButton = new Button("List All Articles");
        listAllArticlesButton.setOnAction(event -> {
            ListArticlesHelpPage listArticlesHelpPage = new ListArticlesHelpPage(stage, helpArticleDatabase);
        });

        Button listByGroupButton = new Button("List Articles by Group");
        listByGroupButton.setOnAction(event -> { 
            // Navigate to InputGroupIdPage for user input
            InputGroupIdPage inputGroupIdPage = new InputGroupIdPage(stage, helpArticleDatabase);
        });

        Button viewArticleButton = new Button("View Article by Title");
        viewArticleButton.setOnAction(event -> {
        	// Navigate to InputGroupIdPage for user input
        	InputTitlePage inputTitlePage = new InputTitlePage(stage, helpArticleDatabase);
        });

        // Add buttons to the TilePane
        getChildren().addAll(listAllArticlesButton, listByGroupButton, viewArticleButton);

        // Set the scene with the current TilePane
        stage.setScene(new Scene(this, 300, 200));
        stage.show();
    }
}
