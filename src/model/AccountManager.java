package model;

import java.util.Map;
import java.util.TreeMap;

public class AccountManager {
	
	private static AccountManager theAccountManager = null;
	
	public static AccountManager getTheAccountManager(){
		if (theAccountManager == null) theAccountManager = new AccountManager();
		return theAccountManager;
	}
	private Map<String, Account> accounts;
	private AccountManager(){
		this.accounts = new TreeMap<String, Account>();
	}
	public Account find (String name) throws AccountNotFoundException{
		Account result = this.accounts.get(name);
		if (result == null) throw new AccountNotFoundException(name);
		return result;
	}
	public Account create (String name) throws AccountException {
		this.checkName(name);
		Account result = this.accounts.get(name);
		if (result != null) throw new AccountAlreadyExistsException(name);
		Account newAccount = Account.create(name);
		this.accounts.put(name, newAccount);
		return newAccount;
	}
	private void checkName(String name)  throws AccountException {
		if (name.length() < 3) throw new AccountNameFormatException(name);
	}
}
