package application;

import application.util.DbConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

//    private static ObservableList<EnhancedWord> preData;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/application/fxml/game2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
//        preLoadData();
    }

//    private void preLoadData() {
//        new Thread(() -> {
//           try (Connection connectDB = DbConnection.getInstance().getConnection()) {
//               Statement statement = connectDB.createStatement();
//               ResultSet query = statement.executeQuery("select word, pronounce, description from av");
//
//               preData = FXCollections.observableArrayList();
//               while(query.next()) {
//                   preData.add(new EnhancedWord(
//                           query.getString("word"),
//                           query.getString("description"),
//                           query.getString("pronounce")
//
//                   ));
//               }
//           } catch (SQLException e) {
//               throw new RuntimeException(e);
//           }
//        }).start();
//    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/application/fxml/DictionaryApp.fxml"));
//        primaryStage.setTitle("Dictionary App");
//        primaryStage.setScene(new Scene(root, 500, 500));
//        primaryStage.show();
//    }
//
//    public static ObservableList<EnhancedWord> getPreData() {
//        return preData;
//    }
    public static void main(String[] args) {
        DbConnection dbConnection = DbConnection.getInstance();
        launch();
    }
}