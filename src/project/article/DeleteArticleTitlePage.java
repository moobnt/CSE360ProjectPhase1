package project.article;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.util.Back;


/**
 * <p> BackUpArticlesByGroup class </p>
 * 
 * <p> Description: Handles the deletion of the article page specified by the user </p>
 * 
 * @version 1.00 2024-10-30 Initial baseline
 */

/**
 * @param articleName takes the article specified by user
 * 
 */

public class DeleteArticleTitlePage extends VBox {
    public DeleteArticleTitlePage(Stage stage, HelpArticleDatabase helpArticleDatabase) {
        stage.setTitle("Delete Article by Title");

        Label instructionLabel = new Label("Enter the title of the article to delete:");
        TextField titleField = new TextField();
        titleField.setPromptText("Enter article title");

        Button submitButton = new Button("View Articles");
        submitButton.setOnAction(event -> {
            String title = titleField.getText().trim();
            if (!title.isEmpty()) {
                new DeleteHelpArticlePage(stage, helpArticleDatabase, title);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a title to search.", ButtonType.OK);
                alert.showAndWait();
            }
        });
        
        Button back = new Button("Back");
        back.setOnAction(backEvent -> {
        	Back.back(stage);
        });

        getChildren().addAll(instructionLabel, titleField, submitButton, back);

        Scene s = new Scene(this, 400, 200);
        Back.pushBack(s, "Delete Article by Title");
        stage.setScene(s);
        stage.show();
    }
}
