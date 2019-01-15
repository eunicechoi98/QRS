import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class evalOneFailedController {
	
	//Let’s work on improving your compressions!
	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
	
    @FXML // fx:id="failOneStackPane"
    private StackPane failOneStackPane; // Value injected by FXMLLoader	
    
    @FXML // fx:id="failOneStackPane"
    private Text evalOneText;
    
    @FXML // fx:id="failOneVBox"
    private VBox failOneVBox; // Value injected by FXMLLoader	
    
    @FXML // fx:id="evalOneFailButton"
    private Button evalOneFailButton;
    
	
	// save return date
	// save that user failed the first evaluation
	
	// move on to training
    @FXML
    void evalOneFailedNextAction(ActionEvent event) {
    	VistaNavigator.loadVista(VistaNavigator.VISTA_5);
    	System.out.println("evalOneFailedButton clicked");
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	System.out.println("Eval 1 Failed (evalOneFailedController) -> Training Vid (trainingVidController)");
    }
	
}
