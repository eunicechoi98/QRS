import java.awt.AWTException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class evalTwoController {

	private static Timeline timeline;
	
	private static Integer timerVal = 15;
	private static Integer tempTimerVal;
	
	private Integer milliTimerVal = 9;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label evalTwoTimerLabel;

    @FXML
    private StackPane evalTwoVista;
    
    public static void setTimeline(Timeline parentTimeline) {
    	evalTwoController.timeline = parentTimeline;
    	timerVal = 15; //make sure this matches above
    }
    

    @FXML
    
    void initialize() {
    	
    	tempTimerVal = timerVal;
    	KeyFrame key = new KeyFrame(
    	        Duration.millis(1050),
    	        ae -> timerMethod());
    	if (timeline.getKeyFrames().isEmpty())
    		timeline.getKeyFrames().add(key);
    	else {
    		timeline.getKeyFrames().clear();
    		timeline.getKeyFrames().add(key);
    	}
    	evalTwoTimerLabel.setText(Integer.toString(tempTimerVal) + ":0");
    	timerMethod();
    	timeline.setCycleCount(tempTimerVal);
    	timeline.play();
  
    }

	public void timerMethod() {
		tempTimerVal--;
		milliTimerVal = 9;
		Timeline timeline2 = new Timeline(new KeyFrame(
    	        Duration.millis(105),
    	        ae -> milliTimerMethod()));
    	timeline2.setCycleCount(10);
		if (tempTimerVal == 0) {
			try {
				MouseMovements.saveTest(1);
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
//			VistaNavigator.loadVista(VistaNavigator.PRIMARYEVALRESULTSVISTA);
			
			// new function in process
			//if (DataIO.evaluationOnePassed() == true)
				//VistaNavigator.loadVista(VistaNavigator.VISTA_10);
			//else
				//VistaNavigator.loadVista(VistaNavigator.VISTA_5);
		}
		else
	    	timeline2.play();
			//timerLabel2.setText(Integer.toString(timerVal));
	}
	
	public void milliTimerMethod( ) {
		String milli = String.format("%01d", milliTimerVal);
		evalTwoTimerLabel.setText(Integer.toString(tempTimerVal) + "." + milli);
		milliTimerVal--;
	}
}