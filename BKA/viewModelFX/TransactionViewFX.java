package viewModelFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import viewModel.TransactionView;
import viewModel.TransferOrTransactionView;
import viewModel.TransferView;

public class TransactionViewFX extends TransactionView {

	private ObservableList<TransferOrTransactionView> transfers = null;

	public TransactionViewFX(int number) {
		super( number);
		this.transfers = FXCollections.observableArrayList();
	}

	public ObservableList<TransferOrTransactionView> getDetails() {
		return this.transfers;
	}

	public static TransactionViewFX create() {
		return new TransactionViewFX( nextTransactionNumber++);
	}

	@Override
	protected void addToTransferLst(TransferView newTransfer) {
		this.transfers.add( newTransfer );
	}

}
