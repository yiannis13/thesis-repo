package gr.ihu.identity;

import gr.ihu.identity.model.TransferResult;
import gr.ihu.identity.db.model.Account;
import gr.ihu.identity.model.ModelAccount;
import gr.ihu.identity.model.PaymentOrder;
import javax.ejb.Local;

/**
 *
 * @author John
 */

@Local
public interface IBankService {

    /**
     * Make a non block transfer.
     *
     * @param payment
     * @param requireConfirm
     * @return
     */
    
    TransferResult transfer(PaymentOrder payment, boolean requireConfirm);

    TransferResult transfer(PaymentOrder payment);

    void confirmTransfer(int transferId);
    
    Account check(String username);

    int createAccount(ModelAccount account);

    void deposit(ModelAccount account);

    void withdraw(ModelAccount account);
}


//void deposit(String username, long amount);
