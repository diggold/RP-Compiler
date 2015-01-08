package code_generator;

import java.util.HashMap;

//questa classe implementa la tabella dei simboli
//per il compilatore dell' RPLanguage mediante l'utilizzo di
//una hash table
public class STable_regole {
	
	//questa tabella è composta dal campo "regola"(chiave)
	//e dal campo Info che rappresenta delle informazioni
	//associate alla regola
	private HashMap<String, Info> table;
	
	//costruttore
	public STable_regole(){
		
		table=new HashMap<String, Info>();
	}
	
	//questo metodo preleva le informazioni di una
	//determinata regola
	public void put(String key, Info value){
		
		table.put(key, value);
	}
	
	//questo metodo inserisce le informazioni di
	//una determinata regola
	public Info get(String key){
		
		return table.get(key);
	}
	
	//questo metodo informa circa la presenza, in tabella,
	//di una determinata regola (chiave della tabella)
	public boolean contains(String key){
		
		return table.containsKey(key);
	}

}
