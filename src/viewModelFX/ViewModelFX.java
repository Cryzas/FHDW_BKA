package viewModelFX;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Account;
import model.AccountException;
import model.AccountManager;
import model.AccountObserver;
import model.TransferException;
import viewExceptions.AccountAlreadyShownException;
import viewModel.AccountView;
import viewModel.AccountViewManager;
import viewModel.EntryView;
import viewModel.TransferOrTransactionView;
import viewModel.TransferView;

public class ViewModelFX implements AccountViewManager{
	public static ViewModelFX create( UserViewFX userView) {
		return new ViewModelFX( userView );
	}
	private ObservableList<AccountViewFX> otherAccounts = FXCollections.observableArrayList();
	private ObservableList<TransferOrTransactionView> pendingTransfersAndOrTransactions = FXCollections.observableArrayList();
	private ObservableList<TransferOrTransactionView> currentTransactionDetails = FXCollections.observableArrayList();

	private UserViewFX userView = null;
	private TransactionViewFX selectedTransaction = null;

	public ViewModelFX( UserViewFX userView ){
		this.userView  = userView;
	}
	
	public String toString(){
		return this.userView.toString();
	}
	
	public ObservableList<AccountViewFX> getMyAccounts() {
		return this.userView.getAccounts();
	}

	public void createAccount(String name) throws AccountException {
		this.userView.addAccount(name);
	}
	
	public ObservableList<AccountViewFX> getOtherAccounts() {
		return otherAccounts;
	}

	public void handleAccountUpdate(AccountObserver accountView) {
		Platform.runLater( new Runnable() {				
			@Override
			public void run() {
				if( ViewModelFX.this.otherAccounts.contains(accountView)){
					int index = ViewModelFX.this.otherAccounts.indexOf(accountView);
					ViewModelFX.this.otherAccounts.set( index, (AccountViewFX) accountView);
				}
			}
		});
	}

	public void changeAccountSelection( AccountViewFX account) {
		this.userView.changeAccountSelection( account );
	}
	public void findAccount(String name) throws AccountException {
		Account foundAccount = AccountManager.getTheAccountManager().find(name);
		if (this.containsInOtherAccounts(foundAccount)) throw new AccountAlreadyShownException(name);
		this.otherAccounts.add( AccountViewFX.create(foundAccount, this));		
	}
	
	private boolean containsInOtherAccounts(Account account) {
		for (AccountViewFX current : this.otherAccounts ) {
			if( current.isFor( account )) return true;
		}
		return false;
	}

	public void clearOtherAccounts() {
		for (AccountViewFX current : this.otherAccounts) {
			current.release();
		}
		this.otherAccounts.clear();
	}

	public ObservableList<EntryView> getCurrentAccountEntries() {
		return this.userView.getCurrentAccountEntries();
	}

	public ObservableList<TransferOrTransactionView> getPendingTransfersAndOrTransactions() {
		return this.pendingTransfersAndOrTransactions;
	}

	public void changeTransactionSelection(TransactionViewFX selectedTransaction) {
		this.selectedTransaction  = selectedTransaction;
		this.currentTransactionDetails = this.selectedTransaction.getDetails();
	}

	public void createTransaction() {
		TransactionViewFX newTransactionView = TransactionViewFX.create();
		this.getPendingTransfersAndOrTransactions().add(newTransactionView);
	}

	public void createTransfer(AccountView from, AccountView to, long amount, String purpose) throws TransferException {
		TransferView newTransaction = TransferView.create(from,to,amount,purpose);
		this.getPendingTransfersAndOrTransactions().add(newTransaction);		
	}

	public void createTransferInTransaction( AccountView from, AccountView to, long amount, String purpose
										   , TransactionViewFX transaction) throws TransferException {
		transaction.addTransfer(from,to,amount, purpose);		
	}

	public ObservableList<TransferOrTransactionView> getCurrentTransactionDetails() {
		return this.currentTransactionDetails;
	}

	public void book(TransferOrTransactionView transferOrTransaction) throws AccountException {
		transferOrTransaction.getTransferOrTransaction().book();
		this.getPendingTransfersAndOrTransactions().remove(transferOrTransaction);
	}

	
}
