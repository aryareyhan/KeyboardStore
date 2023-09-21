package main;

import java.util.ArrayList;

public class TransactionContainer {
	private String loggedIn;
	private ArrayList<Transaction> transactions;

    private TransactionContainer() {
    	transactions = new ArrayList<>();
    }

    private static TransactionContainer instance;

    public static TransactionContainer getInstance() {
        if (instance == null) {
            instance = new TransactionContainer();
        }
        return instance;
    }

    public void addTransaction(String user) {
        Transaction transaction = new Transaction(user, new ArrayList<String>());
        transactions.add(transaction);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
    
    public void setLoggedIn(String user) {
    	this.loggedIn = user;
    }
    
    public String getLoggedIn() {
    	return loggedIn;
    }
    
}
