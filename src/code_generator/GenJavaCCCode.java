package code_generator;

//questa classe si occupa della generazione del codice
//javaCC a partire dall'albero sintattico
public class GenJavaCCCode {
	
	private JavaCCCode code;
	
	//metodo pubblico per la generazione del codice
	public JavaCCCode genCode(Btree tree){
		
		code=new JavaCCCode();
		
		code.addLexerLine("options{\n}\n\nPARSER_BEGIN(LanguageName)\npublic class LanguageName\n{\n}\nPARSER_END(LanguageName)\n\nTOKEN:\n{");
		code.addLexerLine("}");
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
					String line=code.removeLastParserLine();
					line=line+"{}";
					code.addParserLine(line);
					
					break;
				}
				
				case NON_TERM:{
					
					//richiama la funzione corrispondente al simbolo non terminale
					String line=code.removeLastParserLine();
					line=line+node.getVal().replace("<", "").replace(">", "") + "()";
					code.addParserLine(line);
					
					break;
				}
				
				case TERM:{
					
					//concatena il token corrispondente al simbolo terminale
					String line=code.removeLastParserLine();
					line=line+"<"+node.getVal()+">";
					code.addParserLine(line);
					
					//inserisce il token tra le specifiche per il leker, se esso non è già stato inserito
					if(!(code.containsLexerLine("<"+node.getVal()+":\t>") || code.containsLexerLine("|<"+node.getVal()+":\t>"))){
						
						code.removeLastLexerLine();
						if(code.lexerLines()==1)
							code.addLexerLine("<"+node.getVal()+":\t>");
						else
							code.addLexerLine("|<"+node.getVal()+":\t>");
						code.addLexerLine("}");
					}
					
					break;
				}
				
				case OPZ:{
					
					//apre la parentesi per raggruppare i simboli terminali e non terminali opzionali
					String line=code.removeLastParserLine();
					line=line+"(";
					code.addParserLine(line);
					
					//simboli terminali e non terminali opzionali
					this.genCode(node.getSon());
					
					//chiude la parentesi di raggruppamento e inserisce il simbolo di opzionalita
					line=code.removeLastParserLine();
					line=line+")?";
					code.addParserLine(line);
					
					break;
				}
					
				case NUM_LOOKAHEAD:{
					break;
				}
				
				case OPPURE:{
					
					//parte destra della regola
					this.genCode(node.getSon());
					
					//|
					code.addParserLine("|");
					
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
				
				case REGOLA:{
					
					//void testa_regola():
					//{}
					//{
					//LOOKAHEAD(n)
					code.addParserLine("void " + node.getSon().getBrother().getVal().replace("<", "").replace(">", "") + "():");
					code.addParserLine("{}");
					code.addParserLine("{");
					if(node.getSon().getVal()!="1")                            			 //se il numero di simboli di lookahead della
						code.addParserLine("LOOKAHEAD(" + node.getSon().getVal() +")\n");//regola è =1 allora l'indicazione viene omessa
					//...corpo della regola...
					this.genCode(node.getSon().getBrother().getBrother());
					//}
					code.addParserLine("}");
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
