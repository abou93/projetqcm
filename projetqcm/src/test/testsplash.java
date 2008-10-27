package test;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Created on Jul 19, 2005
 *
 */

/**
 * @author Fery.P
 *
 */
public class testsplash  extends JFrame {
	private Container c;
	private JPanel imagePanel;
	private String filePath;

	public testsplash(String filePath) {
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
		c.add(imagePanel);
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
}

