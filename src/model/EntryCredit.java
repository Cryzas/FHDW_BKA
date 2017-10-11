package model;

public class EntryCredit extends EntryAbstract {

	private EntryCredit( Transfer transfer) {
		super( transfer );
	}

	@Override
	public <T> T acceptEntryVisitor(EntryVisitor<T> visitor) {
		return visitor.handleEntryCredit( this );
	}

	public static Entry create(Transfer transfer) {
		return new EntryCredit( transfer);
	}

}
