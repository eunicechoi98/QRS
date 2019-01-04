/**
 * Sample Skeleton for 'vista1.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.control.RadioButton;

public class ForgotCodeVistaController {
	
	ToggleGroup toggleGroup = new ToggleGroup();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="vBoxVista1"
    private VBox vBoxVista1; // Value injected by FXMLLoader

    @FXML // fx:id="studyCodeTextBox"
    private TextField studyCodeTextBox; // Value injected by FXMLLoader
    
    @FXML // fx:id="controlRadio"
    private RadioButton controlRadio; // Value injected by FXMLLoader
    
    @FXML // fx:id="TrainingRadio"
    private RadioButton trainingRadio; // Value injected by FXMLLoader

    @FXML // fx:id="forgotCodeButton"
    private Button nextButton; // Value injected by FXMLLoader

    @FXML // fx:id="vista1"
    private StackPane vista1; // Value injected by FXMLLoader
    

    @FXML
    void cRadioAction(ActionEvent event) {

    }

    @FXML
    void tRadioAction(ActionEvent event) {

    }

    @FXML
    void forgotCodeAction(ActionEvent event) {
    	if (toggleGroup.getSelectedToggle() == controlRadio) {
        	DataIO.forgotCode(studyCodeTextBox.getText(), 0);
    		VistaNavigator.loadVista(VistaNavigator.ControlIntroVista);
    	}
    	else {
    		DataIO.forgotCode(studyCodeTextBox.getText(), 1);
    		VistaNavigator.loadVista(VistaNavigator.TrainingIntroVista);
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws InterruptedException {
    	controlRadio.setToggleGroup(toggleGroup);
    	trainingRadio.setToggleGroup(toggleGroup);
    	
        nextButton.setDisable(true);
        nextButton.disableProperty().bind(Bindings.createBooleanBinding(()
        		-> ((studyCodeTextBox.getText().isEmpty())
        				|| (toggleGroup.getSelectedToggle() == null)) , 
        		studyCodeTextBox.textProperty(),
        		toggleGroup.selectedToggleProperty()));
    }
}
