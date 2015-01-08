package graphic_interface;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import code_generator.Btree;
import code_generator.GenJavaCCCode;
import code_generator.JavaCCCode;
import code_generator.Node;
import compiler.ParseException;
import compiler.RPLanguage;
import compiler.TokenMgrError;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

//questa classe rappresenta implementa l'interfaccia grafica
//dell'applicazione
public class Interface extends OutputStream{
	
	//Main: avvia l'interfaccia
	@SuppressWarnings("resource")
	public static void main(String args[]){
		new Interface();
	}
	
	private JFrame frame;
	private JButton startButton;
	private JButton clearButton;
	private JButton addFileButton;
	private JFileChooser fileChooser;
	private JTextField textFile;
	private  JTextArea display;
	public static PrintStream displayPrint;
	
	//costruttore
	public Interface(){
		
		//---------------------------------------------------------------------------elementi di interfaccia
		//creazione frame e framePanel
		int frameWidth=600;
		int frameHeight=500;
		this.frame=new JFrame("finestra");
		this.frame.setLayout(null);
		this.frame.setBounds(0,0,frameWidth,frameHeight);
		this.frame.setResizable(true);
		JPanel framePanel=new JPanel();
		frame.setContentPane(framePanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(false);
		framePanel.setVisible(false);
		
		//display
		display=new JTextArea();
		display.setVisible(true);
		display.setBackground(new Color(0,0,0));
		display.setForeground(new Color(255,255,255));
		display.setFont(new Font(Font.DIALOG_INPUT, Font.CENTER_BASELINE, 16));
		JScrollPane displayScrollPanel=new JScrollPane(display);
		displayScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		displayScrollPanel.setVisible(true);
		displayPrint=new PrintStream(this);
		
		//creazione bottone start
		this.startButton=new JButton();
		this.startButton.setText("Start");
		this.startButton.setVisible(true);
		this.startButton.setEnabled(false);
		
		//creazione bottone clear
		this.clearButton=new JButton();
		this.clearButton.setText("Clear");
		this.clearButton.setVisible(true);
		this.clearButton.setEnabled(false);
		
		//creazione FileChooser
		this.fileChooser=new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".rp", "rp");
		this.fileChooser.setFileFilter(filter);
		this.fileChooser.setVisible(true);
			
		//creazione bottone addFile
		this.addFileButton=new JButton();
		this.addFileButton.setText("add file");
		this.addFileButton.setVisible(true);
		
		//creazione textField file path
		this.textFile=new JTextField("...");
		this.textFile.setHorizontalAlignment(JTextField.CENTER );
		this.textFile.setVisible(true);
		this.textFile.setEditable(false);
		this.textFile.setBackground(new Color(255,255,255));
		
		//--------------------------------------------------------------------------------------------------------layout
		//grid bag layout
		GridBagLayout gbl=new GridBagLayout();
		framePanel.setLayout(gbl);
		framePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		GridBagConstraints c=new GridBagConstraints();
		
		//pannello per i bottoni start e clear
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(startButton);
		panel.add(clearButton);	
		
		//aggiunta elementi al framePanel
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.weighty=0.05;
		//c.weightx=0.05;
		c.gridx=0;
		c.gridy=0;
		framePanel.add(addFileButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.weighty=0.05;
		c.weightx=1;
		c.gridx=1;
		c.gridy=0;
		framePanel.add(textFile, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weighty=1;
		c.weightx=1;
		c.gridwidth=2;
		c.gridx=0;
		c.gridy=1;
		framePanel.add(displayScrollPanel, c);
		
		c.fill = GridBagConstraints.CENTER;
		c.weighty=0.05;
		c.weightx=1;
		c.gridx=0;
		c.gridy=2;
		framePanel.add(panel, c);
		
		//visualizzazione del frame
		frame.setVisible(true);
		framePanel.setVisible(true);
		
		
		//--------------------------------------------------------------------------------------------------------eventi
		
		//evento pressione bottone addFile
		this.addFileButton.addActionListener(new ActionListener(){

			//viene aperta la finestra di dialogo
			//per la scelta del file da compilare
			public void actionPerformed(ActionEvent e) {

				switch(fileChooser.showOpenDialog(frame)){
				
					case JFileChooser.APPROVE_OPTION:{
						String path=fileChooser.getSelectedFile().getPath();
						
						try{
							//scelto il file da compilare viene abilitato il pulsante
							//Start dell'interfaccia, per avviare la compilazione
							textFile.setText(path);
							startButton.setEnabled(true);
						} catch (java.lang.NullPointerException ex) {textFile.setText("...");}
						break;
					}
				}
			}
			
			
		});
		
		//evento pressione pulsante Start
		this.startButton.addActionListener(new ActionListener(){
			
			
			public void actionPerformed(ActionEvent e) {
				
				PrintStream pr=null;
				try {
					//stream di input sul file da parsare
					File inputFile = new File(textFile.getText());
					
					//parser
					RPLanguage parser = new RPLanguage(new FileInputStream(new File(textFile.getText())));
					

					//---------------------------------------------------------------------parsing e costruzione dell'albero sintattico
					displayPrint.println("Parsing:\n");
					Node root=parser.start();
					Btree tree=new Btree(root);//albero sintattico
					//-----------------------------------------------------------------------------------------------------------------

					//-------------------------------------------------------------------------------------------------controllo errori
					//cè un errore se per un particolare non terminale della grammatica
					//non è stata definita la relativa regola
					ArrayList<String> errori=new ArrayList<String>();
					Iterator<String> itr=parser.table_nonTerm.keySet().iterator();
					String key; 
					Boolean value;
					while(itr.hasNext()){
						key=itr.next(); 
						value=parser.table_nonTerm.get(key);
						if (value == false) //se il non terminale, in tabella dei simboli, presenta il flag "false"
							errori.add(key);//allora vi è l'aggiunta del nome del non terminale alla lista degli errori
					}
					//------------------------------------------------------------------------------------------------------------------

					//---------------------------------------------------------------------------------generazione del codice per javaCC
					//se non vi sono errori 
					if(errori.isEmpty()){
						
						//stream di output sul nuovo file .jj
						File outputFile = new File("./output/"+inputFile.getName()+".jj");
						pr=new PrintStream(new FileOutputStream(outputFile));
						
						//generatore di codice a partire dall'albero sintattico
						displayPrint.println("\n\nJavaCC-code:\n");
						GenJavaCCCode generator = new GenJavaCCCode();//generatore di codice a partire dall'albero sintattico
						JavaCCCode code=generator.genCode(tree);//codice javaCC generato
						
						//scrittura in output della specifica per il lexer
						String line=null;
						Iterator<String> itr2=code.getLexerCode().iterator();
						while(itr2.hasNext()){				
						  line=itr2.next();
						  displayPrint.println(line);
						  pr.println(line);
						}
						
						//scrittura in output della specifica per il parser
						displayPrint.println();					
						itr2=code.getParserCode().iterator();
						while(itr2.hasNext()){
						  line=itr2.next();
						  displayPrint.println(line);
						  pr.println(line);
						}
						pr.close();
						displayPrint.println("\nE' stato creato il file di output: "+"./output/"+outputFile.getName()+"\n");
					}
					//se vi sono errori allora vengono segnalati in output
					//senza generare il codice
					else{
					  String line=null;
					  displayPrint.println("\nERRORE: per i seguenti simboli non terminali non e' stata definita una regola:");
					  itr=errori.iterator();
					  while(itr.hasNext()){
						  line=itr.next();
						  displayPrint.println(line);
						}
					}
					clearButton.setEnabled(true);
					
				} catch (FileNotFoundException | ParseException | TokenMgrError e1) {
					//ridirezionamento dell'output di errore da System.out
					//al display dell'interfaccia
					e1.printStackTrace(displayPrint);
					clearButton.setEnabled(true);
				}				
			}
			
		});
		
		//evento pressione tasto clear
		this.clearButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				display.setText("");
				clearButton.setEnabled(false);
			}
		});
		
	}

	@Override
	public void write(int b) throws IOException {
		display.append(String.valueOf((char) b));	
	};

}
