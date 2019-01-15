import java.awt.AWTException;
import java.awt.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.javafx.geom.Rectangle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PrimaryEvalResultsController {
	// purpose: print out the user's results	
	// purpose: depending on the user's results: lead them to either passed or unpassed categories
	
	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
	
    @FXML // fx:id="primaryEvalResultsStackPane"
    private StackPane primaryEvalResultsStackPane; // Value injected by FXMLLoader	

    @FXML // fx:id="primaryEvalVBox"
    private VBox primaryEvalVBox; // Value injected by FXMLLoader	
  
    @FXML // fx:id="primaryEvalResultsLabel"
    private Label primaryEvalResultsLabel; // Value injected by FXMLLoader	
    
    @FXML // fx:id="primaryEvalResultsLabelTwo"
    private Label primaryEvalResultsLabelTwo; // Value injected by FXMLLoader
    
    @FXML // fx:id="primaryEvalResultsLabelThree"
    private Label primaryEvalResultsLabelThree; // Value injected by FXMLLoader
    
    @FXML // fx:id="primaryEvalResultsLabelFour"
    private Label primaryEvalResultsLabelFour; // Value injected by FXMLLoader
    
    @FXML // fx:id="primaryEvalResultsText"
    private Text primaryEvalResultsText; // Value injected by FXMLLoader
   
    @FXML // fx:id="primaryEvalResultsRate"
    private TextField primaryEvalResultsRate; // Value injected by FXMLLoader
    
    @FXML // fx:id="primaryEvalResultsRate"1_1
    
    private TextField primaryEvalResultsDepth; 
    
    @FXML // fx:id="primaryEvalResultsRate"
    private TextField primaryEvalResultsRecoil; 
    
    @FXML
    private Button primaryEvalResultsNextButton; 
  

    @FXML
    void primaryEvalResultsNextAction(ActionEvent event) {
    	
    	try {
    		System.out.println("Eval One Results Next Action Button is clicked");
    		if (DataIO.evaluationOnePassed() == true) {
        		VistaNavigator.loadVista(VistaNavigator.EVALONEPASSED);
        		System.out.println("Evalation One Passed");
        	} else {
        		VistaNavigator.loadVista(VistaNavigator.EVALONEFAILED);
        		System.out.println("Evaluation One Failed -> moving on to training");
        	} 
    
    	} catch (Exception e) {
    		System.out.println("Eval One Results Next Action Button is NOT clicked");
    	}
    	
    	System.out.println("Eval One Results Next Action try-catch is over");
    	
    }
    
    @FXML    
    void initialize() throws AWTException {
    	
    	
    	try {
    		
	 		//(Calling saveCPRData throws an error rn so just commented stuff out)	  
	    	//Note that to get the primaryEval results saveCPRData(1) must be called first - comment by Colton
	    	DataIO.saveCPRData(1);	    	
	    	
	    	//Initialize the ArrayList for the primary results
	    	ArrayList<Integer> primaryResults = new ArrayList<>();
	    	primaryResults = DataIO.getPrimaryResults();
	    	
	    	//Note that primary results is structured [mean rate,mean depth, % w adequate recoil]
	    	int meanRate = primaryResults.get(0);
	    	int meanDepth = primaryResults.get(1);
	    	int adequateRecoil = primaryResults.get(2);
	    	
	    	primaryEvalResultsRate.setText(Integer.toString(meanRate));
	    	primaryEvalResultsDepth.setText(Integer.toString(meanDepth));
	    	primaryEvalResultsRecoil.setText(Integer.toString(adequateRecoil));    	
	    	
	 
	    	System.out.println("PrimaryEvalResults: Successful return of data to user");
	    	
	    	
    	} catch (Exception e) {
    		System.out.println("PrimaryEvalResults: Error returning data to user");
    		
    	}
    	
    	
    	//System.out.println("testing PrimaryEvalResults to see if the graphic is working");
    
    }
    	 
}