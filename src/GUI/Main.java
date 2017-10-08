package GUI;

import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
	private static final String NAME = "Ninja-6";
	public static void main(String[] args){
		// TODO Auto-generated method stub
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new MainFrame(NAME);
			}
		});
	}

}
