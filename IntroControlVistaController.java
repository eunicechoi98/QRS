/**
 * Sample Skeleton for 'introControlVista.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class IntroControlVistaController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="nextButton"
    private Button nextButton; // Value injected by FXMLLoader

    @FXML // fx:id="introControl"
    private StackPane introControl; // Value injected by FXMLLoader

    @FXML
    void nextAction(ActionEvent event) {
    	VistaNavigator.loadVista(VistaNavigator.VISTA_2);
    }

    void revealMethod() {
        nextButton.setDisable(false);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws InterruptedException {
        nextButton.setDisable(true);
    	Timeline timeline = new Timeline(new KeyFrame(
    	        Duration.millis(5000),
    	        ae -> revealMethod()));
    	timeline.setCycleCount(1);
    	timeline.play();
       //nextButton.disableProperty().bind(Bindings.);
    }
}
