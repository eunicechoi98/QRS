import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.AWTException;

public class evalTwoResultsController {
	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
	
    @FXML // fx:id="primaryEvalResultsStackPane"
    private StackPane evalTwoResultsVista; // Value injected by FXMLLoader	

    @FXML // fx:id="primaryEvalVBox"
    private VBox evalTwoResultsVBox; // Value injected by FXMLLoader	
  
    @FXML // fx:id="primaryEvalResultsLabel"
    private Label evalTwoResultsLabel; // Value injected by FXMLLoader	
    
    @FXML // fx:id="primaryEvalResultsLabelTwo"
    private Text evalTwoResultsText; // Value injected by FXMLLoader
    
    @FXML // fx:id="primaryEvalResultsLabelThree"
    private Button evalTwoResultsNextButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="primaryEvalResultsLabelFour"
    private TextField evalTwoResultsRate; // Value injected by FXMLLoader
    
    @FXML
    private TextField evalTwoResultsDepth; 
    
    @FXML
    private TextField evalTwoResultsRecoil;
    
    @FXML
    void evalTwoNextAction(ActionEvent event) {

    	try {
    		VistaNavigator.loadVista(VistaNavigator.VISTA_10);
    		System.out.println("Evaluation Two Results -> Vista 10: Session Complete, return next session date");
    
    	} catch (Exception e) {
    		System.out.println("Eval Two Results Next Action Button is NOT clicked");
    	}
    }  
    
    @FXML
    void initialize() throws AWTException {
    	
			try {
			    		
				 		//(Calling saveCPRData throws an error rn so just commented stuff out)	  
				    	//Note that to get the primaryEval results saveCPRData(1) must be called first - comment by Colton
				    	DataIO.saveCPRData(2);	    	
				    	
				    	//Initialize the ArrayList for the primary results
				    	ArrayList<Integer> secondaryResults = new ArrayList<>();
				    	secondaryResults = DataIO.getSecondaryResults();
				    	
				    	//Note that primvary results is structured [mean rate,mean depth, % w adequate recoil]
				    	int meanRate = secondaryResults.get(0);
				    	int meanDepth = secondaryResults.get(1);
				    	int adequateRecoil = secondaryResults.get(2);
				    	
				    	evalTwoResultsRate.setText(Integer.toString(meanRate) + " bpm");
				    	evalTwoResultsDepth.setText(Integer.toString(meanDepth) + " mm");
				    	evalTwoResultsRecoil.setText(Integer.toString(adequateRecoil) +"%");    	
				    	
				 
				    	System.out.println("PrimaryEvalResults: Successful return of data to user");
				    	
				    	
			    	} catch (Exception e) {
			    		System.out.println("PrimaryEvalResults: Error returning data to user");
			    		
			    	}
    }

}
