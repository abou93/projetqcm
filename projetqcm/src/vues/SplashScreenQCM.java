package vues;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import securite.hashPassword;

/**
 * @author D.Team
 *
 */
public class SplashScreenQCM  extends JFrame {
	private Container c;
	private JPanel imagePanel;
	private String filePath;
	
	
	//Composants du splashscreen
	JTextField text;
	JPasswordField text2;
	JLabel lab1;
	JLabel lab2;
	JLabel lab3;
	JButton btnValid;
	JButton btnQuit;

	
	//Constructeur
	public SplashScreenQCM(String filePath) {
		super("Image de fond");
		this.filePath = filePath;
		initialize();
	}

	
	//Initialisation
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
		
		//Centrer le splashscreen
		GraphicsEnvironment ge = GraphicsEnvironment.
        getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        GraphicsDevice gd = gs[0];
        GraphicsConfiguration[] gc = gd.getConfigurations();
		Rectangle r = gc[0].getBounds();
        Point pt = new Point( (int)r.width/2, (int)r.height/2 );
        Point loc = new Point( pt.x - 320, pt.y - 200 );
		this.setLocation(loc);
	
		
		//Listener sur le bouton valider
		btnValid.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				hashPassword hash = new hashPassword();
				try {
				String passeSaisi = hash.getHash(text2.getText());
				String passeDefaut =hash.getHash(ResourceBundle.getBundle("securite").getString("motdepasse")); 	

				if(passeSaisi.equals(passeDefaut)){
					new fenPrincipale().setVisible(true);
					SplashScreenQCM.this.dispose();
				}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
			
		});
		
		
		//Listener sur le bouton quitter
		btnQuit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
			
		});
	
		
		text2.addKeyListener(new KeyAdapter(){
			
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					btnValid.doClick();
				}
			}
		});
	
	}

	
	
	/***
	 * Accesseurs aux composant du splashscreen
	 */
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

