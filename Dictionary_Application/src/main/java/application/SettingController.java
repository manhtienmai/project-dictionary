//package application;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Slider;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Properties;
//
//public class SettingController {
//    @FXML
//    private ComboBox<String> voiceComboBox;
//
//    @FXML
//    private Slider speedSlider;
//
//    @FXML
//    private void initialize() throws IOException {
//        voiceComboBox.getItems().addAll("Voice1", "Voice2");
//        loadSettings();
//    }
//
//    private void loadSettings() throws IOException {
//        voiceComboBox.setValue(voice);
//        speedSlider.setValue(speed);
//    }
//
//    public void saveSetting() {
//        String voiceSelected = voiceComboBox.getValue();
//        double speed = speedSlider.getValue();
//
//    }
//}
