import java.awt.AWTException;
import java.net.URL;
import java.util.ArrayList;
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
/*   	
 		(Calling saveCPRData throws an error rn so just commented stuff out)
  
    	//Note that to get the primaryEval results saveCPRData(1) must be called first
    	DataIO.saveCPRData(1);
    	
    	//Initialize the ArrayList for the primary results
    	ArrayList<Integer> primaryResults = new ArrayList<>();
    	primaryResults = DataIO.getPrimaryResults();
    	
    	//Note that primary results is structured [mean rate,mean depth, % w adequate recoil]
    	int meanRate = primaryResults.get(0);
    	int meanDepth = primaryResults.get(1);
    	int adequateRecoil = primaryResults.get(2);
    	
*/


    
    }
    	 	
	// print out the user's results
	
	// depending on the user's results: lead them to either passed or unpassed categories
}
