package my_package;

public class Info {
	
	private Node regola, corpo_regola;
	private int lookahead_number;
	
	public Info(Node regola, Node corpo_regola, int lookahead_number){
		this.regola=regola;
		this.corpo_regola=corpo_regola;
		this.lookahead_number=lookahead_number;
	}
	
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
