package graphic_interface;

import javax.swing.JTextArea;

public class Display {

		private static JTextArea display;
		
		public static void setDisplay(JTextArea dis){
			display=dis;
		}
		
		public static void println(String line){
			display.append(line+"\n");
		};
}
