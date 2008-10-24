package test;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import dal.DalQuestion;
import dal.DalStagiaire;
import dal.DalTest;
import modeles.*;

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
		
		Test test = DalTest.selectTestByNom("Essai");
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
		}
		
		
		new fenPrincipaleBis().setVisible(true);
		
	
		
	}

}
