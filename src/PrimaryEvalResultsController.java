import java.awt.AWTException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class PrimaryEvalResultsController {
	
    @FXML // fx:id="vista3"
    private StackPane vista3; // Value injected by FXMLLoader	

	private static Timeline timeline;
	
	private static Integer timerVal = 60;
	private static Integer tempTimerVal;
	
	private Integer milliTimerVal = 9;

    @FXML
    private ResourceBundle resources;
    
    @FXML // fx:id="vista3"
    private StackPane primary_eval_vista; // Value injected by FXMLLoader	

    @FXML
    private URL location;
    
    @FXML
    void initialize() throws AWTException {
    	
    	System.out.println("Its working!");

    
    }
    	 	
	// print out the user's results
	
	// depending on the user's results: lead them to either passed or unpassed categories
}
