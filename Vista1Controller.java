import javafx.event.ActionEvent;import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Controller class for the first vista.
 */
public class Vista1Controller {

	@FXML // fx:id="studyCodeTextBox"
    private TextField studyCodeTextBox; // Value injected by FXMLLoader

    @FXML // fx:id="forgotCodeButton"
    private Button forgotCodeButton; // Value injected by FXMLLoader
    
    @FXML
    private VBox vBoxVista1;
    
    @FXML
    private StackPane vista1;
    
    @FXML // fx:id="errorLabel"
    private Label errorLabel; // Value injected by FXMLLoader
    
    @FXML
    void forgotCodeAction(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.ForgotCodeVista);
    }
    
    @FXML
    void onEnter(ActionEvent event) {
    	String userInput = studyCodeTextBox.getText();
    	if (userInput.isEmpty() == false) {
    		if (DataIO.validateUsername(userInput)) {
	    		VistaNavigator.loadVista(VistaNavigator.VISTA_2);
	    	}
    	}
    }
    
    @FXML
    void initialize() {
    	System.out.println("Sign In (Vista 1) -> Step One");
    }

	public static void main(String[] args) {
		
		
	}

}
