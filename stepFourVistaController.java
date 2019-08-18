import javafx.event.ActionEvent;import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class stepFourVistaController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="stepOnePane"
    private StackPane stepFourStackPane; // Value injected by FXMLLoader
    
    @FXML // fx:id="stepOneNextButton"
    private Button stepFourNextButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="stepOneVBox"
    private VBox stepFourVBox; // Value injected by FXMLLoader
    
    @FXML // fx:id="stepOneText"
    private Label stepFourLabel; // Value injected by FXMLLoader
    
    @FXML // fx:id="stepOneLabel"
    private Text stepFourText; // Value injected by FXMLLoader
    
    
    @FXML
    void stepFourNextAction(ActionEvent event) {
    	VistaNavigator.loadVista(VistaNavigator.STEPSIXVISTA);
    }
 
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	System.out.println("Step Three (StepFourVistaController) -> Step Four (StepSixVistaController)");
    }
}
