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
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class stepFiveVistaController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="stepOnePane"
    private StackPane stepFiveStackPane; // Value injected by FXMLLoader
    
    @FXML // fx:id="stepOneNextButton"
    private Button stepFiveNextButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="stepOneVBox"
    private VBox stepFiveVBox; // Value injected by FXMLLoader
    
    @FXML // fx:id="stepOneText"
    private Label stepFiveLabel; // Value injected by FXMLLoader
    
    @FXML // fx:id="stepOneLabel"
    private Text stepFiveText; // Value injected by FXMLLoader
    
    @FXML // fx:id="stepOneLabel"
    private ImageView stepFiveImageView; // Value injected by FXMLLoader
    
    
    @FXML
    void stepFiveNextAction(ActionEvent event) {
    	VistaNavigator.loadVista(VistaNavigator.STEPSIXVISTA);
    }
 
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	System.out.println("Step Four (StepFiveVistaController) -> Step Five (StepSixVistaController)");
    }
}
