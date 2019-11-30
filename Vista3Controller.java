import java.awt.AWTException;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
/*
 * Countdown to the CPR Evaluation (10 seconds)
 */
public class Vista3Controller {

	private static Integer timerVal = 10;
	private static Integer tempTimerVal;
	
	private static Timeline timeline;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML // fx:id="vista3"
    private StackPane vista3; // Value injected by FXMLLoader
    
    @FXML // fx:id="vista3VBox"
    private VBox vista3VBox; // Value injected by FXMLLoader
    
    @FXML // fx:id="vista3LabelOne"
    private Label vista3LabelOne; // Value injected by FXMLLoader
    
    @FXML // fx:id="timerLabel1"
    private Label timerLabel1;
    
    @FXML // fx:id="vista3Text"
    private Text vista3Text; // Value injected by FXMLLoader
   
    
    public static void setTimeline(Timeline parentTimeline) {
    	Vista3Controller.timeline = parentTimeline;
    	timerVal = 10;
    }

    @FXML
    void initialize() throws AWTException {
    	tempTimerVal = timerVal;
    	KeyFrame key = new KeyFrame(
    	        Duration.millis(1000),
    	        ae -> timerMethod());
    	if (timeline.getKeyFrames().isEmpty())
    		timeline.getKeyFrames().add(key);
    	else {
    		timeline.getKeyFrames().clear();
    		timeline.getKeyFrames().add(key);
    	}

    	timerLabel1.setText(Integer.toString(tempTimerVal));
    	timeline.setCycleCount(10);
    	timeline.playFromStart();
    	
    	System.out.println("Countdown to CPR Eval (Vista3Controller) -> CPR Evaluation (Vista4Controller)");
    }

	public void timerMethod() {
		tempTimerVal--;
		if (tempTimerVal == 0) {
	    	try {
	    		// calling mouse movements to open the laerdal file
				MouseMovements.startTest();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			VistaNavigator.loadVista(VistaNavigator.VISTA_4);
		}
		else {
			timerLabel1.setText(Integer.toString(tempTimerVal));
		}
	}
}


