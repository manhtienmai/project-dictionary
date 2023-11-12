package application;

import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private Button searchButton;

    @FXML
    private Button translateButton;

    @FXML
    private Button favouriteButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button settingButton;

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
        loadView("/application/fxml/favourite.fxml", "Favourite");
    }

    @FXML
    private void onHistoryButtonClick() {
        loadView("/application/fxml/history.fxml", "History");
    }

    @FXML
    private void onSettingButtonClick(){
        loadView("/application/fxml/setting.fxml", "Setting");
    }



    private void loadView(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            Stage stage = (Stage) searchButton.getScene().getWindow();
            stage.setScene(new Scene(view));
            stage.setTitle(title);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
