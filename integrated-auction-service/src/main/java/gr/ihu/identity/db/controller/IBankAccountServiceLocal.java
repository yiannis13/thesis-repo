package gr.ihu.identity.db.controller;

import gr.ihu.db.controller.IDbService;
import gr.ihu.identity.db.model.Account;
import gr.ihu.identity.db.model.TransferTransaction;
import javax.ejb.Local;

/**
 *
 * @author John
 */
@Local
public interface IBankAccountServiceLocal extends IDbService<Account> {

    Account findAccountByUsername(String username);
    
    TransferTransaction addTransaction(TransferTransaction transaction);
    
    TransferTransaction findTransaction(Object id);
    
    void editTransaction(TransferTransaction transaction);
}
