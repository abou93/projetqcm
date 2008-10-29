package vues;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import javax.swing.ImageIcon;
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
		
		// Affiche une icone dans la barre de tache		
		if (SystemTray.isSupported()) {
			ImageIcon i = new ImageIcon("C:/Images/LogoENI16.png");
			Image im = i.getImage();
			TrayIcon tic = new TrayIcon(im,"Eni - Création de QCM");
			SystemTray tray = SystemTray.getSystemTray();
			try {
				tray.add(tic);
				// tray.remove(tic);  Pour enlever l'icone
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String imagePath = "C:/Images/SplashScreen2.png";
		SplashScreenQCM fond = new SplashScreenQCM(imagePath);
		//fond.setLocationRelativeTo(fond.getParent());
		fond.setUndecorated(true);
		fond.pack();
		fond.setVisible(true);
		
		
		
		fond.getQuit().requestFocusInWindow();
		fond.getValid().requestFocusInWindow();
		fond.getText().requestFocusInWindow();
		fond.getText2().requestFocusInWindow();
	}

}
