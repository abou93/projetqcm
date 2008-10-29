package pluriel;

import java.util.Vector;

import modeles.Section;

public class ListeSections extends Vector<Section>{

	@Override
	public boolean contains(Object o) {
		Section section = (Section) o;
		for(Section s : this){
			if((s.getNumero()==section.getNumero())&(s.getNom().equals(section.getNom()))){
				return true;
			}
		}
		return false;
	}

	@Override
	public int indexOf(Object o) {// TODO Auto
		Section section = (Section) o;
		for(int i = 0 ; i<this.size() ; i++){
			if((this.elementAt(i).getNumero()==section.getNumero())&(this.elementAt(i).getNom().equals(section.getNom()))){
				return i;
			}
		}
		return -1;
	}

	

	
	
}
