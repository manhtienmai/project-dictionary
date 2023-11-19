package application.controller;

import application.util.DbConnection;
import application.Pronounciation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import application.Word;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SearchController extends BaseController{
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

    private final ObservableList<Word> allWords = FXCollections.observableArrayList();
    private final Pronounciation tts = new Pronounciation();
    @FXML
    private void initialize() {
//        already initialize when running application
        DbConnection connect = new DbConnection();
        Connection connectDB = connect.getDBConnection();
        onSpeakButtonClick();
        String query = "select word, description from av";

        //add listener for searchField
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                filterWords(newValue);
            }
        });


        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);

            while (queryResult.next()){
                String word = queryResult.getString("word");
                String definition = queryResult.getString("description");
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

    private void filterWords(String searchText) {
        ObservableList<Word> filteredWords = FXCollections.observableArrayList();
        if (searchText == null || searchText.isEmpty()) {
            filteredWords.addAll(allWords);
        }else {
            for (Word word : allWords) {
                if (word.getWord_target().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredWords.add(word);
                }
            }
        }

        wordView.setItems(filteredWords);
    }
    @FXML
    private void onSpeakButtonClick(){
        speakButton.setOnAction(event -> {
            Word selectedWord = wordView.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {
                String wordToSpeak = selectedWord.getWord_target();
                // to call non-static method on instance
                tts.synthesizeText(wordToSpeak);
//                SpeakTTS.synthesizeText(wordToSpeak);
            }
        });
    }

    @Override
    protected Stage getStage() {
        return (Stage) wordView.getScene().getWindow();
    }
}
