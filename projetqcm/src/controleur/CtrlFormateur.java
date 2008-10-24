package controleur;

import java.util.Vector;

import dal.DalTest;

import modeles.*;

public class CtrlFormateur {
	
	static CtrlFormateur instance;
	
	private CtrlFormateur(){
		super();
	}
	
	public static CtrlFormateur getCtrlFormateur(){
		if(instance == null){
			instance=new CtrlFormateur();
		}
		return instance;
	}
	
	public Vector<Test> getListeTests(){
		return DalTest.selectAllTest();
	}
	
}
