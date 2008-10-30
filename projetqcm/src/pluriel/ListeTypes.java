package pluriel;

import java.util.Vector;

import modeles.Type;

public class ListeTypes extends Vector<Type>{
	private static final long serialVersionUID = 1L;

	@Override
	public int indexOf(Object o) {
		
		for(int i = 0 ; i < this.size(); i++){
			if(((Type)this.elementAt(i)).getNumero()==((Type)o).getNumero()){
				return i ;
			}
		}
		return -1;
		
	}

	
	
}
