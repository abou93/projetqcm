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

	
	
}
