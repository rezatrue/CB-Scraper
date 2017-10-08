package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import GUI.SingoutListerner;
import Model.DataBase;


public class ScrapeRobot {
	// Don't take more than 100 links a time
	private final static int number = 10; 
	// Idea time 60000 millisecond for 100 urls
	private final static int sleeptime = 10000;
	
	private static DataBase db = new DataBase();
	private static FileManager test = new FileManager();
	private static WebHandler web = new WebHandler();
	private static ArrayList<String> shortlist;
	
	private String folder = null;
	private boolean reloadlocator = false;
	private boolean out = false;
	private int startpoint = 0; // need to update from gui(start)
	private int endpoint = 0; // don't set from out side only used for logical purpose 
	private int size; // need to update from gui (ends)
//	private SingoutListerner singoutListerner;
	
public ScrapeRobot(String folderName){
	
	test.setIRlistenner(new AddtoList(){

		@Override
		public void addListtoDataBase(String type, String link) {
			if (type.equals("Invalid")) {
				db.addtoInvalidList(link);
			} else if (type.equals("Reload")){
				// search from the short list & add to database
				String url = searchURL(link);
				db.addtoReloadlist(url);
			}
		}
	});
	
	test.CreateDir(folderName);
	folder = folderName;
	}

	public void loadURLs(String s){
		db.setURLlist(test.ListInfo(s));
	}
	
	public int countdatabase(){
		return db.getURLlist().size();
	}

	// using this as there is lest.java for backup that cann't set this from gui
	public void setInitvalues(int start, int ends){
		// setting endpoint as it transfer data to startpoint in the loop
		endpoint = start -1;
		size = ends;
	}

	public void setout(){
		if (out==true) {
			out = false;
		} else if (out==false) {
			out = true;
		}
		
	}
	// return the end point 
	public String start() throws IOException {
		do {
			// this is for dividing URLs in small group in runtime 
				shortlist = new ArrayList<String>();
				startpoint = endpoint;
				endpoint = startpoint + number;

				if (reloadlocator) {
					size = db.getReloadlist().size();
				} 
				
				if (endpoint>size) endpoint = size;
				
				if (!reloadlocator) {
					shortlist = db.getURLlist(startpoint, endpoint);
				} else {
					shortlist = db.getReloadlist(startpoint, endpoint);
				}
				web.UrlOpener(shortlist);
				
				// Need some time to load all pages in the browser
				try {
					Thread.sleep(sleeptime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// Copy data serially form last to start of short list data
				for (int i = 1; i < (shortlist.size()); i++) {
					String name = test.FileNamer(shortlist.get(i));
					String filename = test.CreateFile(folder +"//Temp//"+ name);
					web.DataCollector(filename);
					// need to identify the file number where to start next time after sing out
					out = test.CVcutter(filename);
					if (out) {
						// as you need to Start form the
						endpoint = startpoint + i -1; 
						break;	
					}
				}
				
				if (!db.getReloadlist().isEmpty() && (!out) ) {
					if (endpoint==(size) && !reloadlocator) {
						reloadlocator = true;
						endpoint = 0;
					} else if(endpoint==(size) && reloadlocator) {
						reloadlocator = false;
					}
				}
		
				// you should take confirmation form here if page is logged out 
					if (out) {
						return "Out";
					}
				
			} while (endpoint!=(size) || reloadlocator);
			
		return "Done";
	
	}

	public String report(){
		// Reporting  
		StringBuilder report = new StringBuilder();
		report.append("<><><><><> REPORT for  "+ folder + " <><><><><>\n");
		report.append("Total links : " + db.getURLlist().size() + "\n");
				
		if (db.getInvalidlist().size()> 0) {
			report.append("Total Invalid links : " + db.getInvalidlist().size() + "\n");
			report.append("Invalid List : " + "\n");
			for (int i = 0; i < db.getInvalidlist().size(); i++) {
				report.append(db.getInvalidlist().get(i) + "\n");
			}
		} else {
			report.append("No Invalid URLs is Listed." + "\n");
		}
		
		if (db.getReloadlist().size()>0) {
			
			for (int i = 0; i < db.getReloadlist().size(); i++) {
				String filename = folder +"//All CVs//"+ db.getReloadlist().indexOf(i);
				File file = new File(filename);
				if (file.exists()) {
					db.getReloadlist().remove(i);
				}
			}
			
			report.append("Reload List : " + "\n");
			for (int i = 0; i < db.getReloadlist().size(); i++) {
				report.append(db.getReloadlist().get(i) + "\n");
			}
		} else {
			report.append("No Reload URLs is Listed." + "\n");
		}
		
		return report.toString();
	}

	public static String searchURL(String id){
		String url = null;
		int x = id.indexOf(".txt");
		String xy = id.substring(0, x);
		for (int i = 0; i < shortlist.size(); i++) {
			if (shortlist.get(i).contains(xy)) {
				url = shortlist.get(i);
				break;
			} 
		}
		return url;
	}

//	public void setSingoutListerner(SingoutListerner singoutListerner) {
//		this.singoutListerner = singoutListerner;
//		
//	}


	
}

