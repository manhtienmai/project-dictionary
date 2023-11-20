package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

public class ResultController {

    @FXML
    private Label comment;

    @FXML
    private Label marks;

    @FXML
    private ProgressIndicator correctProgress;

    @FXML
    private ProgressIndicator wrongProgress;

    @FXML
    private Button backButton;

    @FXML
    public Button againButton;

    public void initialize(int score, int totalQuestions) {
        // Tính toán các giá trị cần thiết
        double percentageCorrect = (double) score / totalQuestions;
        double percentageWrong = 1 - percentageCorrect;

        // Hiển thị số điểm
        marks.setText(score + "/" + totalQuestions);

        // Hiển thị % đúng và % sai
        correctProgress.setProgress(percentageCorrect);
        wrongProgress.setProgress(percentageWrong);

        // Hiển thị comment dựa trên điểm số
        if (percentageCorrect >= 0.75) {
            comment.setText("Excellent");
        } else {
            comment.setText("Try Hard");
        }
    }

    @FXML
    public void onBackButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/main.fxml"));
            Parent resultView = (Parent) loader.load();
            Stage primaryStage = (Stage) backButton.getScene().getWindow();
            primaryStage.setScene(new Scene(resultView));
            primaryStage.setTitle("Dictionary");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onPlayAgainClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/Game.fxml"));
            Parent resultView = (Parent) loader.load();
            Stage primaryStage = (Stage) againButton.getScene().getWindow();
            primaryStage.setScene(new Scene(resultView));
            primaryStage.setTitle("Game");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
