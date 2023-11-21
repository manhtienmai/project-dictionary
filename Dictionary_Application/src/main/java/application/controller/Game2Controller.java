package application.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Game2Controller {
    @FXML
    private Button EngButton1;

    @FXML
    private Button EngButton2;

    @FXML
    private Button EngButton3;

    @FXML
    private Button EngButton4;

    @FXML
    private Button VieButton1;

    @FXML
    private Button VieButton2;

    @FXML
    private Button VieButton3;

    @FXML
    private Button VieButton4;

    @FXML
    private Button BackButton;

    @FXML
    private AnchorPane AnchorPane;

    private List<Question> questions;
    private int currentQuestionIndex;

    private Map<String, String> engVie = new HashMap<>();

    private String selectedAnswer1;
    private String selectedAnswer2;
    private int clickCount = 0;
    private int score = 0;
    private int maxWrongAttempts = 3;
    private int wrongAttempts = 0;

    private int numberOfDummyOptions = 1;

    public void initialize() {
        // Load questions from file
        loadQuestionsFromFile("/application/dictionary/dictionary.txt");

        // Shuffle questions
        Collections.shuffle(questions);

        // Display the first question
        showQuestion();
    }

    private void loadQuestionsFromFile(String filePath) {
        questions = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream(filePath);
             Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\t");
                if (parts.length == 2) {
                    String englishWord = parts[0].trim();
                    String correctAnswer = parts[1].trim();

                    Question question = new Question(englishWord, null, correctAnswer);
                    questions.add(question);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Add error handling
        }
    }

//    private List<String> generateDummyOptions(Question currentQuestion) {
//        List<String> dummyOptions = new ArrayList<>();
//
//        String correctAnswer = currentQuestion.getCorrectAnswer();
//
//        List<Question> newQuestions = new ArrayList<>(questions);
//        Collections.shuffle(newQuestions);
//
//        int count = 0;
//
//        for (Question q : newQuestions) {
//            if (!q.equals(currentQuestion)) {
//                dummyOptions.add(q.getCorrectAnswer());
//                count++;
//
//                if (count >= numberOfDummyOptions) {
//                    break;
//                }
//            }
//        }
//
//
//        dummyOptions.add(correctAnswer);
//
//        return dummyOptions;
//    }

    private void showQuestion() {
        if (currentQuestionIndex < questions.size() && wrongAttempts < maxWrongAttempts) {
            Question question1 = questions.get(currentQuestionIndex);
            engVie.put(question1.getQuestionText(), question1.getCorrectAnswer());
            Collections.shuffle(questions);

            Question question2 = questions.get(currentQuestionIndex);
            engVie.put(question2.getQuestionText(), question2.getCorrectAnswer());
            Collections.shuffle(questions);

            Question question3 = questions.get(currentQuestionIndex);

            engVie.put(question3.getQuestionText(), question3.getCorrectAnswer());
            Collections.shuffle(questions);

            Question question4 = questions.get(currentQuestionIndex);
            engVie.put(question4.getQuestionText(), question4.getCorrectAnswer());

            List<String> shuffleWord = new ArrayList<>();
            shuffleWord.add(question1.getQuestionText());
            shuffleWord.add(question2.getQuestionText());
            shuffleWord.add(question3.getQuestionText());
            shuffleWord.add(question4.getQuestionText());
            shuffleWord.add(question1.getCorrectAnswer());
            shuffleWord.add(question2.getCorrectAnswer());
            shuffleWord.add(question3.getCorrectAnswer());
            shuffleWord.add(question4.getCorrectAnswer());

            Collections.shuffle(shuffleWord);

            List<Button> answerButtons = List.of(EngButton1, EngButton2, EngButton3, EngButton4, VieButton1, VieButton2, VieButton3, VieButton4);

            for (int i = 0; i < shuffleWord.size(); i++) {
                answerButtons.get(i).setText(shuffleWord.get(i));
            }
        } else {
            // End of the game
//            questionLabel.setText("Game Over");
            EngButton1.setDisable(true);
            EngButton2.setDisable(true);
            EngButton3.setDisable(true);
            EngButton4.setDisable(true);
            VieButton1.setDisable(true);
            VieButton2.setDisable(true);
            VieButton3.setDisable(true);
            VieButton4.setDisable(true);

            // Display the player's score
            showResult(score, currentQuestionIndex);
        }
    }
    private void showResult(int score, int totalQuestions) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/result.fxml"));
            AnchorPane resultPane = loader.load();

            // Truy cập controller của ResultController
            application.controller.ResultController resultController = loader.getController();

            // Pass thông tin kết quả
            resultController.initialize(score, totalQuestions);

            // Hiển thị cửa sổ kết quả
            Stage resultStage = new Stage();
            Scene scene = new Scene(resultPane);
            resultStage.setScene(scene);
            resultStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Xử lý nếu có lỗi khi tạo cửa sổ kết quả
        }
    }
    // ẩn button
    private void hideButton(AnchorPane anchorPane, String selectedAnswer) {
        anchorPane.getChildren().removeIf(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                return button.getText().equals(selectedAnswer);
            }
            return false;
        });
    }


    private void checkAnswer(String s1, String s2) {
        for (Map.Entry<String, String> entry : engVie.entrySet()) {
            if ((s1.equals(entry.getKey()) && s2.equals(entry.getValue())) || ((s1.equals(entry.getValue()) && s2.equals(entry.getKey())))) {
                score++;  // Increase the score for correct answer
                hideButton(AnchorPane, s1);
                hideButton(AnchorPane, s2);
            } else {
                wrongAttempts++;
            }
        }
    }

    @FXML
    private void handleAnswerButtonClick(javafx.event.ActionEvent event) {
        ++clickCount;
        Button clickedButton = (Button) event.getSource();
        if (clickCount == 1) {
            selectedAnswer1 = clickedButton.getText();
        } else if (clickCount == 2) {
            selectedAnswer2 = clickedButton.getText();
            checkAnswer(selectedAnswer1, selectedAnswer2);
            clickCount = 0;
        }
    }

    @FXML
    private void Eng1Clicked() {
        handleAnswerButtonClick(new javafx.event.ActionEvent(EngButton1, null));
    }

    @FXML
    private void Eng2Clicked() {
        handleAnswerButtonClick(new javafx.event.ActionEvent(EngButton2, null));
    }

    @FXML
    private void Eng3Clicked() {
        handleAnswerButtonClick(new javafx.event.ActionEvent(EngButton3, null));
    }

    @FXML
    private void Eng4Clicked() {
        handleAnswerButtonClick(new javafx.event.ActionEvent(EngButton4, null));
    }

    @FXML
    private void Vie1Clicked() {
        handleAnswerButtonClick(new javafx.event.ActionEvent(VieButton1, null));
    }

    @FXML
    private void Vie2Clicked() {
        handleAnswerButtonClick(new javafx.event.ActionEvent(VieButton2, null));
    }

    @FXML
    private void Vie3Clicked() {
        handleAnswerButtonClick(new javafx.event.ActionEvent(VieButton3, null));
    }

    @FXML
    private void Vie4Clicked() {
        handleAnswerButtonClick(new javafx.event.ActionEvent(VieButton4, null));
    }

    @FXML
    private void onBackButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/main.fxml"));
            Parent gameView = (Parent) loader.load();
            Stage primaryStage = (Stage) BackButton.getScene().getWindow();
            primaryStage.setScene(new Scene(gameView));
            primaryStage.setTitle("Dictionary");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
