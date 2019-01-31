import java.awt.AWTException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Vista4Controller {

	private static Timeline timeline;
	
	private static Integer timerVal = 60;
	private static Integer tempTimerVal; 
	
	private Integer milliTimerVal = 9;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label timerLabel2;

    @FXML
    private StackPane vista4;
    
    public static void setTimeline(Timeline parentTimeline) {
    	Vista4Controller.timeline = parentTimeline;
    	timerVal = 60; //make sure this matches above
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
    	timerLabel2.setText(Integer.toString(tempTimerVal) + ":0");
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
    	
    	// when time is up
    	// 1. save the performance on laerdal
    	// 2. go to the next step: Primary Eval Results Vista
		if (tempTimerVal == 0) {	
			
			try {
				// **eunice's understanding: saves test with mouse bot for the next vista and DataIO to use
				// **the saved data to evaluate and return data to the user
				MouseMovements.saveTest(1);
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			VistaNavigator.loadVista(VistaNavigator.EVALONERESULTS);
			
	
		}
		else
	    	timeline2.play();
			//timerLabel2.setText(Integer.toString(timerVal));
	}
	
	public void milliTimerMethod( ) {
		String milli = String.format("%01d", milliTimerVal);
		timerLabel2.setText(Integer.toString(tempTimerVal) + "." + milli);
		milliTimerVal--;
	}
}


