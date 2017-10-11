package viewModel;

import model.Transaction;
import model.TransferException;
import model.TransferOrTransaction;

public abstract class TransactionView implements TransferOrTransactionView{

	private static final String TransactionPrefix = "TRANSACTION ";
	protected static int nextTransactionNumber = 1;
	protected int number;
	protected Transaction transaction;

	protected TransactionView(int number){
		this.number = number;
		this.transaction = Transaction.create();		
	}
	public String toString() {
		return TransactionPrefix + this.number;
	}

	public TransferOrTransaction getTransferOrTransaction() {
		return this.transaction;
	}

	public void addTransfer( AccountView from, AccountView to, long amount, String purpose) throws TransferException {
		TransferView newTransfer = TransferView.create( from, to, amount, purpose);
		this.transaction.addTransfer( newTransfer.getTransfer() );
		this.addToTransferLst( newTransfer);
	}

	abstract protected void addToTransferLst(TransferView newTransfer);

	public void accept(TransferOrTransactionViewVisitor visitor) {
		visitor.handleTransactionView(this);
	}

}
