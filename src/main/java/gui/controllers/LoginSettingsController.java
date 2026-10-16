package gui.controllers;

import client.HttpClient;
import gui.GuiHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Util;

import java.io.IOException;

public class LoginSettingsController {
    @FXML Button buttonCancel;
    @FXML Button buttonApply;
    @FXML Button buttonOK;
    @FXML TextField tfPort;
    @FXML TextField tfIPAddress;


    /**
     * This method runs when the view is loaded. It is used to populate the {@linkplain TextField}s.
     * @throws IOException Required by {@code fillInTextFields()}.
     */
    @FXML
    public void initialize() throws IOException {
        fillInTextFields();
    }

    /**
     * Used to populate the {@linkplain TextField}s by getting the values from the scheduler.properties file.
     * @throws IOException Required by {@code retrieveProperty()}
     */
    private void fillInTextFields() throws IOException {
        //.replace().trim() are needed as otherwise the value still contains speech marks
        this.tfIPAddress.setText(Util.retrieveProperty("server.ip").replace('"', ' ').trim());
        this.tfPort.setText(Util.retrieveProperty("server.port").replace('"', ' ').trim());
    }

    /**
     * Gets the values from the text fields and changes the corresponding property in the {@code scheduler.properties} file.
     * It then changes the server to be connected to.
     * @throws Exception Required by {@code changeProperty()}
     */
    private void applySettings() throws Exception {
        String portValue = this.tfPort.getText();
        String ipValue = this.tfIPAddress.getText();

        Util.changeProperty("server.ip", ipValue);
        Util.changeProperty("server.port", portValue);

        HttpClient client = HttpClient.getInstance();
        client.changeServer();
    }

    //----- On Action Methods -----

    /**
     * Applies the current settings while keeping the window open.
     * @throws Exception Required by {@code applySettings()}
     */
    @FXML private void onApplyPress() throws Exception {
        applySettings();
    }

    /**
     * Applies the current settings and closes the Login Settings window.
     * @throws Exception Required by {@code applySettings()}
     */
    @FXML private void onOKPress() throws Exception {
        applySettings();
        GuiHandler guiHandler = GuiHandler.getInstance();
        Stage stage = (Stage) this.buttonCancel.getScene().getWindow();
        guiHandler.closeView(stage);
    }

    /**
     * Closes the Login Settings view
     */
    @FXML private void onCancelPress() {
        GuiHandler guiHandler = GuiHandler.getInstance();
        Stage stage = (Stage) this.buttonCancel.getScene().getWindow();
        guiHandler.closeView(stage);
    }
}



