/**
 * Sample Skeleton for 'familiarizeVista.fxml' Controller Class
 */

import java.awt.AWTException;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class FamiliarizeController {
	
	private static Integer timerVal = 20;
	private static Integer tempTimerVal;
	
    private static Timeline timeline;

	/*
	Timeline timeline = new Timeline(new KeyFrame(
	        Duration.millis(1000),
	        ae -> timerMethod()));
	*/
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="timerLabel3"
    private Label timerLabel3; // Value injected by FXMLLoader

    @FXML // fx:id="famVista"
    private StackPane famVista; // Value injected by FXMLLoader
    
    @FXML
    private ImageView demoImage;
    
    @FXML
    private Button familiarizeNextButton;

    @FXML
    void nextAction(ActionEvent event) {
    	
    	timeline.stop();
        VistaNavigator.loadVista(VistaNavigator.VISTA_6);
        System.out.println("Famiarize next button clicked");
    }
    
    public static void setTimeline(Timeline parentTimeline) {
    	FamiliarizeController.timeline = parentTimeline;
    	timerVal = 20;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
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
    	File file = new File("C:\\Users\\CSC\\Desktop\\QRS_Application\\screenshotCropped.png");
    	Image image = new Image(file.toURI().toString());
    	demoImage.setImage(image);
    	timerLabel3.setText(Integer.toString(tempTimerVal));
    	timeline.setCycleCount(61);
    	timeline.playFromStart();
    	
    	System.out.println("Familiarize Laerdal Software -> Vista 6: 2 min of Practice");
    	
    }
    
    private void timerMethod() {
		tempTimerVal--;
		if (tempTimerVal == 0) {
			VistaNavigator.loadVista(VistaNavigator.VISTA_6);
		}
		else
			timerLabel3.setText(Integer.toString(tempTimerVal));
	}
}
