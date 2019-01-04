/**
 * Sample Skeleton for 'introTrainingVista.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class stepTwoVistaController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private StackPane stepTwoStackPane;
    
    @FXML
    private VBox stepTwoVBox;

    @FXML
    private Text stepTwoTextOne;
    
    @FXML
    private Text stepTwoTextTwo;
    
    @FXML
    private Text stepTwoTextThree;    
    
    @FXML
    private Button stepTwoNextButton;
    
    @FXML
    private Label stepTwoLabel;
    

    @FXML
    void stepTwoNextAction(ActionEvent event) {
    	VistaNavigator.loadVista(VistaNavigator.STEPTHREEVISTA);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        System.out.println("Step Two -> Step Two B (StepThreeVistaController)");
    }
}
