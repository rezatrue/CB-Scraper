package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import Model.DataBase;

public class FileManager {
	private static AddtoList addListing;
	
	public void FileManager(){
	}
	// store all URL to system memory
		public ArrayList<String> ListInfo(){
			ArrayList<String> urllist = new ArrayList<String>();
			
			File urlholder = new File("URLs.txt");
			FileReader fr = null;
			try {
				fr = new FileReader(urlholder);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedReader br = new BufferedReader(fr);
			String xy = null;
			do {
				try {
					xy = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("exception");
				}
				if (xy!=null) {
					urllist.add(xy);
				}
			} while (xy!=null);
			
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return urllist;
		}
		
// over load method for loading URL list
		public ArrayList<String> ListInfo(String s){
			ArrayList<String> urllist = new ArrayList<String>();
			File urlholder = new File(s);
			FileReader fr = null;
			try {
				fr = new FileReader(urlholder);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedReader br = new BufferedReader(fr);
			String xy = null;
			do {
				try {
					xy = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("exception");
				}
				if (xy!=null) {
					urllist.add(xy);
				}
			} while (xy!=null);
			
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return urllist;
		}
		
		
		
		
	// as URL has a unique id so that could help to locate which file comes form which URL 
		public String FileNamer(String xy){
			// then URL url was parameter
			//String xy = url.toString();
			String fileName = xy.substring((xy.indexOf("=")+1), xy.indexOf("&strcrit"));
//			String fileName = xy.substring(78, xy.indexOf("&strcrit"));
			return fileName;
		}
		
	// Creating Blank file & send back the file name to Robot to pest the data
		public String CreateFile(String name){
			String fileName;
			File file;
			int count = 0;
			
			// check out file is already exist 
			do {
				fileName = name + ".txt";
				file = new File(fileName);
				
				if (!file.exists()) {
					// Write a blank file
					try {
						BufferedWriter out = new BufferedWriter(new FileWriter(file));
						out.close();
						
					} catch (IOException e) {
						System.out.println("Unable to write file : " + file);
					}
				} else {
					System.out.println("File name already exist !!!!");
					count++;
					name =  name + "(count)";
				}
				
			} while (!file.exists());
			
			return fileName;	
		}
		// Open file 	
		public void OpenFile(String name){
				ProcessBuilder pb = new ProcessBuilder("Notepad.exe", name);
				try {
					pb.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
					
		// Close file
		public void CloseFile(String name){
					// it would be better to close Opened file with Robot class
			}
		
		// Check & Cut & Save file in different location 
		public boolean CVcutter(String fileName){
				// check whether need to reload the page
				// check whether Invalid
				// cut out unnecessary data
			String linedata = null;
			
			try {
				
				File file = new File(fileName);
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				
//				For test.java
//				File newfile = new File(("works//" + fileName));
				File newfile = new File(fileName.replace("//Temp//", "//All CVs//"));
				FileWriter fw = new FileWriter(newfile);
				BufferedWriter bw = new BufferedWriter(fw);
				
				boolean state = true;
				linedata = br.readLine();
				while ((linedata != null) && state ) {
					
					if (linedata.toLowerCase().contains("sign in")) {
//						System.out.println("You are Signed out.. Please sign in & the the program");
						bw.close();
						fr.close();
						newfile.delete();
						state = false;
						// better to use return -1 to locate sing out status
						return true;
//						break;
					} else if (linedata.startsWith("Name:")) {
						boolean eof = false;
						do {	
							bw.write(linedata + "\r\n"); 
							linedata = br.readLine();
								
							if (linedata.contains("Recent Activity")) {
								eof = true;
								// for identifying EOF
							} else if (!br.ready()) {
								eof = true;
							}	
						} while (!eof);
						bw.close();
						fr.close();
						state = false;
					} else if(linedata.startsWith("Please wait while we load your resume")){
						addListing.addListtoDataBase("Invalid", fileName);
						bw.close();
						fr.close();
						newfile.delete();
						state = false;
					}
					
					linedata = br.readLine();
				}
				
				if((linedata == null) || state ) {
					// you should check whether it is logged out or not before adding to list
//					try {
						addListing.addListtoDataBase("Reload", fileName);
						bw.close();
						fr.close();
						newfile.delete();
						System.out.println("null");
//					} catch (Exception e) {
//						System.out.println("there is an exception going on");
//					}
					
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return false;	
			}
		public void CreateDir(String folderName) {
			// take the system date
			// make a file name with that date & check the exist & add 1 at the end if need
			// Directory name will be "28 August work(0)"
			
			File dir = new File(folderName);
			
			if (!dir.exists()) {
				dir.mkdir();
			} else {
				System.out.println("Folder 1 Sept CV is alresy exsist" );
			}
			
			File dir1 = new File(folderName +"//Temp");
			if (!dir1.exists()) {
				dir1.mkdirs();
			} else {
				System.out.println("Folder Temp is alresy exsist" );
			}
			File dir2 = new File(folderName +"//All CVs");
			if (!dir2.exists()) {
				dir2.mkdir();
			} else {
				System.out.println("Folder All CVs is alresy exsist" );
			}
			
		}

		public void setIRlistenner(AddtoList addtoList) {
			// TODO Auto-generated method stub
			this.addListing = addtoList;
			
		}
}
