package vues;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import securite.hashPassword;
import sun.jkernel.Bundle;

/*
 * Created on Jul 19, 2005
 *
 */

/**
 * @author Fery.P
 *
 */
public class SplashScreen  extends JFrame {
	private Container c;
	private JPanel imagePanel;
	private String filePath;
	
	JTextField text;
	JPasswordField text2;
	JLabel lab1;
	JLabel lab2;
	JLabel lab3;
	JButton btnValid;
	JButton btnQuit;

	public SplashScreen(String filePath) {
		super("Image de fond");
		this.filePath = filePath;
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		c = getContentPane();
		imagePanel = new JPanel() {
			public void paint(Graphics g) {
				try {
					BufferedImage image = ImageIO.read(new File(filePath));
					g.drawImage(image, 0, 0, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		imagePanel.setPreferredSize(new Dimension(640, 400));
		imagePanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6,1));
		JPanel btns = new JPanel();
		btns.setLayout(new BorderLayout());
		
		 text = new JTextField("formateur");
		 text2 = new JPasswordField();
		 lab1 = new JLabel("Login : ");
		 lab2 = new JLabel("Mot de Passe : ");
		 lab3 = new JLabel("");
		 btnValid = new JButton("Valider");
		 btnQuit = new JButton("Quitter");

		btns.add(btnValid,BorderLayout.WEST);
		btns.add(btnQuit,BorderLayout.EAST);
				
		panel.add(lab1);
		panel.add(text);
		panel.add(lab2);
		panel.add(text2);
		panel.add(lab3);
		panel.add(btns);
		
		panel.setBounds(285,160,180,130);
		
		imagePanel.add(panel);
		c.add(imagePanel);
	
		btnValid.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				hashPassword hash = new hashPassword();
				try {
				String passeSaisi = hash.getHash(text2.getText());
				String passeDefaut =hash.getHash(ResourceBundle.getBundle("securite").getString("motdepasse")); 	

				if(passeSaisi.equals(passeDefaut)){
					new fenPrincipale().setVisible(true);
					SplashScreen.this.dispose();
				}
				
				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				
			}
			
		});
	
	
	
	}

	
	
	
	
	/*public static void main(String[] args) {
		String imagePath = "C:/Images/SplashScreen2.png";
		testsplash fond = new testsplash(imagePath);
		fond.setUndecorated(true);
		fond.pack();
		fond.setLocation(200,200);
		fond.add(new JPanelNouvelleReponse());
		fond.setVisible(true);
	}*/
	
	public JPasswordField getText2(){
		return text2;
	}
	
	public JTextField getText(){
		return text;
	}
	
	public JButton getQuit(){
		return btnQuit;
	}
	
	public JButton getValid(){
		return btnValid;
	}
	
	public JLabel getLab1(){
		return lab1;
	}
	
	public JLabel getLab2(){
		return lab2;
	}
}


