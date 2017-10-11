package viewModelFX;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Account;
import model.AccountObserver;
import model.User;
import viewModel.AccountTransactionFacadeView;
import viewModel.EntryView;
import viewModel.UserView;

public class UserViewFX extends UserView {
	public static UserViewFX create(User user, AccountTransactionFacadeView view) {
		return new UserViewFX( user, view);
	}

	private ObservableList<AccountViewFX> myAccounts = null;
	private AccountViewFX selectedAccount;
	private ObservableList<EntryView> currentEntries;
	
	protected UserViewFX( User user, AccountTransactionFacadeView view ){
		super(user, view);
		initializeMyAccount();
	}
	
	private void initializeMyAccount() {
		this.myAccounts = FXCollections.observableArrayList();
		for (Account current : this.user.getAccounts()) {
			this.myAccounts.add( AccountViewFX.create(current, this));
		}
	}

	public ObservableList<AccountViewFX> getAccounts() {
		return this.myAccounts;
	}
	
	@Override
	public void handleNewAccount( Account account) {
		this.getAccounts().add( AccountViewFX.create( account, this));		
	}

	@Override
	public void handleAccountUpdate(AccountObserver accountView) {
		Platform.runLater(new Runnable() {				
			@Override
			public void run() {
				if( UserViewFX.this.getAccounts().contains( accountView )){
					int index = UserViewFX.this.getAccounts().indexOf( accountView );
					UserViewFX.this.getAccounts().set( index, (AccountViewFX) accountView);
				}		
			}
		});
	}

	public void changeAccountSelection(AccountViewFX account) {
		this.selectedAccount  = account;
		this.currentEntries = account.getLastAccountEntries();
		this.view.updateEntriesOfSelectedAccount();				
	}

	public ObservableList<EntryView> getCurrentAccountEntries() {
		return this.currentEntries;
	}

	public AccountViewFX getSelectedAccount() {
		return this.selectedAccount;
	}
	

}
