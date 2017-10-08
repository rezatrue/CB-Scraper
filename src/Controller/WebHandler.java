package Controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class WebHandler {
	public void WebHandler(){
	}
	
	public void UrlOpener(ArrayList<String> list){


//way of opening browser along with multiple URLs		
//		String[] args = new String[] { "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe", "your url" };
//		Runtime.getRuntime().exec( args );

		list.add(0, "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
		String[] links = null;
		links = list.toArray(new String[list.size()]);

		try {
			Runtime.getRuntime().exec(links);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void DataCollector(String filename){
		
		
		try {
				Robot robot = new Robot();
				// this delay is necessary as you have to wait until the page loaded properly
				robot.delay(300);
				
				robot.keyPress(KeyEvent.VK_TAB);
				robot.delay(100);
				robot.keyPress(KeyEvent.VK_TAB);
				robot.delay(100);
				robot.keyRelease(KeyEvent.VK_TAB);
				robot.delay(100);
				
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_A);
				robot.delay(300);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_C);
//				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.delay(300);
				
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_W);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.delay(300);
					
				
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//		systemClipboard.setContents(null, null);
		Transferable clipboardContents = systemClipboard.getContents(null);
		String returnText = null;		
		
		if (clipboardContents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			// return text content
				try {
					returnText = (String) clipboardContents.getTransferData(DataFlavor.stringFlavor);
//					System.out.println(returnText.toString());
//					System.out.println("<><><><><><><><><><><><><><><><><>");
				} catch (UnsupportedFlavorException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
		}
		
				File newfile = new File(filename);
				try {
					FileWriter fw = new FileWriter(newfile);
					BufferedWriter bw = new BufferedWriter(fw);
					
					String[] lines;
					try {
						lines = returnText.split("\n");
						for (int i = 0; i < lines.length; i++) {
							bw.write(lines[i] + "\r\n"); 
//							System.out.println("<><>"+ lines[i]  +"<><>");
						}
					} catch (Exception e) {
					}
					
					bw.close();
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				systemClipboard = null;
				
				// Clip board cleaner  
//				StringSelection stringSelection = new StringSelection("");
//				systemClipboard.setContents(stringSelection, null);
				
				}
					
	            	
				
				
}
		
				
				

