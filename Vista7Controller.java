import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;

public class Vista7Controller {
	
	ToggleGroup toggleGroupScale = new ToggleGroup();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="scale8"
    private RadioButton scale8; // Value injected by FXMLLoader
    
    @FXML // fx:id="nextButton"
    private Button nextButton; // Value injected by FXMLLoader

    @FXML // fx:id="scale9"
    private RadioButton scale9; // Value injected by FXMLLoader

    @FXML // fx:id="scale6"
    private RadioButton scale6; // Value injected by FXMLLoader

    @FXML // fx:id="vista7"
    private StackPane vista7; // Value injected by FXMLLoader

    @FXML // fx:id="scale7"
    private RadioButton scale7; // Value injected by FXMLLoader

    @FXML // fx:id="scale4"
    private RadioButton scale4; // Value injected by FXMLLoader

    @FXML // fx:id="scale5"
    private RadioButton scale5; // Value injected by FXMLLoader

    @FXML // fx:id="scale2"
    private RadioButton scale2; // Value injected by FXMLLoader

    @FXML // fx:id="scale3"
    private RadioButton scale3; // Value injected by FXMLLoader

    @FXML // fx:id="scale1"
    private RadioButton scale1; // Value injected by FXMLLoader

    @FXML // fx:id="scale10"
    private RadioButton scale10; // Value injected by FXMLLoader

    @FXML
    void next3(ActionEvent event) {
    	int comfort = toggleGroupScale.getToggles().indexOf(toggleGroupScale.getSelectedToggle()) + 1;
    	DataIO.savePostQuestion(comfort);
 
    	VistaNavigator.loadVista(VistaNavigator.BreakVista);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	scale1.setToggleGroup(toggleGroupScale);
    	scale2.setToggleGroup(toggleGroupScale);
    	scale3.setToggleGroup(toggleGroupScale);
    	scale4.setToggleGroup(toggleGroupScale);
    	scale5.setToggleGroup(toggleGroupScale);
    	scale6.setToggleGroup(toggleGroupScale);
    	scale7.setToggleGroup(toggleGroupScale);
    	scale8.setToggleGroup(toggleGroupScale);
    	scale9.setToggleGroup(toggleGroupScale);
    	scale10.setToggleGroup(toggleGroupScale);
    	
    	nextButton.setDisable(true);
        nextButton.disableProperty().bind(Bindings.createBooleanBinding(() ->
        ((toggleGroupScale.getSelectedToggle() == null)) ,
        toggleGroupScale.selectedToggleProperty()));

 

    }
}