package viewModelSwing;

import model.Transaction;
import viewModel.TransactionView;
import viewModel.TransferOrTransactionView;
import viewModel.TransferView;

public class TransactionViewSwing extends TransactionView  {

	public static TransactionViewSwing create() {
		return new TransactionViewSwing(nextTransactionNumber++);
	}
	private SpecialDefaultListModel<TransferOrTransactionView> transfers;
	
	public TransactionViewSwing(int number) {
		super( number );
		this.transfers = new SpecialDefaultListModel<TransferOrTransactionView>();
	}
	Transaction getTransaction() {
		return transaction;
	}
	public SpecialDefaultListModel<TransferOrTransactionView> getDetails(){
		return this.transfers;
	}
	@Override
	protected void addToTransferLst( TransferView newTransfer) {
		this.transfers.addElement( newTransfer);
	}
}
