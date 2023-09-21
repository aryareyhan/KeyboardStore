package main;

import java.util.ArrayList;

public class Transaction {
	String user;
	ArrayList<String> keyboards;
	
	public Transaction(String user, ArrayList<String> keyboards) {
        this.user = user;
        this.keyboards = keyboards;
    }
	
	public ArrayList<String> getList(){
		return this.keyboards;
	}
	
	public void addKeyboard(String keyboard) {
		this.keyboards.add(keyboard);
	}
	public String getUser(){
		return user;
	}
	public void clear() {
		this.keyboards.clear();
	}
}
