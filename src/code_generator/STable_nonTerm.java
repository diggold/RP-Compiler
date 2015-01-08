package code_generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

//questa classe implementa la tabella dei simboli per
//i nodi non terminali della grammatica.
//i campi della tabella sono:
//	
//		-nome del non terminale
//		-flag che indica se per quel non terminale è stata definita una regola di produzione
public class STable_nonTerm {
	
	private HashMap<String, Boolean> table;
	
	//costruttore
	public STable_nonTerm(){
		
		table=new HashMap<String, Boolean>();
	}
	
	//inserisce un record in tabella
	public void put(String key, Boolean value){
		
		table.put(key, value);
	}
	
	//preleva il valore data la chiave
	public Boolean get(String key){
		
		return table.get(key);
	}
	
	//questo metodo informa circa la presenza, in tabella,
	//di un determinato non terminale
	public Boolean contains(String key){
		
		return table.containsKey(key);
	}
	
	//restituisce un keySet della tabella
	public Set<String> keySet() {

		return table.keySet();
	}

}
