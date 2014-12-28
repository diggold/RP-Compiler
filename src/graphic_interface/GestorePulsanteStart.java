package graphic_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import javax.swing.JButton;
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
	private JButton clearButton;
	
	//costruttore
	public GestorePulsanteStart(JTextField filePath, JTextArea display, JButton clearButton){
		this.filePath=filePath;
		this.display=display;
		this.clearButton=clearButton;
	}
	
	//azione
	public void actionPerformed(ActionEvent e){
		
		//parsing e costruzione dell'albero sintattico
		Node root;
		try {
			
			File inputFile = new File(filePath.getText());
			File outputFile = new File("./output/"+inputFile.getName()+".jj");
	  	  	PrintStream pr=new PrintStream(new FileOutputStream(outputFile));
			RPLanguage parser = new RPLanguage(new FileInputStream(new File(filePath.getText())));
			
			this.display.append("____________________PARSING____________________:\n\n");
			root = parser.start();
			Btree tree=new Btree(root);
		
			//generazione del codice per javaCC
			this.display.append("\n\n____________________JavaCC-code:____________________\n\n");
			GenJavaCCCode generator = new GenJavaCCCode();
			JavaCCCode code=generator.genCode(tree);
			Iterator<String> itr2=code.getLexerCode().iterator();
			String line=null;
			while(itr2.hasNext()){
				line=itr2.next();
				this.display.append(line+"\n");
				pr.println(line);
			}
			this.display.append("\n");
			itr2=code.getParserCode().iterator();
			while(itr2.hasNext()){
				line=itr2.next();
				this.display.append(line+"\n");
				pr.println(line);
			}
			
			this.display.append("\n\nè stato generato il file di output: "+outputFile.getPath()+"\n");
			this.clearButton.setEnabled(true);
			pr.close();			
		} catch (ParseException | FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}