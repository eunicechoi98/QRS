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
     /*
    @FXML
    void tempNext1(ActionEvent event) {
    	if (DataIO.inControlGroup() == true)
			VistaNavigator.loadVista(VistaNavigator.VISTA_10);
		else
			VistaNavigator.loadVista(VistaNavigator.VISTA_5);
        timeline.stop();
    }\*/

    @FXML
    
    void initialize() {
    	
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
    	timerLabel2.setText(Integer.toString(tempTimerVal) + ":0");
    	timerMethod();
    	timeline.setCycleCount(tempTimerVal);
    	timeline.play();
    	VistaNavigator.loadVista(VistaNavigator.PRIMARYEVALRESULTSVISTA);
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
			
			
			VistaNavigator.loadVista(VistaNavigator.PRIMARYEVALRESULTSVISTA);
			
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
		timerLabel2.setText(Integer.toString(tempTimerVal) + "." + milli);
		milliTimerVal--;
	}
}


