import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class Vista10Controller {
	
	Integer timeLeft = 5;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="vista10"
    private StackPane vista10; // Value injected by FXMLLoader
    
    @FXML // fx:id="vista10NextSession"
    private TextField vista10NextSession;
    
	void timerMethod() { // what is this for?

		if (timeLeft == 0) {
			timeLeft = 10;
			VistaNavigator.loadVista(VistaNavigator.VISTA_1);
		}
		else
			timeLeft--;
	}

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	
    	
//    	DataIO.saveCPRData(2);
    	//If we are using either a demo or testing id, do not schedule the next session and display default text
    	if (DataIO.getUsername().equals("test1") || DataIO.getUsername().equals("test2")|| DataIO.getUsername().equals("demo"))
    		vista10NextSession.setText("X month(s)");
    	else {
	    	String nextSession = DataIO.scheduleNextSession();
	    	vista10NextSession.setText(nextSession + " month(s)");
    	}

//    	if (DataIO.evaluationTwoPassed() == true) {
//    		if (nextSession.equals("1")) {
//    			vista10NextSession.setText(nextSession + " month");
//    		} else {
//    			vista10NextSession.setText(nextSession + " months");
//    		}
//    		
//    	} else {
//    		vista10NextSession.setText(nextSession + " months");
//    	}
    	
    	DataIO.resetData();
    	Timeline timeline = new Timeline(new KeyFrame(
    	        Duration.millis(1000),
    	        ae -> timerMethod()));
    	timeline.setCycleCount(11);
    	timeline.play();
    	System.out.println("Vista 10: Session Complete. Return Next Session Date.");
    	
    }
}
