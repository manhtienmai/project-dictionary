package application.controller;

import application.util.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingController extends BaseController {
    @FXML
    private TextField wordField, meaningField, updateWordField, updateMeaningField, deleteWordField;

    @FXML
    private Button addWordButton;

    @FXML
    private Button updateWordButton;

    @FXML
    private Button deleteWordButton;

    @FXML
    private Button saveSettingButton;

    @FXML
    private void handleAddWord(ActionEvent event) throws SQLException {
        String word = wordField.getText();
        String meaning = meaningField.getText();

        if (!word.isEmpty() && !meaning.isEmpty()) {
            if (!wordExistsInDatabase(word)) {
                try {
                    addWordToDatabase(word, meaning);
                    wordField.clear();
                    meaningField.clear();
                    showAlert(Alert.AlertType.INFORMATION,"Success", "Word added successfully");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR,"Error", "Failed to add word.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Word already exists");
            }

        } else {
            showAlert(Alert.AlertType.WARNING,"Error", "Word and meaning cannot be empty");
        }
    }

    private void addWordToDatabase(String word, String meaning) throws SQLException {
        String sql = "INSERT INTO av (word, description) VALUES (?, ?)";
        DbConnection connect = new DbConnection();
        try (Connection connectDB = connect.getDBConnection()){
            PreparedStatement statement = connectDB.prepareStatement(sql);

            statement.setString(1, word);
            statement.setString(2, meaning);
            statement.executeUpdate();
        }
    }

    @FXML
    private void handleDeleteWord(ActionEvent event) {
        String word = deleteWordField.getText();
        if (!word.isEmpty()) {
            try {
                if (wordExistsInDatabase(word)) {
                    deleteWordFromDatabase(word);
                    deleteWordField.clear(); // Clear the field after deletion
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Word deleted successfully");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Word does not exist in database");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error while deleting.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Error", "Word field cannot be empty");
        }
    }

    private void deleteWordFromDatabase(String word) throws SQLException {
        String sql = "DELETE FROM av WHERE word = ?";
        try (Connection connectDB = new DbConnection().getDBConnection();
             PreparedStatement statement = connectDB.prepareStatement(sql)) {
            statement.setString(1, word);
            statement.executeUpdate();
        }
    }

    private boolean wordExistsInDatabase(String word) throws SQLException {
        String sql = "SELECT EXISTS (SELECT 1 FROM av WHERE word = ?)";
        try (Connection connectDB = new DbConnection().getDBConnection();
             PreparedStatement statement = connectDB.prepareStatement(sql)) {
            statement.setString(1, word);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }



    @FXML
    private void handleUpdateWord(ActionEvent event) throws SQLException {
        String word = updateWordField.getText();
        String newMeaning = updateMeaningField.getText();
        if (!word.isEmpty() && !newMeaning.isEmpty()) {
            if (wordExistsInDatabase(word)) {
                try {
                    updateWordInDatabase(word, newMeaning);
                    updateWordField.clear();
                    updateMeaningField.clear();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Word updated successfully");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR,"Error", "Error while updating word");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Word does not exist");
            }

        } else {
            showAlert(Alert.AlertType.WARNING, "Error", "Please fill both word and new meaning");
        }
    }
    private void updateWordInDatabase(String word, String newMeaning) throws SQLException {
        String sql = "UPDATE av SET description = ? WHERE word = ?";
        try (Connection connectDB = new DbConnection().getDBConnection();
             PreparedStatement statement = connectDB.prepareStatement(sql)) {
            statement.setString(1, newMeaning);
            statement.setString(2, word);
            statement.executeUpdate();
        }
    }

    @FXML
    private void handleSaveSetting() {
        try {
            System.out.println("save setting");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Save error", "Failed to save setting");
        }
    }

    @Override
    protected Stage getStage() {
        return (Stage) searchButton.getScene().getWindow();
    }
}