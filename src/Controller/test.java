package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Model.DataBase;

// This for running the program from console 
// need a folder named 'works' & 'URLs.text' for links upload

public class test {
	// Don't take more than 100 links a time
	private final static int number = 100; 
	// Idea time 60000 millisecond for 100 urls
	private final static int sleeptime = 60000;
	
	private static DataBase db = new DataBase();
	private static FileManager test = new FileManager();
	private static WebHandler web = new WebHandler();
	private static ArrayList<String> shortlist;
	
public static void main(String[] args) throws IOException {
	
//	test.CreateDir();
	boolean reloadlocator = false;
	boolean out = false;
	int startpoint = 0;
	int endpoint = 0;
	
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
	
//	test.CVcutter("works (2).txt");
	
	db.setURLlist(test.ListInfo());
	int size;
	
	// loop for dividing URLs in groups
	
	
	do {
		
		shortlist = new ArrayList<String>();
		startpoint = endpoint;
		endpoint = startpoint + number;
		
		if (!reloadlocator) {
			size = db.listsize();
		} else {
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
			String filename = test.CreateFile(name);
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
			while (out) {
				System.out.println("you are logged out... Please take the necessary step");
				System.out.println(endpoint);
				while (out !=false) {
					System.out.println("Do you logged in?? ");
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					if (br.readLine()!="yes") {
						out = false;
					}
					
				}
				
				
				
				System.out.println(out);
			}		
		
	} while (endpoint!=(size) || reloadlocator);
	
	
	// Reporting  
		System.out.println("Total links : " + db.getURLlist().size());
		
		if (db.getInvalidlist().size()> 0) {
			System.out.println("Total Invalid links : " + db.getInvalidlist().size());
			System.out.println("Invalid List : ");
			for (int i = 0; i < db.getInvalidlist().size(); i++) {
				System.out.println(db.getInvalidlist().get(i));
			}
		} else {
			System.out.println("No Invalid URLs is Listed.");
		}
		
		if (db.getReloadlist().size()>0) {
			System.out.println("Reload List : ");
			for (int i = 0; i < db.getReloadlist().size(); i++) {
				System.out.println(db.getReloadlist().get(i));
			}
		} else {
			System.out.println("No Reload URLs is Listed.");
		}
		
		
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


	
}

