import java.awt.AWTException;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;
/*
 * Countdown to the CPR Evaluation (10 seconds)
 */
public class evalTwoCountdownController {

	private static Integer timerVal = 10;
	private static Integer tempTimerVal;
	
	private static Timeline timeline;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML // fx:id="evalTwoCountdownVista"
    private StackPane evalTwoCountdownVista; // Value injected by FXMLLoader
    
    @FXML // fx:id="evalTwoCountdownVBox"
    private VBox evalTwoCountdownVBox; // Value injected by FXMLLoader
    
    @FXML // fx:id="evalTwoCountdownLabel"
    private Label evalTwoCountdownLabel; // Value injected by FXMLLoader
    
    @FXML // fx:id="evalTwoCountdownTimerLabel"
    private Label evalTwoCountdownTimerLabel;
    
    @FXML // fx:id="evalTwoCountdownText"
    private Text evalTwoCountdownText; // Value injected by FXMLLoader
   
    
    public static void setTimeline(Timeline parentTimeline) {
    	evalTwoCountdownController.timeline = parentTimeline;
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
    	//String musicFile = "C:\\Users\\CSC\\Desktop\\QRS_Application\\countdownBeeps.mp3";     // For example
    	//System.out.println("in initialize again");

    	//Media sound = new Media(new File(musicFile).toURI().toString());
    	//MediaPlayer mediaPlayer = new MediaPlayer(sound);
    	//mediaPlayer.play();
    	evalTwoCountdownTimerLabel.setText(Integer.toString(tempTimerVal));
    	timeline.setCycleCount(10);
    	timeline.playFromStart();
    	
    	System.out.println("Eval 2 Countdown -> CPR Eval 2");
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
			VistaNavigator.loadVista(VistaNavigator.EVALTWO);
		}
		else {
			evalTwoCountdownTimerLabel.setText(Integer.toString(tempTimerVal));
		}
	}
}