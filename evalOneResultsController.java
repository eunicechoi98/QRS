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

public class evalOneResultsController {
	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
	
    @FXML // fx:id="primaryEvalResultsStackPane"
    private StackPane evalOneResultsVista; // Value injected by FXMLLoader	

    @FXML // fx:id="primaryEvalVBox"
    private VBox evalOneResultsVBox; // Value injected by FXMLLoader	
  
    @FXML // fx:id="primaryEvalResultsLabel"
    private Label evalOneResultsLabel; // Value injected by FXMLLoader	
    
    @FXML // fx:id="primaryEvalResultsLabelTwo"
    private Text evalOneResultsText; // Value injected by FXMLLoader
    
    @FXML // fx:id="primaryEvalResultsLabelThree"
    private Button evalOneResultsNextButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="primaryEvalResultsLabelFour"
    private TextField evalOneResultsRate; // Value injected by FXMLLoader
    
    @FXML
    private TextField evalOneResultsDepth; 
    
    @FXML
    private TextField evalOneResultsRecoil;
    
    @FXML
    void evalOneNextAction(ActionEvent event) {

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
				    	
				    	evalOneResultsRate.setText(Integer.toString(meanRate) + " cpm");
				    	evalOneResultsDepth.setText(Integer.toString(meanDepth) + " mm");
				    	evalOneResultsRecoil.setText(Integer.toString(adequateRecoil) +"%");    	
				    	
				 
				    	System.out.println("PrimaryEvalResults: Successful return of data to user");
				    	
				    	
			    	} catch (Exception e) {
			    		System.out.println("PrimaryEvalResults: Error returning data to user");
			    		
			    	}

    	/*
    	ArrayList<Integer> primaryResults = DataIO.getPrimaryResults();
    	Integer rate = primaryResults.get(0);
    	Integer depth = primaryResults.get(1);
    	Integer recoil = primaryResults.get(2);
    	
    	String rateResult = rate.toString();
    	String depthResult = depth.toString();
    	String recoilResult = recoil.toString();
    	
    	evalOneResultsRate.setText(rateResult);
    	evalOneResultsDepth.setText(depthResult);    	
    	evalOneResultsRecoil.setText(recoilResult);
    	*/
    }

}
