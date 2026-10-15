package gui.controllers;

import client.HttpClient;
import gui.GuiHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Pair;
import util.Util;

import java.io.IOException;
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
        this.labelPrompt.setText(text);
        //We must remove any other style on the label
        this.labelPrompt.getStyleClass().removeIf(str -> str.startsWith("label-prompt"));
        this.labelPrompt.getStyleClass().add("label-prompt-error");
    }

    //----- On Action Methods -----

    //TODO: Display some sort of loading animation when waiting for the server response

    /**
     * This method is called when the login button is pressed. It determines whether the user can sign in. If they can,
     * it will load in the main view. Otherwise, it will inform the user that the login was unsuccessful.
     */
    @FXML
    private void onLoginPress() throws ExecutionException, InterruptedException, IOException {
        System.out.println("Pressed Login Button");
        String username = tfUsername.getText();
        String password = tfPassword.getText();


        //Determining whether the user is permitted to log in
        HttpClient httpClient = HttpClient.getInstance();
        Pair<Boolean, String> loginPair = httpClient.login(username, password);

        if (loginPair.key()) {
            //Load the scheduler main view and close the login view
            GuiHandler guiHandler = GuiHandler.getInstance();
            Stage stage = (Stage) this.buttonLogin.getScene().getWindow();
            guiHandler.closeView(stage);
            guiHandler.loadMainView();

        } else {
            labelPromptError(loginPair.value());
        }
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
