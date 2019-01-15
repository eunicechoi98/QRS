import java.awt.TextField;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class evalOnePassedController {
	
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

	// provide user's next return date depending on their last return date
	
	// save user's return date for next time
	    
	    @FXML // This method is called by the FXMLLoader when initialization is complete
	    void initialize() {
	    	
	    	
	    	//DataIO.saveCPRData(1);
	    	// dont need this here because it's already done in the previous vista
	    	
	    	String nextSession = DataIO.scheduleNextSession();
	    	if (DataIO.evaluationTwoPassed() == true) { 
	    		passOneReturnDate.setText(nextSession);
	    	} else {
	    		passOneReturnDate.setText(nextSession);
	    	}
	    	
	    	DataIO.exportToCSV();
	    	DataIO.resetData(); //needed?
	    	/*
	    	Timeline timeline = new Timeline(new KeyFrame(
	    	        Duration.millis(1000),
	    	        ae -> timerMethod()));
	    	timeline.setCycleCount(11);
	    	timeline.play();
	    	*/
	    	
	    	System.out.println("Eval One Passed, Session Complete!");
	    }
}
