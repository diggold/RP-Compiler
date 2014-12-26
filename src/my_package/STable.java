package my_package;

import java.util.HashMap;
import java.util.Iterator;

public class STable {
	
	private HashMap<String, Node> table;
	
	public STable(){
		
		table=new HashMap<String, Node>();
	}
	
	public void put(String key, Node value){
		
		table.put(key, value);
	}
	
	public Node get(String key){
		
		return table.get(key);
	}
	
	public boolean contains(String key){
		
		return table.containsKey(key);
	}
	
	public void stampa(){
		
		//iterazione sulle chiavi per stampare i valori
		System.out.println("ITERAZIONE SULLE CHIAVI E STAMPA DEI VALORI:\n");
		Iterator<String> i=table.keySet().iterator();
				
		String key; 
		Node value;
		while(i.hasNext()){
			key=i.next(); 
			value=table.get(key);
			System.out.println("<"+key+", "+value+">");
		}
	}

}
