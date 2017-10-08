package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class ControlPanel extends JPanel implements ActionListener{
	private JTextField starts;
	private JTextField ends;
	private JTextField limitcounts;
	private JButton startButton;
	private JButton loadButton;
	private JButton browseButton;
	private JRadioButton autoRadio;
	private JRadioButton manualRadio;
	private ButtonGroup automanual;
	
	private ButtonListener buttonListener;
	
	public ControlPanel(){
		starts = new JTextField(5);
		ends = new JTextField(5);
		limitcounts = new JTextField(7);
		startButton = new JButton("Start");
		loadButton = new JButton("Load");
		browseButton = new JButton("Browse");
		autoRadio = new JRadioButton("Auto");
		manualRadio = new JRadioButton("Manual");
		automanual = new  ButtonGroup();
		
		starts.setEditable(false);
		ends.setEditable(false);
		limitcounts.setEditable(false);
		manualRadio.setEnabled(false);
		
		
		// for blocking copy past
//		starts.setTransferHandler(null);
//		ends.setTransferHandler(null);
//		limitcounts.setTransferHandler(null);
		
		autoRadio.setSelected(true);
		automanual.add(autoRadio);
		automanual.add(manualRadio);
		
		startButton.setText("Start");
		startButton.setEnabled(false);
		loadButton.setEnabled(false);
		
		autoRadio.setActionCommand("Auto");
		manualRadio.setActionCommand("Manual");
		
		startButton.addActionListener(this);
		loadButton.addActionListener(this);
		browseButton.addActionListener(this);
		autoRadio.addActionListener(this);
		manualRadio.addActionListener(this);
		
		
		starts.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent arg0) {
			}
			public void focusLost(FocusEvent arg0) {
				try {
					int xy = Integer.parseInt(starts.getText());
					if (xy<1 || xy>(Integer.parseInt(ends.getText())-1) ) {
						starts.setText(1+"");
					}
				} catch (NumberFormatException e) {
					starts.setText(1+"");
				}
			}
			
		});
		
		ends.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent arg0) {
			}
			public void focusLost(FocusEvent arg0) {
				try {
					int xy = Integer.parseInt(ends.getText());
					if (xy>Integer.parseInt(limitcounts.getText()) || xy<Integer.parseInt(starts.getText())) {
						ends.setText(limitcounts.getText());
					}
				} catch (NumberFormatException e) {
					ends.setText(limitcounts.getText());
				}
			}
			
		});
		
		ComponentLayout();
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if ((boolean)((AbstractButton) e.getSource()).getText().contains("Start")) {
			buttonListener.ButtonActionlistener("Program is Starting..... \n Please STOP using Mouse & Keybord");
		}else if ((boolean)((AbstractButton) e.getSource()).getText().contains("Load")) {
			buttonListener.ButtonActionlistener("Loading URLs");
		}else if ((boolean)((AbstractButton) e.getSource()).getText().contains("Browse")) {
			buttonListener.ButtonActionlistener("Browse");
		}else if ((boolean)automanual.getSelection().getActionCommand().contains("Auto")) {
			buttonListener.RadioActionlistener("Auto");
			starts.setEditable(false);
			ends.setEditable(false);
		}else if ((boolean)automanual.getSelection().getActionCommand().contains("Manual")) {
			buttonListener.RadioActionlistener("Manual");
			starts.setEditable(true);
			ends.setEditable(true);
		}
	
	}
	
	
	
	public void setStartButton(boolean b) {
		startButton.setEnabled(b);
	}

	public void setLoadButton(boolean b) {
		loadButton.setEnabled(b);
	}

	public void setBrowseButton(boolean b) {
		browseButton.setEnabled(b);
	}

	public void setLimit(int limit){
		limitcounts.setText(limit + "");
	}
	
	public void setButtonListener(ButtonListener listener){
		this.buttonListener = listener;
	}
	
	public int getstart(){
		return Integer.parseInt(starts.getText());
	}
	
	public void setstart(int s){
		starts.setText(s +"");
	}
	
	public int getends(){
		return Integer.parseInt(ends.getText());
	}
	
	public void setends(int e){
		ends.setText(e +"");
	}
	
	public void setmanual(boolean b){
		manualRadio.setEnabled(b);
	}
	
	public void ComponentLayout(){
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(5, 5, 0, 0);
		add(new JLabel("Collecting Data from:"), gbc);
		gbc.insets = new Insets(5, 0, 0, 0);
		gbc.gridx++;
		add(starts, gbc);
		gbc.gridx++;
		add(new JLabel("to"), gbc);
		gbc.gridx++;
		add(ends, gbc);
		gbc.gridx++;
		add(new JLabel("Remaining Limit:"), gbc);
		gbc.gridx++;
		gbc.anchor = GridBagConstraints.CENTER;
		add(limitcounts, gbc);
		
		
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.insets = new Insets(5, 5, 5, 0);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 2;
		add(new JLabel("      Mode : "), gbc);
		gbc.anchor = GridBagConstraints.CENTER;
		add(autoRadio, gbc);
		gbc.anchor = GridBagConstraints.EAST;
		add(manualRadio, gbc);
		
		gbc.gridx = 3;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 0, 5, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		add(browseButton, gbc);
		gbc.gridx++;
		gbc.gridwidth = 1;
		add(loadButton, gbc);
		gbc.gridx++;
		add(startButton, gbc);
		
	}

}
