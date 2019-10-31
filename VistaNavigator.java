import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Utility class for controlling navigation between vistas.
 *
 * All methods on the navigator are static to facilitate
 * simple access from anywhere in the application.
 */

public class VistaNavigator {

    /**
     * Convenience constants for fxml layouts managed by the navigator.
     */
    public static final String MAIN    = "main.fxml";
    public static final String VISTA_1 = "vista1.fxml";
    
    public static final String STEPTWOVISTA = "stepTwoVista.fxml";
    public static final String STEPTHREEVISTA = "stepThreeVista.fxml";
    public static final String STEPFOURVISTA = "stepFourVista.fxml";
    public static final String STEPFIVEVISTA = "stepFiveVista.fxml";
    public static final String STEPSIXVISTA = "stepSixVista.fxml";
    
    public static final String EVALTWORESULTS = "evalTwoResults.fxml";
    
    public static final String EVALONERESULTS = "evalOneResults.fxml";
    public static final String EVALONEPASSED = "EvalOnePassedVista.fxml";  
    public static final String EVALONEFAILED = "evalOneFailedVista.fxml";
    
    public static final String TRAININGVID = "trainingVid.fxml";
    public static final String PRACCOUNTDOWN= "practiveCountdown.fxml";
    public static final String PRACTICE = "practice.fxml";
    public static final String REST = "rest.fxml";
    public static final String EVALTWOCOUNTDOWN = "evalTwoCountdown.fxml";
    public static final String EVALTWO = "evalTwo.fxml";
    
    public static final String EVALTWOFAILED = "evalTwoFailed.fxml";
    public static final String EVALTWOPASSED = "evalTwoPassed.fxml";
    
  
    public static final String VISTA_2 = "vista2.fxml";
    public static final String VISTA_3 = "vista3.fxml";
    public static final String VISTA_4 = "vista4.fxml";
    public static final String VISTA_5 = "vista5.fxml";
    public static final String VISTA_6 = "vista6.fxml";
    public static final String VISTA_7 = "vista7.fxml";
    public static final String VISTA_8 = "vista8.fxml";
    public static final String VISTA_9 = "vista9.fxml";
    public static final String VISTA_10 = "vista10.fxml";
    public static final String ForgotCodeVista = "forgotCodeVista.fxml";
    public static final String ControlIntroVista = "introControlVista.fxml";
    public static final String TrainingIntroVista = "introTrainingVista.fxml";
    public static final String EndEarlyVista = "endEarlyVista.fxml";
    public static final String SaveErrorVista = "saveErrorVista.fxml";
    public static final String BreakVista = "breakVista.fxml";
    public static final String FamiliarizeVista = "familiarizeVista.fxml";
    
    
    public static String currentVista;

    /** The main application layout controller. */
    public static MainController mainController;
    
    public static String getCurrentVista() {
    	return currentVista;
    }

    /**
     * Stores the main controller for later use in navigation tasks.
     *
     * @param mainController the main application layout controller.
     */
    public static void setMainController(MainController mainController) {
        VistaNavigator.mainController = mainController;
    }

    /**
     * Loads the vista specified by the fxml file into the
     * vistaHolder pane of the main application layout.
     *
     * Previously loaded vista for the same fxml file are not cached.
     * The fxml is loaded anew and a new vista node hierarchy generated
     * every time this method is invoked.
     *
     * A more sophisticated load function could potentially add some
     * enhancements or optimizations, for example:
     *   cache FXMLLoaders
     *   cache loaded vista nodes, so they can be recalled or reused
     *   allow a user to specify vista node reuse or new creation
     *   allow back and forward history like a browser
     *
     * @param fxml the fxml file to be loaded.
     */
    public static void loadVista(String fxml) {
    	currentVista = fxml;
        try {
            mainController.setVista(
                FXMLLoader.load(
                    VistaNavigator.class.getResource(
                        fxml
                    )
                )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}