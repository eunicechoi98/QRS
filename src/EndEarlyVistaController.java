/**
 * Sample Skeleton for 'endEarlyVista.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class EndEarlyVistaController {
	
	Integer timeLeft = 15;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="EndEarlyPane"
    private StackPane EndEarlyPane; // Value injected by FXMLLoader
    
    void timerMethod() {
		//System.out.println(Integer.toString(timeLeft));
		if (timeLeft == 0) {
			timeLeft = 15;
			VistaNavigator.loadVista(VistaNavigator.VISTA_1);
		}
		else
			timeLeft--;
	}

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	Timeline timeline = new Timeline(new KeyFrame(
    	        Duration.millis(1000),
    	        ae -> timerMethod()));
    	timeline.setCycleCount(16);
    	timeline.play();
    }
}
