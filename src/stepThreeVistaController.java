
import javafx.event.ActionEvent;import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class stepThreeVistaController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="stepOnePane"
    private StackPane stepThreeStackPane; // Value injected by FXMLLoader
    
    @FXML // fx:id="stepOneNextButton"
    private Button stepThreeNextButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="stepOneVBox"
    private VBox stepThreeVBox; // Value injected by FXMLLoader
    
    @FXML // fx:id="stepOneLabel"
    private Text stepThreeTextOne; // Value injected by FXMLLoader
    
    @FXML // fx:id="stepOneText"
    private Text stepThreeTextTwo; // Value injected by FXMLLoader
    
    @FXML
    void stepThreeNextAction(ActionEvent event) {
    	VistaNavigator.loadVista(VistaNavigator.STEPFOURVISTA);
    }
 
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	System.out.println("Step Two B (StepThreeVistaController) -> Step Three (StepFourVistaController)");
    }
}
