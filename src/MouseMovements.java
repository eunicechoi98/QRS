import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.AWTException;

public class MouseMovements {
	private static int delay = 50;
	
	private static int bottomScreenY = 768;	
	
	public static void startTest() throws AWTException {
		Robot robot = new Robot();
		robot.setAutoDelay(delay);
		
		switchWindow();
		
		robot.mouseMove(1164, 736); 
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		switchWindow();
	}
	
	public static void saveTest(int testNum) throws AWTException {
		Robot robot = new Robot();
		robot.setAutoDelay(delay);
		
		switchWindow();
		
		robot.mouseMove(920, 735); //save result
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.delay(750);
		
		String saveString = Integer.toString(DataIO.getCurrentCode()) + "_" + Integer.toString(testNum);
		StringSelection stringSelection = new StringSelection(saveString);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		
		/*
		robot.mouseMove(860, 490); //OK after entering string
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		*/
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		
		robot.delay(500);
		robot.mouseMove(1160, 735); //Practice button
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		switchWindow();

	}
	public static void switchWindow() throws AWTException {
		Robot robot = new Robot();
		robot.setAutoDelay(delay);
		
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_ALT);
			
	}
	
}
