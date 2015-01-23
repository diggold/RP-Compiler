package code_generator;

//questa classe implementa l'insieme delle
//informazioni di una regola.
//tali informazioni vengono salvate come valori associati
//alle regole, in tabella dei simboli
public class Info {
	
	//le informazioni di una determinata regola sono:
	//
	//	-corpo_regola: riferimento al nodo del corpo della regola nell'albero sintattico
	//	-lookahead: riferimento al nodo relativo al numero di simboli di lookahead utilizzati dal parser.
	private Node corpo_regola, lookahead;
	
	//costruttore
	public Info(Node corpo_regola, Node lookahead){
		this.corpo_regola=corpo_regola;
		this.lookahead=lookahead;
	}
	
	//---------------------------------------------------------GETTERS/SETTERS
	
	public void setCorpoRegola(Node corpo_regola){
		this.corpo_regola=corpo_regola;
	}
	
	public void setLookahead(Node lookahead){
		this.lookahead=lookahead;
	}
	
	public Node getCorpoRegola(){
		return this.corpo_regola;
	}
	
	public Node getLookahead(){
		return this.lookahead;
	}
}
