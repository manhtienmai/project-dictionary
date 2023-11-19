package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class ResultController {

    @FXML
    private Label marks;

    @FXML
    private ProgressIndicator correctProgress;

    @FXML
    private ProgressIndicator wrongProgress;

    @FXML
    private Label comment;

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

}
