import client.HttpClient;
import gui.controllers.LoginController;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


import java.io.*;
import java.util.Objects;

import static javafx.application.Application.launch;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        new Thread(() -> {
            HttpClient httpClient = HttpClient.getInstance();
            httpClient.startClient();
        }).start();

        launch(args);
    }
}
