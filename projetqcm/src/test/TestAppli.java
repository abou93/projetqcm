package test;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import securite.*;


public class TestAppli {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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

		
		//SplashScreen
		/*JFrame f = new JFrame();
		f.setBounds(500, 400, 640, 400);
		f.setUndecorated(true);
		JLabel l = new JLabel();
		l.setPreferredSize(new Dimension(640,400));
		l.setIcon(new ImageIcon("SplashScreen2.png"));
		JPanel splash = new JPanel();
		splash.add(l);
		splash.setBorder(BorderFactory.createRaisedBevelBorder());
		f.add(splash);
		f.setVisible(true);*/
		
		
		/*Test test = new Test("Essai",20,1);
		Section sect = new Section("Section1",1);
		Question quest = new Question("Comment ca marche",new Type());
		quest.addReponse(new Reponse("tres bien",1,false,quest));
		quest.addReponse(new Reponse("assez bien",2,true,quest));
		quest.addReponse(new Reponse("pas bien",3,false,quest));
		quest.setSection(sect);
		quest.setType(DalQuestion.selectType(0));
		sect.addQuestion(quest);
		test.addSection(sect);
		
			
		DalTest.insertTest(test);
		DalTest.insertSection(sect);
		DalQuestion.insertQuestion(quest);
		for(Reponse r : quest.getListeReponses()){
			DalQuestion.insertReponse(r);
		}*/
		
		/*Test test = DalTest.selectTestByNom("Essai");
		Vector<Section> sect = DalTest.selectSectionByTest("Essai");
		for(Section s : sect){
			s.setQuestions(DalQuestion.selectQuestions(s));
			for(Question q : s.getQuestions()){
				q.setListesReponses(DalQuestion.selectReponse(q));
			}
		}
		
		for(Section s : sect){
			for (Question q : s.getQuestions()){
				System.out.println(q.toString());
				System.out.println(q.getType().getLibelle());
				for (Reponse r : q.getListeReponses()){
					System.out.println(r.toString());
				}
			}
		}
		
		
		
		
		Vector<Stagiaire> s = DalStagiaire.selectStagiairesPromotion("DL90");
		Enumeration<Stagiaire> enu = s.elements();
		while(enu.hasMoreElements()){
			Stagiaire sta = enu.nextElement();
			System.out.println(sta.toString().trim());
			System.out.println(DalStagiaire.selectStagiaire(sta.getId()).toString());
		}*/
		
		
		// Affiche une icone dans la barre de tache		
//		if (SystemTray.isSupported()) {
//			ImageIcon i = new ImageIcon("C:/Users/steve/Downloads/iconex_v_samples/v_collections_png/16x16/16x16plain/flag_wales.png");
//			Image im = i.getImage();
//			TrayIcon tic = new TrayIcon(im,"Info Bulle");
//			SystemTray tray = SystemTray.getSystemTray();
//			try {
//				tray.add(tic);
//				// tray.remove(tic);  Pour enlever l'icone
//			} catch (AWTException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		 
		
		/*hashPassword calculhash = new hashPassword();
		String passe = "LEFORT Steve Password";
		String passeHash = null;
		
		try {
		
			passeHash = calculhash.getHash(passe);
			System.out.println(passe);
			System.out.println(passeHash);
			String passeBad = "MOTPASSE";
			String mot = ResourceBundle.getBundle("securite").getString("motdepasse");
			System.out.println("Properties :" + mot);
			
			IOProperties secur = new IOProperties();
			Properties securitePass = new Properties();
			securitePass.setProperty("motDePasseFormateur",passeHash);
			secur.saveProperties(securitePass,"C:/password.properties", "passwordGestion QCM");
			
			secur.displayProperties(securitePass);
			//Properties p = secur.loadProperties("D:/password.properties");
			
			//System.out.println(p.getProperty("motDePasseFormateur"));
			System.out.println(passeHash.equals(calculhash.getHash(passeBad)));
			//System.out.println(passeHash.equals(p.getProperty("motDePasseFormateur").trim()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* final SplashScreen splash = SplashScreen.getSplashScreen();
	        if (splash == null) {
	            System.out.println("SplashScreen.getSplashScreen() returned null");
	            return;
	        }
	        Graphics2D g = splash.createGraphics();
	        if (g == null) {
	            System.out.println("g is null");
	            return;
	        }*/

		String imagePath = "C:/Images/SplashScreen2.png";
		testsplash fond = new testsplash(imagePath);
		fond.setUndecorated(true);
		fond.pack();
		fond.setLocation(200,200);
		//fond.add(new JPanelNouvelleReponse());
//		JTextField text = new JTextField();
//		text.setPreferredSize(new Dimension(100,25));
		//text.setBounds(20, 20, 100, 25);
		//fond.add(text);
		fond.setVisible(true);
	   // new fenPrincipale().setVisible(true);
		
		
		
	}

}
