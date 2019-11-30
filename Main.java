import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.*;
/**
 * Main application class.
 */
public class Main extends Application {
	
	// starting point
    public static void main(String[] args) {		
    	// method inside the Application class
    	// set up the program as a JavaFX application
        launch(args);
    }
	
	public static Stage publicStage;

	/*
	 * In Java, the scene is called the stage
	 * the scene is the content
	 * pass in the primaryStage
	 * 
	 */
    @Override
    public void start(Stage stage) throws Exception{
    	// setting the stage
        stage.setTitle("Quick Refresher Session");
        // setting the scene
        stage.setScene(
            createScene(
                loadMainPane()
            )
        );
        
        stage.show();
        publicStage = stage;
    }
    

    /**
     * Loads the main fxml layout.
     * Sets up the vista switching VistaNavigator.
     * Loads the first vista into the fxml layout.
     *
     * @return the loaded pane.
     * @throws IOException if the pane could not be loaded.
     */
    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(VistaNavigator.MAIN));

        MainController mainController = loader.getController();

        VistaNavigator.setMainController(mainController);
        VistaNavigator.loadVista(VistaNavigator.VISTA_1);
        

        return mainPane;
    }

    /**
     * Creates the main application scene.
     *
     * @param mainPane the main application layout.
     *
     * @return the created scene.
     */
    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(
            mainPane
        );

        scene.getStylesheets().setAll(
            getClass().getResource("vista.css").toExternalForm()
        );
        return scene;
    }

}
