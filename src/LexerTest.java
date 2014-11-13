import java.io.IOException;

public class LexerTest {

public static void main(String[] args) throws IOException {
		
		Token t; 				  /*token*/
		RPLanguageTokenManager tm;/*lexer*/
		
		//apertura del file sorgente
		java.io.InputStream infile;
		infile = new java.io.FileInputStream("input/regole1.txt");
		
		//definizione del lexer (sul file sorgente)
		tm = new RPLanguageTokenManager(new SimpleCharStream(infile));
		
		//lettura dei token (e stampa a video dei tipi)
		t = tm.getNextToken();
		while (t.kind != RPLanguageConstants.EOF) {
			
			System.out.println(t.kind+" "+t.image);
			t = tm.getNextToken();
		}
		
		infile.close();
	}
}
