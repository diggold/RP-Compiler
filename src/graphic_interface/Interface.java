package graphic_interface;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

import java.awt.Color;


public class Interface{
	
	private JFrame frame;
	private JButton startButton;
	private JButton addFileButton;
	private JFileChooser fileChooser;
	private JTextField textFile;
	private static JTextArea display;
	
	public Interface(){
		
		
		
		//---------------------------------------------------------------------------elementi di interfaccia
		//creazione frame
		int frameWidth=1000;
		int frameHeight=700;
		this.frame=new JFrame("finestra");
		this.frame.setLayout(null);
		this.frame.setBounds(0,0,frameWidth,frameHeight);
		this.frame.setResizable(true);
		JPanel framePanel=new JPanel();
		frame.setContentPane(framePanel);
		
		
		//display
		display=new JTextArea();
		display.setVisible(true);
		display.setBackground(new Color(0,0,0));
		display.setForeground(new Color(255,255,255));
		display.setFont(new Font(Font.DIALOG_INPUT, Font.CENTER_BASELINE, 16));
		JScrollPane displayScrollPanel=new JScrollPane(display);
		displayScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		displayScrollPanel.setVisible(true);
		
		//creazione bottone start
		this.startButton=new JButton();
		this.startButton.setText("Start");
		this.startButton.setVisible(true);
		this.startButton.setEnabled(false);
		
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
		GridBagLayout gbl=new GridBagLayout();
		framePanel.setLayout(gbl);
		//framePanel.setBackground(Color.RED);
		framePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		GridBagConstraints c=new GridBagConstraints();
		
		//---------------------------------------------------------------------inserimento elementi nel frame principale		
		
		//aggiunta elementi al frame
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.weighty=0.05;
		//c.weightx=0.05;
		c.gridx=0;
		c.gridy=0;
		framePanel.add(addFileButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.weighty=0.05;
		//c.weightx=1;
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
		framePanel.add(startButton, c);
		
		//frame.pack();
		frame.setVisible(true);
		
		
		//--------------------------------------------------------------------------------------------------------eventi
		
		//evento pressione bottone addFile
		ActionListener listenerAddApkButton=new GestorePulsanteAddFile(this.fileChooser, this.frame, this.textFile, this.startButton);
		this.addFileButton.addActionListener(listenerAddApkButton);
		
		this.startButton.addActionListener(new GestorePulsanteStart(this.textFile, display));
		
		//evento di chiusura frame
		this.frame.addWindowListener(new java.awt.event.WindowAdapter() 
		{
				public void windowClosing(java.awt.event.WindowEvent e) 
				{ 
					System.exit(0);
				}
		});
	}
	
	//metodo esportato per scrivere sul display del frame
	public static void println(String line){
		display.append(line+"\n");
	};

}
