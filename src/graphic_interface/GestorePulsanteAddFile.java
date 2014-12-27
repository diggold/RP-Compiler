package graphic_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

//questa classe gestisce l'evento di click sul pulsante "addApkButton"
//della classe InterfaceEngine
public class GestorePulsanteAddFile implements ActionListener{
	
	private JFileChooser j;	// sellezionatore dei file
	private JFrame f;		// frame principale utilizzato per l'apertura del filechooser
	private JTextField t;   // area di testo relativa al path dell'apk
	private JButton b;		// pulsante start
	
	//costruttore
	public GestorePulsanteAddFile(JFileChooser j, JFrame f, JTextField t, JButton b){
		this.j=j;
		this.f=f;
		this.t=t;
		this.b=b;
	}
	
	//1.quando il pulsante addFile viene premuto si visualizza il filechooser
	//2.l'area di associata al pulsante viene settata con il path dell'apk
	//3.se il file selezionato termina con .apk viene sbloccato il pulsante start
	public void actionPerformed(ActionEvent e) {
		
		this.j.showOpenDialog(this.f);
		String path=this.j.getSelectedFile().getPath();
		
		try{
			this.t.setText(path);
			this.b.setEnabled(true);
		} catch (java.lang.NullPointerException ex) {this.t.setText("...");}
		
	}
}
