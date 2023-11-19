package application.controller;

import application.util.DbConnection;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.sql.Connection;

public class MainController extends BaseController {

    @Override
    protected Stage getStage() {
        return (Stage) searchButton.getScene().getWindow();
    }

//    @FXML
//    public void initialize() {
//        DbConnection connect = new DbConnection();
//        Connection connectDB = connect.getDBConnection();
//    }
}