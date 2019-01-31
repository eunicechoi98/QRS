import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebView;
import javafx.util.Duration;

public class Vista5Controller {

	private static int videoLength = 60;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane vista5;
    
    @FXML
    private VBox vBoxVista5;
    
    @FXML
    private HBox hBoxVista5;
    
    @FXML // fx:id="nextButton"
    private Button vista5nextButton; // Value injected by FXMLLoader
    
    @FXML
    void vista5NextAction(ActionEvent event) {
    	
        VistaNavigator.loadVista(VistaNavigator.FamiliarizeVista);
        System.out.println("Vista 5 next button clicked");
        
    }
    
    void revealMethod() {
        vista5nextButton.setDisable(false);
    }

    @FXML
    void initialize() {
    	
    	//DataIO.saveCPRData(1);
    	vista5nextButton.setDisable(true);
    	Timeline timeline = new Timeline(new KeyFrame(
    	        Duration.millis(videoLength*1000),
    	        ae -> revealMethod()));
    	timeline.setCycleCount(1);
    	timeline.play();
    	File file = new File("C:\\Users\\CSC\\Desktop\\QRS_trainingvideo.mp4");
    	String media = file.toURI().toString();
    	Media video = new Media(media);
    	MediaPlayer player = new MediaPlayer(video);
    	
    	MediaView mediaView = new MediaView(player);
    	hBoxVista5.getChildren().add(mediaView);
    	mediaView.setFitHeight(650);
    	mediaView.setFitWidth(850);
    	player.play();
    	
    	System.out.println("Vista 5: Video -> Familiarize Laerdal Software");
    	/*
    	WebView webview = new WebView();
        webview.getEngine().load(
          "https://www.youtube.com/embed/hizBdM1Ob68?autoplay=1"          
        		);
        webview.setPrefSize(1000, 100);
        hBoxVista5.getChildren().add(webview);
        
        */
    }
}

