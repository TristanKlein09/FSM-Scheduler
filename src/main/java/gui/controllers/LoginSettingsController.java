package gui.controllers;

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

    private void applySettings() {

    }

    //----- On Action Methods -----

    @FXML private void onApplyPress() {

    }

    @FXML private void onOKPress() {

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
