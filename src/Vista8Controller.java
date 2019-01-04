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

public class Vista8Controller {

	private static Integer timerVal = 10;
	private static Integer tempTimerVal;
	
    private static Timeline timeline;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label timerLabel4;

    @FXML
    private StackPane vista8;
    
    public static void setTimeline(Timeline parentTimeline) {
    	Vista8Controller.timeline = parentTimeline;
    	timerVal = 10;
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
    	String musicFile = "C:\\Users\\jsuni\\Desktop\\QRS_Application\\countdownBeeps.mp3";     // For example

    	Media sound = new Media(new File(musicFile).toURI().toString());
    	MediaPlayer mediaPlayer = new MediaPlayer(sound);
    	mediaPlayer.play();
    	timerLabel4.setText(Integer.toString(tempTimerVal));
    	timeline.setCycleCount(10);
    	timeline.playFromStart();
    }

	private void timerMethod() {
		tempTimerVal--;
		if (tempTimerVal == 0) {
			try {
				MouseMovements.startTest();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			VistaNavigator.loadVista(VistaNavigator.VISTA_9);
			System.out.println("Vista 8: Countdown to 2nd Eval -> Vista 9: 2nd Eval");
		}
		else
			timerLabel4.setText(Integer.toString(tempTimerVal));
	}
}


