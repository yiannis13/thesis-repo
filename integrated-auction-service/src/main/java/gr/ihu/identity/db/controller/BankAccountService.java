package gr.ihu.identity.db.controller;

import gr.ihu.db.controller.GenericRepository;
import gr.ihu.db.controller.AbstractFacade;
import gr.ihu.identity.db.model.Account;
import gr.ihu.identity.db.model.TransferTransaction;
import gr.ihu.utils.checks.ArgsCheck;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author John
 */
@Stateless
public class BankAccountService extends AbstractFacade<Account> implements IBankAccountServiceLocal {

    @PersistenceContext(unitName = "auction_pu")
    private EntityManager em;

    public BankAccountService() {
        super(Account.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
    @Override
    public Account findAccountByUsername(String username) {
        ArgsCheck.ensureNotWhite(username, "username");
        return InternalUtilities.getFirstOrDefault(em, Account.class, "Account.findByUsername", "username", username);
    }

    @Override
    public TransferTransaction addTransaction(TransferTransaction transaction) {
        ArgsCheck.ensureNotNull(transaction, "transaction");
        GenericRepository<TransferTransaction> repo = new GenericRepository<>(em, TransferTransaction.class);
        repo.create(transaction);
        repo.count();
        return transaction;
    }

    @Override
    public TransferTransaction findTransaction(Object id) {
        ArgsCheck.ensureNotNull(id, "id");
        GenericRepository<TransferTransaction> repo = new GenericRepository<>(em, TransferTransaction.class);
        TransferTransaction tr = repo.find(id);
        return tr;
    }
    
    @Override
    public void editTransaction(TransferTransaction transaction) {
        ArgsCheck.ensureNotNull(transaction, "transaction");
        GenericRepository<TransferTransaction> repo = new GenericRepository<>(em, TransferTransaction.class);
        repo.edit(transaction);
        repo.count();
    }
}
