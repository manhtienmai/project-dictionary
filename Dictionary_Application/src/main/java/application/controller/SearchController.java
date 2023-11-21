package application.controller;

import application.model.BKTree;
import application.model.EnhancedWord;
import application.model.Word;
import application.service.DictionaryAPI;
import application.service.Pronounciation;
import application.util.DbConnection;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javafx.scene.web.WebView;
public class SearchController extends BaseController {
    @FXML
    private TableView<EnhancedWord> wordView;
    @FXML
    private TableColumn<EnhancedWord, String> wordColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Button speakButton;

    @FXML
    private WebView webViewWord;

    @FXML
    private ToggleButton optionData;

    private boolean useAPI = false;

    private final ObservableList<EnhancedWord> allWords = FXCollections.observableArrayList();
    private final Pronounciation tts = new Pronounciation();
    private BKTree bkTree;

    // debounce to limit rate filter operation
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final long DELAY = 400;

    @FXML
    private void initialize() {
        onSpeakButtonClick();
        //        handleOptionDataToggle();
        fetchDataFromDB();
        wordView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showWordDetails(newValue)
        );

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            executor.schedule(() -> {
                Platform.runLater(() -> filterWords(newValue.trim()));
            }, DELAY, TimeUnit.MILLISECONDS);
        });

        bkTree = new BKTree("");
        for (EnhancedWord word : allWords) {
            bkTree.insert(word.getWord_target());
        }
        System.out.println("Initialize");
    }

    private void fetchDataFromDB() {
        try (Connection connectDB = DbConnection.getInstance().getConnection()) {
            String query = "select word, pronounce, description  from av";
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);

            while (queryResult.next()) {
                String word = queryResult.getString("word");
                String definition = queryResult.getString("description");
                String pronounceApi = queryResult.getString("pronounce");
                allWords.add(new EnhancedWord(word, definition, pronounceApi));
            }

            wordColumn.setCellValueFactory(new PropertyValueFactory<>("word_target"));
            wordView.setItems(allWords);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in fetching data from db");
        }
    }

    @FXML
    private void handleOptionDataToggle() {
        useAPI = optionData.isSelected();
        if (!useAPI) {
            fetchDataFromDB();
        }
    }

    private void showWordDetails(EnhancedWord word) {
        if (word != null) {
            if (useAPI) {
                fetchWordDetailsFromAPI(word.getWord_target());
            } else {
                String wordDetails = generateHtmlForWord(word);
                webViewWord.getEngine().loadContent(wordDetails);
            }
        }
    }

    private void fetchWordDetailsFromAPI(String word) {
        new Thread(() -> {
            try {
                JsonNode wordData = DictionaryAPI.getWords(word);
                String wordDetailsHtml = generateHtmlFromAPI(wordData);

                Platform.runLater(() -> {
                    webViewWord.getEngine().loadContent(wordDetailsHtml);
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    webViewWord.getEngine().loadContent("Error loading word from api");
                });
            }
        }).start();
    }

    private String generateHtmlFromAPI(JsonNode wordData) {
        if (!wordData.isArray()) {
            return "<p>No data available</p>";
        }
        StringBuilder html = new StringBuilder("<html><head><style>");
        html.append("body { font-family: 'Georgia', serif; background-color: #f4f4f4; color: #333; }");
        html.append("h2 { color: #4a86e8; }");
        html.append("h3 { color: #6a5acd; }");
        html.append("p { font-size: 16px; }");
        html.append(".example { font-style: italic;color: #008000; }");
        html.append("</style></head><body>");

        for (JsonNode wordNode : wordData) {
            String word = wordNode.path("word").asText("N/A");
            String phonetic = wordNode.path("phonetic").asText("N/A");
            html.append("<h2>").append(word).append(" [").append(phonetic).append("]</h2>");
            JsonNode meaningsNode = wordNode.path("meanings");
            for (JsonNode meaning : meaningsNode) {
                String partOfSpeech = meaning.path("partOfSpeech").asText("N/A");
                html.append("<h3>").append(partOfSpeech).append("</h3>");
                JsonNode definitionsNode = meaning.path("definitions");
                for (JsonNode definitionNode : definitionsNode) {
                    String definition = definitionNode.path("definition").asText("N/A");
                    html.append("<p><b>Definition:</b> ").append(definition).append("</p>");
                    String example = definitionNode.path("example").asText("");
                    if (!example.isEmpty()) {
                        html.append("<p class='example'><i>Example:</i> ").append(example).append("</p>");
                    }
                }
            }
        }
        html.append("</body></html>");
        return html.toString();
    }




    private String generateHtmlForWord(EnhancedWord word) {
        String wordName = word.getWord_target();
        String css = "<style>" +
                "  body { font-family: Arial, sans-serif; margin: 10px; }" +
                "  h1 { color: navy; margin-bottom: 0; }" +
                "  p { margin-top: 5px; font-size: 14px; }" +
                "  .word-container { border: 1px solid #ddd; padding: 15px; border-radius: 8px; background-color: #f9f9f9; }" +
                "</style>";

        String html = "<html><head>" + css + "</head><body>" +
                "<div class='word-container'>" +
                "<h1>" + word.getWord_target() + "</h1>" +
                "<p>" + word.getPronounce() + "</p>" +
                "<p>" + word.getWord_explain() + "</p>" +
                "</div>" +
                "</body></html>";
        return html;
    }

    private void filterWords(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            wordView.setItems(FXCollections.observableArrayList());
            return;
        }

        List<String> results = new ArrayList<>();
        bkTree.search(searchText, 1, results);

        ObservableList<EnhancedWord> filteredWords = allWords.stream()
                .filter(word -> results.contains(word.getWord_target()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        wordView.setItems(filteredWords);
    }

    @FXML
    private void onSpeakButtonClick() {
        speakButton.setOnAction(event -> {
            Word selectedWord = wordView.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {
                String wordToSpeak = selectedWord.getWord_target();
                tts.synText(wordToSpeak);
//                SpeakTTS.synthesizeText(wordToSpeak);
            }
        });
    }
}
//    private void filterWords(String searchText) {
//        ObservableList<EnhancedWord> filteredWords = FXCollections.observableArrayList();
//        if (searchText == null || searchText.trim().isEmpty()) {
//            filteredWords.addAll(allWords);
//        } else {
//            String lowerText = searchText.toLowerCase();
//            List<EnhancedWord> exactWords = allWords.stream()
//                    .filter(word -> word.getWord_target().toLowerCase().startsWith(lowerText))
//                    .toList();
//
//            if (!exactWords.isEmpty()) {
//                filteredWords.addAll(exactWords);
//            } else {
//                allWords.stream().filter(word -> word.getWord_target().toLowerCase().contains(lowerText)
//                                || levenshteinDistance(lowerText, word.getWord_target().toLowerCase()) <= 1)
//                        .limit(10)
//                        .forEach(filteredWords::add);
//            }
//        }
//
//        wordView.setItems(filteredWords);
//    }
//    private void filterWords(String searchText) {
//        ObservableList<Word> filteredWords = FXCollections.observableArrayList();
//        if (searchText == null || searchText.isEmpty()) {
//            filteredWords.addAll(allWords);
//        }else {
//            for (Word word : allWords) {
//                if (word.getWord_target().toLowerCase().contains(searchText.toLowerCase())) {
//                    filteredWords.add(word);
//                }
//            }
//        }
//        wordView.setItems(filteredWords);
//    }

