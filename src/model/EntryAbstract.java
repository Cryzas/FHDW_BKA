package model;

public abstract class EntryAbstract implements Entry {

	private Transfer transfer;

	protected EntryAbstract(Transfer transfer) {
		this.transfer = transfer;
	}

	public long getAmount() {
		return this.transfer.getAmount();
	}

	public String getSubject(){
		return this.transfer.getSubject();
	}
	
}
