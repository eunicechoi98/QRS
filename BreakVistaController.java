import java.awt.AWTException;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class BreakVistaController {

	private static Integer timerVal = 6;
	private static Integer tempTimerVal;
	
    private static Timeline timeline;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label timerLabel4;

    @FXML
    private StackPane breakvista;
    
    public static void setTimeline(Timeline parentTimeline) {
    	BreakVistaController.timeline = parentTimeline;
    	timerVal = 3;
    }

    @FXML
    void initialize() throws AWTException {
    	tempTimerVal = timerVal;
    	KeyFrame key = new KeyFrame(
    	        Duration.millis(875),
    	        ae -> timerMethod());
    	if (timeline.getKeyFrames().isEmpty())
    		timeline.getKeyFrames().add(key);
    	else {
    		timeline.getKeyFrames().clear();
    		timeline.getKeyFrames().add(key);
    	}
    	timerLabel4.setText(Integer.toString(tempTimerVal));
    	timeline.setCycleCount(61);
    	timeline.playFromStart();
    }

	private void timerMethod() {
		tempTimerVal--;
		if (tempTimerVal == 0) {
			VistaNavigator.loadVista(VistaNavigator.VISTA_8);
			System.out.println("BreakVista: Break from practice -> Vista 8: Countdown to 2nd eval");
		}
		else
			timerLabel4.setText(Integer.toString(tempTimerVal));
	}
}


