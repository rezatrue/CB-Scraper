package GUI;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class TextPanel extends JPanel {
	private JTextArea textArea;
	
	public TextPanel(){
		
		textArea = new JTextArea();
//		textArea.setTransferHandler(null);
//		textArea.getCaret().setVisible(false);
		setLayout(new BorderLayout());
		add(new JScrollPane(textArea), BorderLayout.CENTER);
	}
	
	public void appendText(String text){
		textArea.append(text + "\n");
	}
	
	public String textIn(){
		return textArea.getText();
	}
	
}

