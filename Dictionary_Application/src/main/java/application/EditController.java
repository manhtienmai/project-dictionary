package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditController {
    @FXML
    private TextField wordField;

    @FXML
    private Button backButton;
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
            System.out.println("Error in add word");
        }
    }

    private void addWordToDatabase(String word, String meaning) throws SQLException {
        String sql = "INSERT INTO tbl_edict (word, detail) VALUES (?, ?)";
        DbConnection connect = new DbConnection();
        try (Connection connectDB = connect.getDBConnection()){
            PreparedStatement statement = connectDB.prepareStatement(sql);

            statement.setString(1, word);
            statement.setString(2, meaning);

            int dataAffected = statement.executeUpdate();
        }


    }

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
}
