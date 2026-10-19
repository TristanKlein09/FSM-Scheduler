package gui.controllers;

import client.HttpClient;
import gui.GuiHandler;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Pair;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LoginController {

    @FXML private TextField tfUsername;
    @FXML private PasswordField tfPassword;
    @FXML private Button buttonLogin;
    @FXML private Label labelPrompt;
    @FXML private MenuItem mitemSettings;
    @FXML private MenuItem mitemClose;
    @FXML private MenuItem mitemAbout;

    @FXML
    public void initialize() {
        //----- Animating text fields -----
        tfUsername.focusedProperty().addListener((obs, oldVal, focused) -> {
            Timeline animation = new Timeline(
                    new KeyFrame(Duration.millis(200),
                            new KeyValue(
                                    tfUsername.prefWidthProperty(), //The property to animate
                                    focused ? 220 : 200, //New Length, Original Length
                                    Interpolator.EASE_BOTH //Type of animation
                            )
                    )
            );
            animation.play();
        });

        tfPassword.focusedProperty().addListener((obs, oldVal, focused) -> {
            Timeline animation = new Timeline(
                    new KeyFrame(Duration.millis(200),
                            new KeyValue(
                                    tfPassword.prefWidthProperty(),
                                    focused ? 220 : 200,
                                    Interpolator.EASE_BOTH
                            )
                    )
            );
            animation.play();
        });
    }

    /**
     * This method is used to change the labelPrompt's text to show the user that an error has occurred. It changes the
     * colour of the prompt to the error colour as specified by the CSS file.
     * @param text The text to change the labelPrompt to
     */
    private void labelPromptError(String text) {
        this.labelPrompt.setText(text);
        this.labelPrompt.getStyleClass().add("label-error");
    }

    /**
     * This method is used to change the text & password fields colour to show the user that an error has occurred.
     */
    private void tfError() {
        this.tfPassword.getStyleClass().add("password-field-error");
        this.tfUsername.getStyleClass().add("text-field-error");
    }

    /**
     * This method changes the buttons graphic to a {@link ProgressIndicator} to indicate that something is loading.
     */
    private void buttonSetLoading() {
        ProgressIndicator circle = new ProgressIndicator();
        circle.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        circle.setPrefSize(24, 24);

        buttonLogin.getStyleClass().add("login-button");
        buttonLogin.setGraphic(circle);
        buttonLogin.setText("");
        buttonLogin.setDisable(true);
    }

    /**
     * This method removes any graphic from the button to restore it to its original state.
     */
    private void buttonRestoreLoading() {
        buttonLogin.getStyleClass().remove("login-button");
        buttonLogin.setGraphic(null);
        buttonLogin.setText("Login");
        buttonLogin.setDisable(false);
    }

    //----- On Action Methods -----

    //TODO: Display some sort of loading animation when waiting for the server response
    @FXML
    private void onLoginPress() throws ExecutionException, InterruptedException, IOException {
        System.out.println("Pressed Login Button");
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        //If we do not make it run on a new thread, the application hangs momentarily and stops responding
        new Thread(() -> {
            try {
                Platform.runLater(this::buttonSetLoading);
                //Determining whether the user is permitted to log in
                HttpClient httpClient = HttpClient.getInstance();
                Pair<Boolean, String> loginPair = httpClient.login(username, password);

                if (loginPair.key()) {
                    //Load the scheduler main view and close the login view
                    GuiHandler guiHandler = GuiHandler.getInstance();
                    Stage stage = (Stage) this.buttonLogin.getScene().getWindow();
                    Platform.runLater(() -> {
                        try {
                            guiHandler.closeView(stage);
                            guiHandler.loadMainView();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                } else {
                    Platform.runLater(() -> {
                        buttonRestoreLoading();
                        labelPromptError(loginPair.value());
                        tfError();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();



    }

    /**
     *This method is called when the settings menu item is pressed. Its role is to open the login settings menu, so that
     * the user can specify the port or the ip address of the server.
     * @throws IOException Required by {@code loadLoginSettingsView()}
     */
    @FXML void onSettingsPress() throws IOException {
        GuiHandler guiHandler = GuiHandler.getInstance();
        guiHandler.loadLoginSettingsView();
    }

    @FXML void onAboutPress() {}

    @FXML void onClosePress() {}
}
