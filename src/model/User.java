package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class User {
	private String name;
	private Map<String, Account> accounts;
	private Collection<UserObserver> observers = null;
		
	public User( String name ){
		this.name = name;
		this.accounts = new HashMap<String, Account>();
	}	
	public String getName() {
		return this.name;
	}	
	public boolean hasAccount( String name){
		return this.accounts.containsKey(name);
	}
	public void addAccount( String name) throws AccountException {
		Account newAccount = AccountManager.getTheAccountManager().create(name);
		this.accounts.put( name, newAccount);
		this.notifyObservers( newAccount);
	}
	public Account getAccount( String name ){
		return this.accounts.get(name);
	}	
	public Collection<Account> getAccounts(){
		return this.accounts.values();
	}
	private Collection<UserObserver> getObservers(){
		if( this.observers == null ){
			this.observers = new LinkedList<UserObserver>();
		}
		return this.observers;
	}
	public void register(UserObserver observer) {
		this.getObservers().add(observer);
	}	
	
	public void deregister( UserObserver observer){
		this.getObservers().remove(observer);
	}
	
	private void notifyObservers(Account account){
		for( UserObserver current : getObservers()) {
			current.handleNewAccount(account);
		}
	}
}
