package graphic_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import code_generator.Btree;
import code_generator.GenJavaCCCode;
import code_generator.JavaCCCode;
import code_generator.Node;
import compiler.ParseException;
import compiler.RPLanguage;

public class GestorePulsanteStart implements ActionListener{

	private JTextField filePath;
	private JTextArea display;
	
	//costruttore
	public GestorePulsanteStart(JTextField filePath, JTextArea display){
		this.filePath=filePath;
		this.display=display;
	}
	
	//azione
	public void actionPerformed(ActionEvent e){
		
		//parsing e costruzione dell'albero sintattico
		Node root;
		try {
			RPLanguage parser = new RPLanguage(new FileInputStream(new File(filePath.getText())));
			
			this.display.append("____________________PARSING____________________:\n\n");
			root = parser.start();
			Btree tree=new Btree(root);
		
			//generazione del codice per javaCC
			this.display.append("\n\n____________________JavaCC-code:____________________\n\n");
			GenJavaCCCode generator = new GenJavaCCCode();
			JavaCCCode code=generator.genCode(tree);
			Iterator<String> itr2=code.getLexerCode().iterator();
			while(itr2.hasNext())
				this.display.append(itr2.next()+"\n");
			this.display.append("\n");
			itr2=code.getParserCode().iterator();
			while(itr2.hasNext())
				this.display.append(itr2.next()+"\n");
			
		} catch (ParseException | FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}