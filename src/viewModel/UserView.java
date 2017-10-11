package viewModel;

import model.Account;
import model.AccountException;
import model.User;
import model.UserObserver;

abstract public class UserView implements UserObserver, AccountViewManager{

	protected User user = null;
	protected AccountTransactionFacadeView view;

	protected UserView( User user, AccountTransactionFacadeView view) {
		this.user = user;
		this.view = view;
		this.user.register( this );
	}

	public String toString(){
		return this.user.getName();
	}
	
	public void finalize(){
		this.user.deregister(this);
	}

	public void addAccount(String name) throws AccountException {
		this.user.addAccount(name);
	}

	@Override
	abstract public void handleNewAccount(Account account);
}
