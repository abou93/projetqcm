package test;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import dal.DalStagiaire;
import modeles.*;

public class TestAppli {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//mettre le look and feel souhaité
		try {
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		Test test = new Test("Essai",20,1);
		Section sect = new Section("Section1",1);
		Question quest = new Question("Comment ca marche",new Type());
		
		sect.addQuestion(quest);
		test.addSection(sect);
		
		System.out.println(test.getSection(0).getQuestionAt(0).toString());
		System.out.println(test.toString());
		
		Vector<Stagiaire> s = DalStagiaire.selectStagiairesPromotion("DL90");
		Enumeration<Stagiaire> enu = s.elements();
		while(enu.hasMoreElements()){
			Stagiaire sta = enu.nextElement();
			System.out.println(sta.toString().trim());
			System.out.println(DalStagiaire.selectStagiaire(sta.getId()).toString());
		}
		
	TestFrame f = new TestFrame();
	f.setVisible(true);
		
	}

}
