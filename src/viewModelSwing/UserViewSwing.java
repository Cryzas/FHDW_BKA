package viewModelSwing;

import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import model.Account;
import model.AccountObserver;
import model.User;
import viewModel.AccountTransactionFacadeView;
import viewModel.EntryView;
import viewModel.UserView;

public class UserViewSwing extends UserView {
	public static UserViewSwing create(User user, AccountTransactionFacadeView view) {
		return new UserViewSwing( user, view );
	}

	private SpecialDefaultListModel<AccountViewSwing> myAccounts;
	private AccountViewSwing selectedAccount;
	private ListModel<EntryView> currentEntries;
	
	protected UserViewSwing( User user, AccountTransactionFacadeView view){
		super(user, view);
		initializeMyAccount();
	}
	
	private void initializeMyAccount() {
		this.myAccounts = new SpecialDefaultListModel<AccountViewSwing>();
		this.currentEntries = new DefaultListModel<EntryView>();
		for (Account current : this.user.getAccounts()) {
			this.myAccounts.addElement( AccountViewSwing.create(current, this));
		}
	}

	public SpecialDefaultListModel<AccountViewSwing> getAccounts() {
		return this.myAccounts;
	}
	
	@Override
	public void handleNewAccount( Account account) {
		this.getAccounts().addElement( AccountViewSwing.create( account, this));		
	}

	@Override
	public void handleAccountUpdate(AccountObserver accountView) {
		int index = 0;
		Enumeration<AccountViewSwing> myAccountsEnumeration = this.myAccounts.elements();
		while (myAccountsEnumeration.hasMoreElements()) {
			viewModel.AccountView current = myAccountsEnumeration.nextElement();
			if (current.equals(accountView)){
				this.myAccounts.fireEntryChanged(index);
				break;
			}
			index++;
		}
		if (this.selectedAccount != null && this.selectedAccount.equals(accountView)) 
			this.changeAccountSelection(this.selectedAccount);		
	}

	public void changeAccountSelection(AccountViewSwing account) {
		this.selectedAccount  = account;
		this.currentEntries = account.getAccountEntries();
		this.view.updateEntriesOfSelectedAccount();				
	}

	public ListModel<EntryView> getCurrentAccountEntries() {
		return this.currentEntries;
	}

	public AccountViewSwing getSelectedAccount() {
		return this.selectedAccount;
	}
	


}
