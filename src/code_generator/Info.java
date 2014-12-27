package code_generator;

//questa classe implementa l'insieme delle
//informazioni di una regola.
//tali informazioni vengono salvate come valori associati
//alle regole, in tabella dei simboli
public class Info {
	
	//le informazioni di una determinata regola sono:
	//
	//	-regola: riferimiento al nodo della regola nell'albero sintattico
	//	-corpo_regola: riferimento al nodo del corpo della regola nell'albero sintattico
	//	-lookahead_number: numero di simboli di lookahead utilizzati dal parser top down.
	private Node regola, corpo_regola;
	private int lookahead_number;
	
	//costruttore
	public Info(Node regola, Node corpo_regola, int lookahead_number){
		this.regola=regola;
		this.corpo_regola=corpo_regola;
		this.lookahead_number=lookahead_number;
	}
	
	//---------------------------------------------------------GETTERS/SETTERS
	public void setRegola(Node regola){
		this.regola=regola;
	}
	
	public void setCorpoRegola(Node corpo_regola){
		this.corpo_regola=corpo_regola;
	}
	
	public void setLookaheadNumber(int lookahead_number){
		this.lookahead_number=lookahead_number;
	}
	
	public Node getRegola(){
		return this.regola;
	}
	
	public Node getCorpoRegola(){
		return this.corpo_regola;
	}
	
	public int getLookaheadNumber(){
		return this.lookahead_number;
	}
}
