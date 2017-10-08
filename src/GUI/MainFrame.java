package GUI;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import Controller.ScrapeRobot;

public class MainFrame extends JFrame{
	private JToolBar toolBar;
	private TextPanel textPanel;
	private ControlPanel controlPanel;
	private ScrapeRobot scrapeRobot;
	private JFileChooser fileChooser;
	
	private static int LIMIT;
	private static int start;
	private static int ends;
	private static boolean manual = false;
	private String source = null;
	private String timeStamp;
	private boolean restart;
	
	public MainFrame(String name){
		super(name);
		try {
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Images/Ninja-1.png"));
		} catch (Exception e) {
		}
		
		setLayout(new BorderLayout());
		
		timeStamp = new SimpleDateFormat("EEE, d MMM yyyy HHmmss").format(Calendar.getInstance().getTime());
		
		toolBar = new JToolBar();
		textPanel = new TextPanel();
		controlPanel = new ControlPanel();
		scrapeRobot = new ScrapeRobot(timeStamp);	
		fileChooser = new JFileChooser();
		restart = false;
		
		textPanel.appendText("Welcome..." + name);
		textPanel.appendText("Folder Name : " + timeStamp);
		
//		scrapeRobot.setSingoutListerner(new SingoutListerner(){
//			public String sighout(String string){
//				// add code here to set the value
//				String ans = null;
//				System.out.println("check");
//				textPanel.appendText("You are logged out. Please login.\n");
//				System.out.println("check");
//				while(!(ans == "yes")){
//					textPanel.appendText("Do you log in??? : \n");
//					ans = textPanel.textIn();
//				}
//				return ans;
//				
//			}
//		});
		
		controlPanel.setButtonListener(new ButtonListener(){
			public void ButtonActionlistener(String text) {
				if (text.contains("Start")) {
							if (!restart) {
								start = controlPanel.getstart();
								ends = controlPanel.getends();
								if (start>=1 && ends>=2 && start<ends && ends<=LIMIT) {
									scrapeRobot.setInitvalues(start, ends);
									textPanel.appendText("Starting point :" + start + "\nEnding point :" + ends + "\n" + text);
								} 
							}else {
								textPanel.appendText("Scrape Restarting ....");
							}
							controlPanel.setStartButton(false);
		            		controlPanel.setmanual(false);
		            		
		            		new Thread(new Runnable() {
		            			public void run() {
		            				runStart();
		            			}
		            		}).start();
		            		
//							else {
//								textPanel.appendText(" !!! Please specify valid scraping limits !!! ");
//							}
					
				} else if (text.contains("Pause")){
					scrapeRobot.setout();
				}else if (text.contains("Load")){
						scrapeRobot.loadURLs(source);
						textPanel.appendText("Loaing URLs...... ");
						LIMIT = scrapeRobot.countdatabase();
						textPanel.appendText("Total " + LIMIT +" URLs are loaded");
						controlPanel.setLimit(LIMIT);
						controlPanel.setStartButton(true);
						controlPanel.setLoadButton(false);
						controlPanel.setstart(1);
						controlPanel.setends(LIMIT);
						controlPanel.setmanual(true);
					
				}else if (text.contains("Browse")){
					if(fileChooser.showOpenDialog(MainFrame.this)== fileChooser.APPROVE_OPTION){
						source = (String)fileChooser.getSelectedFile().toString();
						textPanel.appendText("Source of URLs : ");
						textPanel.appendText(source);
						controlPanel.setLoadButton(true);
						controlPanel.setBrowseButton(false);
						
					}else {
						textPanel.appendText(" !!! No Source file selected. !!!");
						controlPanel.setLoadButton(false);
					}
				}
			}

			public void RadioActionlistener(String string) {
				if (string.equals("Manual")) {
					textPanel.appendText("Manual Mode selected");
					manual = true;
				} else if (string.equals("Auto")) {
					textPanel.appendText("Auto Mode selected");
					manual = false;
				}
			}
			
		});
				
//		textPanel.appendText(scrapeRobot.report());
		
		add(toolBar, BorderLayout.NORTH);
		add(textPanel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		
		setSize(500, 350);
		setLocation(350, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void runStart(){
			try {
			String check = scrapeRobot.start();
			if(check.equals("Out")){
				controlPanel.setStartButton(true);
				textPanel.appendText("!!! You are singed out !!!! -->> Please Singin again. \n And press --> START button to restart\n");
				restart = true;
			}else if(check.equals("Done")){
				textPanel.appendText("CV Collection Completed");
				scrapeRobot.report();
				textPanel.appendText("Thank you for using Ninja Warriors Tool");
				controlPanel.setStartButton(true);
				controlPanel.setBrowseButton(true);
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
