package test;

import modeles.*;

public class TestAppli {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Test test = new Test("Essai",20,1);
		Section sect = new Section("Section1",1);
		Question quest = new Question("Comment ca marche",new Type());
		
		sect.addQuestion(quest);
		test.addSection(sect);
		
		System.out.println(test.getSection(0).getQuestionAt(0).toString());
		System.out.println(test.toString());
		
		
		
	}

}
