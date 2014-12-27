package graphic_interface;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javafx.stage.FileChooser;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.print.attribute.AttributeSet;

import java.awt.Color;


public class Interface{
	
	private JFrame frame;
	private JButton startButton;
	private JButton addFileButton;
	private JFileChooser fileChooser;
	private JTextField textFile;
	private static JTextArea display;
//	private JTextField textPackageName;
//	private JCheckBox chBoxDisassReass;
//	private JCheckBox chBoxChanPack;
//	private JCheckBox chDataEncoding;
//	private JCheckBox chBoxInsJunk;
//	private JCheckBox chBoxRepacking;
	
	public Interface(){
		
//---------------------------------------------------------------------------elementi di interfaccia
		//creazione frame
		int frameWidth=1000;
		int frameHeight=700;
		this.frame=new JFrame("finestra");
		this.frame.setLayout(null);
		this.frame.setBounds(0,0,frameWidth,frameHeight);
		this.frame.setResizable(false);
		
		//display
		int displayX=this.frame.getX();
		int displayY=this.frame.getY()+40;
		int displayWidth=frameWidth;
		int displayHeigh=frameHeight-150;
		this.display=new JTextArea();
		this.display.setVisible(true);
		this.display.setBackground(new Color(0,0,0));
		this.display.setForeground(new Color(255,255,255));
		this.display.setFont(new Font(Font.DIALOG_INPUT, Font.CENTER_BASELINE, 16));
		JScrollPane sp=new JScrollPane(this.display);
		sp.setBounds(displayX,displayY,displayWidth-6,displayHeigh);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setVisible(true);
		
		
		
		//creazione bottone start
		int buttonStartWidth=80;
		int buttonStartHeight=30;
		this.startButton=new JButton();
		this.startButton.setBounds((frameWidth/2)-buttonStartWidth/2,frameHeight-buttonStartHeight*5/2,buttonStartWidth,buttonStartHeight);
		this.startButton.setText("Start");
		this.startButton.setVisible(true);
		this.startButton.setEnabled(false);
		
		//creazione FileChooser
		int fileChooserWidth=100;
		int fileChooserHeight=50;
		this.fileChooser=new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".rp", "rp");
		this.fileChooser.setFileFilter(filter);
		this.fileChooser.setBounds(0, 0, fileChooserWidth, fileChooserHeight);
		this.fileChooser.setVisible(true);

		//creazione bottone addFile
		int buttonAddApkWidth=80;
		int buttonAddApkHeight=30;
		this.addFileButton=new JButton();
		this.addFileButton.setBounds(this.frame.getX(),this.frame.getY(),buttonAddApkWidth,buttonAddApkHeight);
		this.addFileButton.setText("add file");
		this.addFileButton.setVisible(true);
		
		//creazione textField file path
		int textFileWidth=frameWidth-buttonAddApkWidth-7;
		int textFileHeight=30;
		this.textFile=new JTextField("...");
		this.textFile.setHorizontalAlignment(JTextField.CENTER );
		this.textFile.setBounds(this.frame.getX()+buttonAddApkWidth+1, this.frame.getY(), textFileWidth, textFileHeight);
		this.textFile.setVisible(true);
		this.textFile.setEditable(false);
		this.textFile.setBackground(new Color(255,255,255));
		
		
//---------------------------------------------------------------------inserimento elementi nel frame principale		
		
		//aggiunta elementi al frame
		this.frame.add(this.addFileButton);
		this.frame.add(this.startButton);
		this.frame.add(this.textFile);
		this.frame.getContentPane().add(sp);
		this.frame.setVisible(true);
		
		
		
//--------------------------------------------------------------------------------------------------------eventi
		
		//evento pressione bottone addFile
		ActionListener listenerAddApkButton=new GestorePulsanteAddFile(this.fileChooser, this.frame, this.textFile, this.startButton);
		this.addFileButton.addActionListener(listenerAddApkButton);
		
		this.startButton.addActionListener(new GestorePulsanteStart(this.textFile, this.display));
		
		//evento di chiusura frame
		this.frame.addWindowListener(new java.awt.event.WindowAdapter() 
		{
				public void windowClosing(java.awt.event.WindowEvent e) 
				{ 
					System.exit(0);
				}
		});
	}
	
	public static void println(String line){
		display.append(line+"\n");
	};

}
