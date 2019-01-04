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
    void stepFourNextAction(ActionEvent event) {
    	if (DataIO.evaluationOnePassed() == true) {
    		VistaNavigator.loadVista(VistaNavigator.VISTA_10);
    	} else {
    		VistaNavigator.loadVista(VistaNavigator.VISTA_5);
    	}
    }  
    
    @FXML
    void initialize() throws AWTException {
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
