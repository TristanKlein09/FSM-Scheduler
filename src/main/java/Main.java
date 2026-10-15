import client.HttpClient;
import gui.GuiHandler;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GuiHandler.getInstance().loadLoginView(stage);
    }

    public static void main(String[] args) throws IOException {
        new Thread(() -> {
            HttpClient httpClient = HttpClient.getInstance();
            httpClient.startClient();
        }).start();

        launch(args);
    }
}
