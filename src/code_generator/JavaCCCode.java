package code_generator;

import java.util.ArrayList;

public class JavaCCCode {

	private ArrayList<String> parserCode;
	private ArrayList<String> lexerCode;
	
	public JavaCCCode(){
		this.parserCode=new ArrayList<String>();
		this.lexerCode=new ArrayList<String>();
	}
	
	public void addLexerLine(String line){
		lexerCode.add(line);
	}
	
	public void addParserLine(String line){
		parserCode.add(line);
	}
	
	public ArrayList<String> getParserCode(){
		return this.parserCode;
	}
	
	public ArrayList<String> getLexerCode(){
		return this.lexerCode;
	}
	
	public String removeLastParserLine(){
		return parserCode.remove(parserCode.size()-1);
	}
	
	public String removeLastLexerLine(){
		return lexerCode.remove(lexerCode.size()-1);
	}
	
	public boolean containsLexerLine(String line){
		
		return lexerCode.contains(line);
	}
	
	public int lexerLines(){
		return lexerCode.size();
	}
	
}