//    private void filterWords(String searchText) {
//        ObservableList<Word> filteredWords = FXCollections.observableArrayList();
//        if (searchText == null || searchText.isEmpty()) {
//            filteredWords.addAll(allWords);
//        } else {
//            filteredWords.addAll(allWords.stream()
//                    .filter(word -> word.getWord_target().toLowerCase().contains(searchText.toLowerCase()))
//                    .toList());
//
//            // If no exact matches found, get suggestions
//            if (filteredWords.isEmpty()) {
//                List<String> suggestions = getSuggestions(searchText, 2); // Threshold set to 2
//                for (String suggestion : suggestions) {
//                    // Find the Word object corresponding to each suggestion and add to filteredWords
//                    allWords.stream()
//                            .filter(word -> word.getWord_target().equals(suggestion))
//                            .findFirst()
//                            .ifPresent(filteredWords::add);
//                }
//            }
//        }
//        wordView.setItems(filteredWords);
//    }



//    @Override
//    protected Stage getStage() {
//        return (Stage) wordView.getScene().getWindow();
//    }

//    private List<String> getSuggestions(String inputWord, int threshold) {
//        List<String> suggestions = new ArrayList<>();
//
//        for (Word word : allWords) {
//            int distance = levenshteinDistance(inputWord.toLowerCase(), word.getWord_target().toLowerCase());
//            if (distance <= threshold) {
//                suggestions.add(word.getWord_target());
//                if (suggestions.size() >= 10) break; // limit just 10 suggestions.
//            }
//        }
//
//        return suggestions;
//    }
    /*
    Pseudocode levenshtein distance:
    int LevenshteinDistance(char s[1..m], char t[1..n])
    // d is a table with m+1 rows and n+1 columns
    declare int d[0..m, 0..n]

    for i from 0 to m
      d[i, 0]:= i
    for j from 0 to n
      d[0, j]:= j

    for i from 1 to m
      for j from 1 to n
      {
     if s[i] = t[j] then cost:= 0
     else cost:= 1
     d[i, j]:= minimum(
      d[i-1, j] + 1, // trường hợp xoá
      d[i, j-1] + 1, // trường hợp thêm
      d[i-1, j-1] + cost  // trường hợp thay thế
     )
      }

     return d[m, n]

     */
