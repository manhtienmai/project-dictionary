package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import application.Word;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SearchController {
    @FXML
    private TableView<Word> wordView;
    @FXML
    private TableColumn<Word, String> wordColumn;
    @FXML
    private TableColumn<Word, String> meaningColumn;
    @FXML
    private TextField searchField;

    @FXML
    private Button speakButton;

    @FXML
    private Button backButton;
    private final ObservableList<Word> allWords = FXCollections.observableArrayList();

    @FXML
    private void onBackButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/main.fxml"));
            Parent translateView = (Parent) loader.load();
            Stage primaryStage = (Stage) backButton.getScene().getWindow();
            primaryStage.setScene(new Scene(translateView));
            primaryStage.setTitle("Dictionary");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        DbConnection connect = new DbConnection();
        Connection connectDB = connect.getDBConnection();
        String query = "select word, detail from tbl_edict";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);

            while (queryResult.next()){
                String word = queryResult.getString("word");
                String definition = queryResult.getString("detail");
                allWords.add(new Word(word, definition));
            }

            wordColumn.setCellValueFactory(new PropertyValueFactory<>("word_target"));
            meaningColumn.setCellValueFactory(new PropertyValueFactory<>("word_explain"));

            wordView.setItems(allWords);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Initialize method");
    }

    @FXML
    private void onSpeakButtonClick(){
        speakButton.setOnAction(event -> {
            Word selectedWord = wordView.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {
                String wordToSpeak = selectedWord.getWord_target();
                SpeakTTS.synthesizeText(wordToSpeak);
            }
        });
    }
}
