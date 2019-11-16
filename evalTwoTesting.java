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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.awt.AWTException;
import java.awt;

public class evalTwoTesting {
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
    		VistaNavigator.loadVista(VistaNavigator.VISTA_10);
    		System.out.println("Evaluation Two Results -> Vista 10: Session Complete, return next session date");
    
    	} catch (Exception e) {
    		System.out.println("Eval Two Results Next Action Button is NOT clicked");
    	}
    }  
    
    @FXML
    void initialize() throws AWTException {
    	
			try {
			    		
				    	
				    	
				    	DataIO.saveCPRData(2);	    	
				    	
				    	//Initialize the ArrayList for the primary results
				    	ArrayList<Integer> secondaryResults = new ArrayList<>();
				    	secondaryResults = DataIO.getSecondaryResults();
				    	
				    	//Note that primvary results is structured [mean rate,mean depth, % w adequate recoil]
				    	int meanRate = secondaryResults.get(0);
				    	int meanDepth = secondaryResults.get(1);
				    	int adequateRecoil = secondaryResults.get(2);
				    	
				    	evalOneResultsRate.setText(Integer.toString(meanRate) + " cpm");
				    	
				    	// convert depth in mm to cm
				    	float meanDepthCm = meanDepth/10;
				    	evalOneResultsDepth.setText(Float.toString(meanDepth) + " cm");
				    	evalOneResultsRecoil.setText(Integer.toString(adequateRecoil) +"%");    	
				    	
				    	// indicating which fields they passed or not
				    	// 1. Rate
				    	if (meanRate >= 100 && meanRate <= 120){
				    		// set textfield to green

				    		evalOneResultsRate.setStyle("-fx-background-color: #91FF8E;");
				    	} else {
				    		// set textfield to red
				    		evalOneResultsRate.setStyle("-fx-background-color: #FFA7A7;");
				    	}
				    	
				    	
				    	// 2. Depth
				    	if (meanDepth >= 50 && meanDepth <= 60) {
				    		evalOneResultsDepth.setStyle("-fx-background-color: #91FF8E;");
				    	} else {
				    		evalOneResultsDepth.setStyle("-fx-background-color: #FFA7A7;");
				    	}
				    	
				    	
				    	// 3. Recoil
				    	if (adequateRecoil >= 80) {
				    		evalOneResultsRecoil.setStyle("-fx-background-color: #91FF8E;");
				    	} else {
				    		evalOneResultsRecoil.setStyle("-fx-background-color: #FFA7A7;");
				    	}
				 
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