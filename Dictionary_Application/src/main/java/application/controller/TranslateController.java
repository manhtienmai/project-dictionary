package application.controller;

import application.service.Pronounciation;
import application.service.Translation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.concurrent.Task;
import javafx.application.Platform;

public class TranslateController extends BaseController {
    @FXML
    private TextArea sourceTextArea, targetTextArea;

    @FXML
    private Button translateButton, pronounceButton;

    private final Translation translation = new Translation();
    private final Pronounciation pronounciation = new Pronounciation();

    @FXML
    private void onTranslateButtonClick() {
        String sourceText = sourceTextArea.getText();
        translateText(sourceText, "en", "vi");
    }

    @FXML
    private void onPronounceButtonClick() {
        String textPronounce = sourceTextArea.getText();
        if (!textPronounce.isBlank()) {
            pronounciation.synText(textPronounce);
        }
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

//    @Override
//    protected Stage getStage() {
//        return (Stage) translateButton.getScene().getWindow();
//    }
}
