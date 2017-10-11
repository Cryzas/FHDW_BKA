package model;

/** An entry (debit or credit) for an account, typically result of a booking process. */
public interface Entry {

	abstract public <T> T acceptEntryVisitor(EntryVisitor<T> visitor);	
	public long getAmount();
	public abstract String getSubject();
	
}
