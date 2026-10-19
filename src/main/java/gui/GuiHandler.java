package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiHandler {
    private static GuiHandler instance;

    /**
     * Responsible for displaying and configuring the login application view. It does this by loading in {@code login.fxml},
     * and applying the {@code styles.css} stylesheet.
     * @throws IOException Required by {@code loader.load()}
     */
    public void loadLoginView(Stage stage) throws IOException {
        //Loading in the FXML file
        FXMLLoader loader = new FXMLLoader(GuiHandler.class.getResource("/login.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        //Specifying the CSS file to be applied to the scene
        scene.getStylesheets().add(GuiHandler.class.getResource("/styles.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    /**
     * Responsible for displaying and configuring the main application view. It does this by loading in {@code schedulerMainView.fxml},
     * and applying the {@code styles.css} stylesheet.
     * @throws IOException Required by {@code loader.load()}
     */
    public void loadMainView() throws IOException {
        //Loading in the FXML file
        FXMLLoader loader = new FXMLLoader(GuiHandler.class.getResource("/schedulerMainView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        //Specifying the CSS file to be applied to the scene
        scene.getStylesheets().add(GuiHandler.class.getResource("/styles.css").toExternalForm());
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Scheduler");
        newStage.show();
    }

    /**
     * Responsible for displaying and configuring the main application view. It does this by loading in {@code loginSettings.fxml},
     * and applying the {@code styles.css} stylesheet.
     * @throws IOException Required by {@code loader.load()}
     */
    public void loadLoginSettingsView() throws IOException {
        //Loading in the FXML file
        FXMLLoader loader = new FXMLLoader(GuiHandler.class.getResource("/loginSettings.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        //Specifying the CSS file to be applied to the scene
        scene.getStylesheets().add(GuiHandler.class.getResource("/styles.css").toExternalForm());
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Login Settings");
        newStage.show();
    }

    /**
     *A method the closes the specified JavaFX stage
     * @param stage The {@link Stage} to be closed
     */
    public void closeView(Stage stage) {
        stage.close();
    }

    /**
     * Loads all the fonts in the font resource folder so that the css styling can be applied properly.
     */
    public void loadFonts() {

    }

    /**
     * Responsible for creating/returning the {@link GuiHandler} instance, as the class must be a singleton
     * @return {@link GuiHandler} instance
     */
    public static GuiHandler getInstance() {
        if (instance == null) {
            instance = new GuiHandler();
        }
        return instance;
    }

}
