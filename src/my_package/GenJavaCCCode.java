package my_package;
import java.util.Vector;

//questa classe si occupa della generazione del codice
//javaCC a partire dall'albero sintattico
public class GenJavaCCCode {
	
	private Vector<String> code;
	
	//metodo pubblico per la generazione del codice
	public Vector<String> genCode(Btree tree){
		
		code=new Vector<String>();
		
		this.genCode(tree.getRoot());
		
		return code;
	}
	
	//metodo privato, ricorsivo, per la generazione del codice
	private void genCode(Node node){
		
		if(node!=null){
			
			Symbol symbol=node.getSymbol();
			
			switch(symbol){
					
				case EPS:{
					
					//concatena il nonterminale epsilon ("{}" in javaCC)
					String line=code.remove(code.size()-1);
					line=line+"{}";
					code.addElement(line);
					
					break;
				}
				
				case NON_TERM:{
					
					//richiama la funzione corrispondente al simbolo non terminale
					String line=code.remove(code.size()-1);
					line=line+node.getVal().replace("<", "").replace(">", "") + "()";
					code.addElement(line);
					
					break;
				}
				
				case TERM:{
					
					//concatena il token corrispondente al simbolo terminale
					String line=code.remove(code.size()-1);
					line=line+"<"+node.getVal()+">";
					code.addElement(line);
					
					break;
				}
				
				case OPZ:{
					
					//apre la parentesi per raggruppare i simboli terminali e non terminali opzionali
					String line=code.remove(code.size()-1);
					line=line+"(";
					code.addElement(line);
					
					//simboli terminali e non terminali opzionali
					this.genCode(node.getSon());
					
					//chiude la parentesi di raggruppamento e inserisce il simbolo di opzionalita
					line=code.remove(code.size()-1);
					line=line+")?";
					code.addElement(line);
					
					break;
				}
					
				case NUM_LOOKAHEAD:{
					break;
				}
				
				case OPPURE:{
					
					//parte destra della regola
					this.genCode(node.getSon());
					
					//|
					code.addElement("|");
					
					//altra parte destra della regola
					this.genCode(node.getSon().getBrother());
					
					break;
				}
				
				case CONCAT:{
					
					//simbolo terminale o non terminale
					this.genCode(node.getSon());
					
					//concatenato a...
					
					//simbolo terminale o non terminale
					this.genCode(node.getSon().getBrother());
					
					break;
				}
					
				case REGOLA_LL1:{
					
					//void testa_regola():
					//{}
					//{
					code.addElement("void " + node.getSon().getVal().replace("<", "").replace(">", "") + "():");
					code.addElement("{}");
					code.addElement("{");
					//...corpo della regola...
					this.genCode(node.getSon().getBrother());
					//}
					code.addElement("}");
					break;
				}
				
				case REGOLA_LLK:{
					
					//void testa_regola():
					//{}
					//{
					//LOOKAHEAD(n)
					code.addElement("void " + node.getSon().getBrother().getVal().replace("<", "").replace(">", "") + "():");
					code.addElement("{}");
					code.addElement("{");
					code.addElement("LOOKAHEAD(" + node.getSon().getVal() +")\n");
					//...corpo della regola...
					this.genCode(node.getSon().getBrother().getBrother());
					//}
					code.addElement("}");
					break;
				}
				
				case REGOLE:{
					
					//regola corrente
					this.genCode(node.getSon());
					//regole successive
					this.genCode(node.getSon().getBrother());
					
					break;	
				}
				
				default:break;
			
			}
		}
	}
	
}
