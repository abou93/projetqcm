package vues;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LanceurApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//mettre le look and feel souhaité
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		String imagePath = "C:/Images/SplashScreen2.png";
		SplashScreenQCM fond = new SplashScreenQCM(imagePath);
		fond.setUndecorated(true);
		fond.pack();
		fond.setVisible(true);
		fond.getQuit().requestFocusInWindow();
		fond.getValid().requestFocusInWindow();
		fond.getText().requestFocusInWindow();
		fond.getText2().requestFocusInWindow();
	}

}
