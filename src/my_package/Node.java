package my_package;
/**
 * Classe nodo:
 * è il nodo di un albero Ennario
 * @author Luciano
 *
 */
public class Node {
	
	private Symbol symbol;
	private String val;
	private Node son;
	private Node brother;
	
	//Nodo interno:
	//un nodo interno di un albero Ennario è rappresentato in una struttura Binaria.
	//Infatti un nodo avente n figli, viene implementato come un nodo avente
	//un figlio ed n-1 fratelli del primo figlio, organizzati in una lista che parte dal figlio
	// e contiene tutti i fratelli. pertanto l'albero diventa binario.
	public Node(Symbol symbol, String val, Node son, Node... sons_brothers){
		
		this.symbol=symbol;
		this.val=val;
		this.son=son;
	
		Node current=this.son;
		
		for(Node brother : sons_brothers){
			if (brother==null) continue;
			current.setBrother(brother);
			current=brother;
		}
		current.setBrother(null);
	}
	
	//Nodo foglia:
	//il nodo foglia non ha figli ne fratelli all'atto della sua
	//costruzione ma potrebbe avere fratelli nel caso in cui tale
	//nodo sia figlio di un nodo ad n figli (vedi il primo costruttore)
	public Node(Symbol symbol, String val){
		
		this.symbol=symbol;
		this.val=val;
		this.son=null;
		this.brother=null;
	}
	
	//-----------------------------------------------------GETTERS E SETTERS
	public Symbol getSymbol(){
		return symbol;
	}
	
	public String getVal(){
		return val;
	}
	
	public Node getBrother(){
		return brother;
	}
	
	public Node getSon(){
		return son;
	}
	
	public void setSon(Node son){
		this.son=son;
	}
	
	public void setBrother(Node brother){
		this.brother=brother;
	}
	
	public void setVal(String val){
		
		this.val=val;
	}
	
	public void setsymbol(Symbol symbol){
		
		this.symbol=symbol;
	}
	//----------------------------------------------------------------------
}
