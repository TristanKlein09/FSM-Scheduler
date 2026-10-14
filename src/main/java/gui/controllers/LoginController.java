package gui.controllers;

import client.HttpClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import models.HttpRequest;
import models.Pair;

import java.util.concurrent.ExecutionException;


public class LoginController {

    @FXML private TextField tfUsername;
    @FXML private TextField tfPassword;
    @FXML private Button buttonLogin;
    @FXML private Label labelPrompt;
    @FXML private MenuItem mitemSettings;
    @FXML private MenuItem mitemClose;
    @FXML private MenuItem mitemAbout;

    /**
     * This method is used to change the labelPrompt's text to show the user that an error has occurred. It changes the
     * colour of the prompt to the error colour as specified by the CSS file.
     * @param text The text to change the labelPrompt to
     */
    private void labelPromptError(String text) {
        labelPrompt.setText(text);
        //We must remove any other style on the label
        labelPrompt.getStyleClass().removeIf(str -> str.startsWith("label-prompt"));
        labelPrompt.getStyleClass().add("label-prompt-error");
    }

    //----- On Action Methods -----

    /**
     * This method is called when the login button is pressed
     */
    @FXML
    private void onLoginPress() throws ExecutionException, InterruptedException {
        System.out.println("PressedButton");
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        HttpClient httpClient = HttpClient.getInstance();
        Pair<Boolean, String> loginPair = httpClient.login(username, password);

        if (loginPair.key()) {
            //We can log the user in
        } else {
            labelPromptError(loginPair.value());
        }
    }

    @FXML void onSettingsPress() {}

    @FXML void onAboutPress() {}

    @FXML void onClosePress() {}
}
