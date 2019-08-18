//import java.awt.TextField;
//import java.net.URL;
//import java.util.ResourceBundle;
//
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.control.TextField;

public class evalOnePassedController {
	
		Integer timeLeft = 10;
	
	//Let’s work on improving your compressions!
		@FXML // ResourceBundle that was given to the FXMLLoader
	    private ResourceBundle resources;

	    @FXML // URL location of the FXML file that was given to the FXMLLoader
	    private URL location;
		
	    @FXML // fx:id="failOneStackPane"
	    private StackPane passOneVista; // Value injected by FXMLLoader	

	    @FXML // fx:id="failOneVBox"
	    private VBox passOneVBox; // Value injected by FXMLLoader	
	    
	    @FXML // fx:id="evalOneFailButton"
	    private TextField passOneReturnDate;
	    
		void timerMethod() { // what is this for?

			if (timeLeft == 0) {
				timeLeft = 10;
				VistaNavigator.loadVista(VistaNavigator.VISTA_1);
			}
			else
				timeLeft--;
		}

	// provide user's next return date depending on their last return date
	
	// save user's return date for next time
	    
	    @FXML // This method is called by the FXMLLoader when initialization is complete
	    void initialize() {
	    	
	    	
	    	//DataIO.saveCPRData(1);
	    	// dont need this here because it's already done in the previous vista
	    	
	    	String nextSession = DataIO.scheduleNextSession();
	    	passOneReturnDate.setText(nextSession + " month(s)");
	    	
	    	DataIO.exportToCSV();
	    	DataIO.resetData(); //needed?
	    	/*
	    	Timeline timeline = new Timeline(new KeyFrame(
	    	        Duration.millis(1000),
	    	        ae -> timerMethod()));
	    	timeline.setCycleCount(11);
	    	timeline.play();
	    	*/
	    	Timeline timeline = new Timeline(new KeyFrame(
	    	        Duration.millis(1000),
	    	        ae -> timerMethod()));
	    	timeline.setCycleCount(11);
	    	timeline.play();  	
	    	
	    	System.out.println("Eval One Passed, Session Complete!");
	    }
}
