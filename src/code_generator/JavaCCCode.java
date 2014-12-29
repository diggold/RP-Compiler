package code_generator;
import java.util.ArrayList;

//questa vlasse modella il codice JavaCC
//che è suddiviso in specifiche per il lexer e specifiche 
//per il parser
public class JavaCCCode {

	private ArrayList<String> parserCode;//specifiche per il lexer
	private ArrayList<String> lexerCode;//specifiche per il parser
	
	//costruttore
	public JavaCCCode(){
		this.parserCode=new ArrayList<String>();
		this.lexerCode=new ArrayList<String>();
	}
	
	//inserimento linea di codice tra le specifiche per il lexer
	public void addLexerLine(String line){
		lexerCode.add(line);
	}
	
	//inserimento linea di codice tra le specifiche per il parser
	public void addParserLine(String line){
		parserCode.add(line);
	}
	
	//restituisce il codice di specifica per il parser
	public ArrayList<String> getParserCode(){
		return this.parserCode;
	}
	
	//restituisce il codice di specifica per il lexer
	public ArrayList<String> getLexerCode(){
		return this.lexerCode;
	}
	
	//rimuove e restituisce l'ultima riga di codice del parser
	public String removeLastParserLine(){
		return parserCode.remove(parserCode.size()-1);
	}
	
	//rimuove e restituisce l'ultima riga di codie del lexer
	public String removeLastLexerLine(){
		return lexerCode.remove(lexerCode.size()-1);
	}
	
	//controlla se tra le linee di codice del lexer che una particolare linea di codice
	public boolean containsLexerLine(String line){
		
		return lexerCode.contains(line);
	}
	
	//restituisce il numero di linee del codice di specifica per il lexer
	public int lexerLines(){
		return lexerCode.size();
	}
	
}
