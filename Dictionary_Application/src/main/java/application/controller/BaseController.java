package application.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public abstract class BaseController {

    @FXML
    protected Button searchButton, translateButton, settingButton, gameButton, homeButton, game2Button;

    @FXML
    protected StackPane contentPane;

    @FXML
    private void onSearchButtonClick() {
        loadView("/application/fxml/search.fxml", "Search");
    }

    @FXML
    private void onTranslateButtonClick() {
        loadView("/application/fxml/translate.fxml", "Translate");
    }

    @FXML
    private void onHomeButtonClick() {
        loadView("/application/fxml/main.fxml", "Dictionary");
    }

    @FXML
    private void onGame2ButtonClick() {
        loadView("/application/fxml/game2.fxml", "Dictionary");
    }

    @FXML
    private void onSettingButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/setting.fxml"));
            Parent root = loader.load();
            SettingController controller = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.setTitle("Settings");
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onGameButtonClick(){
        loadView("/application/fxml/game.fxml", "Setting");
    }

    protected void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/application/style/alert.css").toExternalForm());
        alert.showAndWait();
    }

    protected void loadView(String fxmlPath, String title) {
        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
//            Parent view = loader.load();
//            Stage stage = (Stage) searchButton.getScene().getWindow();
//            stage.setScene(new Scene(view));
//            stage.setTitle(title);
//            stage.show();
            Node content = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentPane.getChildren().setAll(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    protected void navigateTo(String fxmlPath) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
//            Parent view = loader.load();
//            Stage stage = (Stage) getStage();
//            stage.setScene(new Scene(view));
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//            showAlert(Alert.AlertType.ERROR,"Error", "Failed to open fxml.");
//        }
//    }

//    protected abstract Stage getStage();
}
