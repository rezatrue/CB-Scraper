package Model;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
	private ArrayList<String> URLlist = new ArrayList<String>();
	private ArrayList<String> Invalidlist = new ArrayList<String>();
	private ArrayList<String> Reloadlist = new ArrayList<String>();
	
	public void DataBase(){
	}

	public ArrayList<String> getURLlist(int start, int end) {
		ArrayList<String> returnURLlist = new ArrayList<String>();
		List l = URLlist.subList(start, end);
		returnURLlist.addAll(l);
		return returnURLlist;
	}
	
	public ArrayList<String> getURLlist() {
		return URLlist;
	}

	public void setURLlist(ArrayList<String> uRLlist) {
		URLlist = uRLlist;
	}
	

	public int listsize() {
		return URLlist.size();
	}
	
	public ArrayList<String> getInvalidlist() {
		return Invalidlist;
	}

	public void addtoInvalidList(String invalidlink) {
		Invalidlist.add(invalidlink);
	}

	public ArrayList<String> getReloadlist() {
		return Reloadlist;
	}

	public ArrayList<String> getReloadlist(int start, int end) {
		ArrayList<String> returnURLlist = new ArrayList<String>();
		List l = Reloadlist.subList(start, end);
		returnURLlist.addAll(l);
		return returnURLlist;
	}
	
	public void addtoReloadlist(String reloadlink) {
		Reloadlist.add(reloadlink);
	}
	
}
