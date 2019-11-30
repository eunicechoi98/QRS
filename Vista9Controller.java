import java.awt.AWTException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Vista9Controller {
	
	private static Timeline timeline;
	
	private static Integer timerVal = 60;
	private static Integer tempTimerVal;

	private Integer milliTimerVal = 9;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label timerLabel5;

    @FXML
    private StackPane vista9;
    
    public static void setTimeline(Timeline parentTimeline) {
    	Vista9Controller.timeline = parentTimeline;
    	timerVal = 60; //make sure this matches above
    }

    @FXML
    void initialize() {
    	tempTimerVal = timerVal;
    	KeyFrame key = new KeyFrame(
    	        Duration.millis(1100),
    	        ae -> timerMethod());
    	if (timeline.getKeyFrames().isEmpty())
    		timeline.getKeyFrames().add(key);
    	else {
    		timeline.getKeyFrames().clear();
    		timeline.getKeyFrames().add(key);
    	}
    	timerLabel5.setText(Integer.toString(tempTimerVal) + ":0");
    	timerMethod();
    	timeline.setCycleCount(tempTimerVal);
    	timeline.play();
    }

	public void timerMethod() {
		tempTimerVal--;
		milliTimerVal = 9;
		Timeline timeline2 = new Timeline(new KeyFrame(
    	        Duration.millis(110),
    	        ae -> milliTimerMethod()));
    	timeline2.setCycleCount(10);
    	
    	
		if (tempTimerVal == 0) {
			try {
				// **saves the eval two results using bot
				// **to be used for the evaluation in the next vista
				MouseMovements.saveTest(2);
				System.out.println("Vista 9: Saved Test using MouseMovements");
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("**Vista 9: ERROR couldnt save test using MouseMovements");
			}
			VistaNavigator.loadVista(VistaNavigator.EVALTWOTESTING);
			System.out.println("Vista 9: 2nd Eval over -> Eval Two Results Vista");
		}
		else
	    	timeline2.play();
	}
	
	public void milliTimerMethod( ) {
		String milli = String.format("%01d", milliTimerVal);
		timerLabel5.setText(Integer.toString(tempTimerVal) + "." + milli);
		milliTimerVal--;
	}
}


