package model;

public interface TransferOrTransaction {
	
	/** Removes the receiver's <amount> from the receiver's <fromAccount> (debit)
	 *  and adds the receiver's <amount> to the receiver's <toAccount> (credit).
	 * @throws AccountException if the booking violates account limits. 
	 */
	public void book() throws AccountException ;

}
