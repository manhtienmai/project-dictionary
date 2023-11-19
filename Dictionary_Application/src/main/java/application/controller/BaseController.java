package application.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
public abstract class BaseController {

    @FXML
    protected Button searchButton;
    @FXML
    protected Button translateButton;
    @FXML
    protected Button favouriteButton;
    @FXML
    protected Button historyButton;
    @FXML
    protected Button settingButton;
    @FXML
    protected Button gameButton;

    @FXML
    private void onSearchButtonClick() {
        loadView("/application/fxml/search.fxml", "Search");
    }

    @FXML
    private void onTranslateButtonClick() {
        loadView("/application/fxml/translate.fxml", "Translate");
    }

    @FXML
    private void onFavouriteButtonClick() {
        loadView("/application/fxml/edit.fxml", "Favourite");
    }

    @FXML
    private void onHistoryButtonClick() {
        loadView("/application/fxml/history.fxml", "History");
    }

    @FXML
    private void onSettingButtonClick(){
        loadView("/application/fxml/setting.fxml", "Setting");
    }


    @FXML
    private void onGameButtonClick(){
        loadView("/application/fxml/Game.fxml", "Game");
    }

    protected void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected void loadView(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            Stage stage = (Stage) searchButton.getScene().getWindow(); // You might need to adjust this
            stage.setScene(new Scene(view));
            stage.setTitle(title);
            stage.show();
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
//            showAlert(Alert.AlertType.ERROR,"Navigation Error", "Failed to open fxml.");
//        }
//    }

    protected abstract Stage getStage();
}
