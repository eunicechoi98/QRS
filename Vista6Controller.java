import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Vista6Controller {

	private static Integer timerVal = 12;
	private static Integer tempTimerVal;
	
	private static Timeline timeline;
	
	private Integer milliTimerVal = 9;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label timerLabel3;

    @FXML
    private StackPane vista6;
    
    public static void setTimeline(Timeline parentTimeline) {
    	Vista6Controller.timeline = parentTimeline;
    	timerVal = 2; //make sure this matches above
    }

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
    	VistaNavigator.mainController.setMini();
    	timerLabel3.setText(Integer.toString(tempTimerVal) + ":0");
    	timerMethod();
    	timeline.setCycleCount(tempTimerVal);
    	timeline.play();
    }

	public void timerMethod() {
		tempTimerVal--;
		milliTimerVal = 9;
		Timeline timeline2 = new Timeline(new KeyFrame(
    	        Duration.millis(100),
    	        ae -> milliTimerMethod()));
    	timeline2.setCycleCount(10);
    	
		if (tempTimerVal == 0) {
			VistaNavigator.mainController.setFullScreen();
			VistaNavigator.loadVista(VistaNavigator.BreakVista);
			System.out.println("Vista 6: practice over -> BreakVista: break from practice");
		}
		else
	    	timeline2.play();
	}
	
	public void milliTimerMethod( ) {
		String milli = String.format("%01d", milliTimerVal);
		timerLabel3.setText(Integer.toString(tempTimerVal) + "." + milli);
		milliTimerVal--;
	}
}


