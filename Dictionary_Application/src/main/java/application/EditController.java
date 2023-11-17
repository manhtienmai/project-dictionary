package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditController {
    @FXML
    private TextField wordField;

    @FXML
    private TextField meaningField;

    @FXML
    private Button addWordButton;

    @FXML
    private void handleAddWord(ActionEvent event) throws SQLException {
        String word = wordField.getText();
        String meaning = meaningField.getText();

        if (!word.isEmpty() && !meaning.isEmpty()) {
            addWordToDatabase(word, meaning);
            wordField.clear();
            meaningField.clear();
        } else {
            // Handle error
        }
    }

    private void addWordToDatabase(String word, String meaning) throws SQLException {
        String sql = "INSERT INTO words (word, detail) VALUES (?, ?)";
        DbConnection connect = new DbConnection();
        try (Connection connectDB = connect.getDBConnection()){
            PreparedStatement statement = connectDB.prepareStatement(sql);

            statement.setString(1, word);
            statement.setString(2, meaning);

            int dataAffected = statement.executeUpdate();
        }


    }

}
