package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.concurrent.Task;
import javafx.application.Platform;
import javafx.stage.Stage;

public class TranslateController {
    @FXML
    private TextArea sourceTextArea;

    @FXML
    private TextArea targetTextArea;

    @FXML
    private Button translateButton;

    @FXML
    private Button backButton;

    private final Translation translation = new Translation();

    @FXML
    private void onTranslateButtonClick() {
        String sourceText = sourceTextArea.getText();
        translateText(sourceText, "en", "vi");
    }

    private void translateText(String text, String fromLang, String toLang) {
        Task<String> translationTask = new Task<>() {
            @Override
            protected String call() {
                return translation.translate(fromLang, toLang, text);
            }
        };

        translationTask.setOnSucceeded(event ->
                Platform.runLater(() -> targetTextArea.setText(translationTask.getValue()))
        );

        translationTask.setOnFailed(event ->
                Platform.runLater(() -> targetTextArea.setText("Translation failed."))
        );

        new Thread(translationTask).start();
    }

    @FXML
    private void onBackButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/main.fxml"));
            Parent translateView = (Parent) loader.load();
            Stage primaryStage = (Stage) translateButton.getScene().getWindow();
            primaryStage.setScene(new Scene(translateView));
            primaryStage.setTitle("Dictionary");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
