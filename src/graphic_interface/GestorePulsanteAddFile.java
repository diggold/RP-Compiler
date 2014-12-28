package graphic_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class GestorePulsanteAddFile implements ActionListener{
	
	private JFileChooser j;	
	private JFrame f;		
	private JTextField t;   
	private JButton b;
	
	//costruttore
	public GestorePulsanteAddFile(JFileChooser j, JFrame f, JTextField t, JButton b){
		this.j=j;
		this.f=f;
		this.t=t;
		this.b=b;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		this.j.showOpenDialog(this.f);
		String path=this.j.getSelectedFile().getPath();
		
		try{
			this.t.setText(path);
			this.b.setEnabled(true);
		} catch (java.lang.NullPointerException ex) {this.t.setText("...");}
		
	}
}
