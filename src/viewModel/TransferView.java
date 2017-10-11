package viewModel;

import model.Transfer;
import model.TransferException;
import model.TransferOrTransaction;

public class TransferView implements TransferOrTransactionView {

	private static final String FromSeparator = "   FROM   ";
	private static final String ToSeparator = "   TO   ";
	static final String SubjectSeparator = "  SUBJECT: ";

	public static TransferView create(AccountView from, AccountView to, long amount, String purpose) throws TransferException {
		return new TransferView(from,to,amount,purpose);
	}

	final private Transfer transfer;
	final private AccountView fromAccountView;
	final private AccountView toAccountView;
	
	public TransferView(AccountView from, AccountView to, long amount, String purpose) throws TransferException {
		this.fromAccountView = from;
		this.toAccountView = to;
		this.transfer = Transfer.create(from.getAccount(), to.getAccount(), amount, purpose);
	}
	
	public String toString() {
		return this.transfer.getAmount() + FromSeparator +
				this.fromAccountView.getName() + ToSeparator +
				this.toAccountView.getName() + SubjectSeparator +
				this.transfer.getSubject();
	}

	@Override
	public void accept(TransferOrTransactionViewVisitor visitor) {
		visitor.handleTransferView(this);
	}

	public Transfer getTransfer() {
		return this.transfer;
	}

	@Override
	public TransferOrTransaction getTransferOrTransaction() {
		return this.transfer;
	}
}
