package vues;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import securite.IOProperties;
import securite.hashPassword;

/**
 * @author D.Team
 *
 */
public class SplashScreenQCM  extends JFrame {
	private static final long serialVersionUID = 1L;
	private Container c;
	private JPanel imagePanel;
	private Point pt;
	
	//Composants du splashscreen
	JTextField text;
	JPasswordField text2;
	JLabel lab1;
	JLabel lab2;
	JLabel lab3;
	JButton btnValid;
	JButton btnQuit;

	
	//Constructeur
	public SplashScreenQCM() {
		super("Gestion de test QCM");
		
		initialize();
		
		// Affiche une icone dans la barre de tache		
		if (SystemTray.isSupported()) {
			ImageIcon i = new ImageIcon("Images/LogoENI16.png");
			Image im = i.getImage();
			TrayIcon tic = new TrayIcon(im,"Eni - Gestion de test QCM");
			SystemTray tray = SystemTray.getSystemTray();
			try {
				tray.add(tic);
				// tray.remove(tic);  Pour enlever l'icone
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
	}

		
	//Initialisation
	private void initialize() {
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		c = getContentPane();
		imagePanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paint(Graphics g) {
				try {
					BufferedImage image = ImageIO.read(getClass().getResource("/Images/SplashScreen3.png"));//new File(filePath));
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
        pt = new Point( (int)r.width/2, (int)r.height/2 );
        Point loc = new Point( pt.x - 320, pt.y - 200 );
		this.setLocation(loc);
				
		//Listener sur le bouton valider
		btnValid.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				hashPassword hash = new hashPassword();
				try {
				String passeSaisi = hash.getHash(text2.getText());
				IOProperties ioProperties = new IOProperties();
				Properties password  = ioProperties.loadProperties("securite.properties");
				password.getProperty("motdepasse");
				String passeDefaut =password.getProperty("motdepasse");
				 	

				if(passeSaisi.equals(passeDefaut)){
					fenPrincipale f = new fenPrincipale();
					f.setLocationRelativeTo(f.getParent());
				//	Point loc = new Point( pt.x - f.getWidth()/2,pt.y - f.getHeight()/2);
				//	f.setLocation(loc);
					f.setVisible(true);
					SplashScreenQCM.this.dispose();
				}else{
					text2.setBackground(Color.RED);
					text2.setText("");
					text2.requestFocusInWindow();
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
	
		// Listener sur la zone de password
		text2.addKeyListener(new KeyAdapter(){
			
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					btnValid.doClick();
				}else{
					text2.setBackground(Color.WHITE);
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


