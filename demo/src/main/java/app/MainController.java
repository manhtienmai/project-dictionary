package app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class MainController {

    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<String> suggestionsListView; // Assuming you have a ListView for suggestions

    @FXML
    public void initialize() {
        // Initialize with listeners and setup
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // This is where you would filter the suggestions based on the newValue
            updateSuggestionsList(newValue);
        });
    }

    @FXML
    private void onSearchButtonClicked(MouseEvent event) {
        performSearch(searchTextField.getText());
    }

    private void updateSuggestionsList(String searchText) {
        // This method would be responsible for filtering suggestions based on the searchText
        // For demonstration purposes, let's assume you populate your suggestions here
        suggestionsListView.getItems().clear();
        if (!searchText.isEmpty()) {
            suggestionsListView.getItems().addAll(
                    "Suggestion 1 for " + searchText,
                    "Suggestion 2 for " + searchText,
                    "Suggestion 3 for " + searchText
                    // Add as many suggestions as needed
            );
        }
    }

    private void performSearch(String query) {
        // Logic to perform search goes here
        System.out.println("Performing search for: " + query);

        // Hide the suggestions list view
        suggestionsListView.setVisible(false);
    }

    @FXML
    private void onSuggestionClicked(MouseEvent event) {
        // When a suggestion is clicked, set its text in the search field and perform the search
        String selectedSuggestion = suggestionsListView.getSelectionModel().getSelectedItem();
        if (selectedSuggestion != null && !selectedSuggestion.isEmpty()) {
            searchTextField.setText(selectedSuggestion);
            performSearch(selectedSuggestion);
        }
    }
}
