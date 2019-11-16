import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.scene.control.ButtonBar;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

public class MainController {
	
//	private static Integer testingUserID = 100;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="headerLabel"
    private Label headerLabel; // Value injected by FXMLLoader

    /** Holder of a switchable vista. */
    @FXML
    private StackPane vistaHolder;
    
    @FXML
    private StackPane headerPane;

    @FXML // fx:id="exitButton1"
    private Button exitButton1; // Value injected by FXMLLoader
    
    private Timeline vista3Timeline = new Timeline();
    private Timeline vista4Timeline = new Timeline();
    private Timeline vista6Timeline = new Timeline();
    private Timeline breakVistaTimeline = new Timeline();
    private Timeline familiarizeTimeline = new Timeline();
    private Timeline vista8Timeline = new Timeline();
    private Timeline vista9Timeline = new Timeline();

    @FXML
    public void exitAction(ActionEvent event) {
    	Alert alert = new Alert(Alert.AlertType.WARNING);
    	alert.setTitle("Quick Refresher Sesssions");
    	alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    	alert.getDialogPane().setMinWidth(450);
    	alert.setContentText("You are terminating your session early and it will not be "
    					+ "recorded. Please come back to complete the session at your "
    					+ "earliest convenience.");
    	ButtonType yesButton = new ButtonType("Yes, I want to end my session", ButtonBar.ButtonData.YES);
    	ButtonType noButton = new ButtonType("No, I want to resume my session", ButtonBar.ButtonData.NO);
    	alert.getButtonTypes().setAll(yesButton, noButton);
    	alert.showAndWait().ifPresent(type -> {
    	        if (type == yesButton) {
    	        	vista3Timeline.stop();
    	        	vista4Timeline.stop();
    	        	vista6Timeline.stop();
    	        	breakVistaTimeline.stop();
    	        	familiarizeTimeline.stop();
    	        	vista8Timeline.stop();
    	        	vista9Timeline.stop();
    	        	setTimelines();
    	        	setFullScreen();
    	        	DataIO.resetData();
    	        	VistaNavigator.loadVista(VistaNavigator.EndEarlyVista);
//    	        	exitButton1.setVisible(false);    	
    	        } else {
    	        }
    	});
    }
    
    public void setVista(Node node) {
        vistaHolder.getChildren().setAll(node);
    }
    
    public void setMini() {
    	Main.publicStage.setHeight(250);
    	Main.publicStage.setY(799-250);
    }
    
    public void setFullScreen() {
    	Main.publicStage.setHeight(1366);
    	Main.publicStage.setY(-5);
    }
    
//    public void addExitButton(int currentCode) {
//    	if (testingUserID.equals(currentCode))
//    		exitButton1.setVisible(true);    	   		
//    }
    
    public void setTimelines() {
    	Vista3Controller.setTimeline(vista3Timeline);
    	Vista4Controller.setTimeline(vista4Timeline);
    	Vista6Controller.setTimeline(vista6Timeline);
    	BreakVistaController.setTimeline(breakVistaTimeline);
    	FamiliarizeController.setTimeline(familiarizeTimeline);
    	Vista8Controller.setTimeline(vista8Timeline);
    	Vista9Controller.setTimeline(vista9Timeline);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	
    	//Bind the management of the button with its visibility
//    	exitButton1.managedProperty().bind(exitButton1.visibleProperty());
    	
    	//Hide the button and make it unresponsive
//    	exitButton1.setVisible(false);    	
    	
    	setTimelines();
        assert headerLabel != null : "fx:id=\"headerLabel\" was not injected: check your FXML file 'main.fxml'.";
        assert exitButton1 != null : "fx:id=\"exitButton1\" was not injected: check your FXML file 'main.fxml'.";

    }
}
