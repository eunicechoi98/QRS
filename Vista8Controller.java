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

public class Vista8Controller {

	private static Integer timerVal = 3;
	private static Integer tempTimerVal;
	
    private static Timeline timeline;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="vista8"
    private StackPane vista8; // Value injected by FXMLLoader
    
    @FXML // fx:id="vista8VBox"
    private VBox vista8VBox; // Value injected by FXMLLoader
    
    @FXML // fx:id="vista8LabelOne"
    private Label vista8LabelOne; // Value injected by FXMLLoader
    
    @FXML // fx:id="timerLabel4"
    private Label timerLabel4;
    
    @FXML // fx:id="vista8Text"
    private Text vista8Text; // Value injected by FXMLLoader
    
    public static void setTimeline(Timeline parentTimeline) {
    	Vista8Controller.timeline = parentTimeline;
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


