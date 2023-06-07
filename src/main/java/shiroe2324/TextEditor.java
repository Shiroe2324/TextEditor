package shiroe2324;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class TextEditor extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("text_editor.fxml"));
            Parent root = loader.load();
            TextEditorController controller = loader.getController();

            controller.setPrimaryStage(primaryStage);

            Image icon = new Image(getClass().getResourceAsStream("images/logo.png"));
            primaryStage.getIcons().add(icon);
    
            Scene scene = new Scene(root, 800, 600);

            primaryStage.setTitle("Nuevo Archivo");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
