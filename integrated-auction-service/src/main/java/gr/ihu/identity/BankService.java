package gr.ihu.identity;

import gr.ihu.identity.model.TransferResult;
import gr.ihu.identity.db.controller.IBankAccountServiceLocal;
import gr.ihu.identity.db.controller.IUserServiceLocal;
import gr.ihu.db.exception.EntityNotFoundException;
import gr.ihu.identity.db.model.Account;
import gr.ihu.identity.db.model.TransferTransaction;
import gr.ihu.identity.db.model.User;
import gr.ihu.identity.email.IEmailService;
import gr.ihu.identity.model.ModelAccount;
import gr.ihu.identity.model.ModelEntityConverters;
import gr.ihu.identity.model.PaymentOrder;
import gr.ihu.identity.model.TransferTransactionModel;
import gr.ihu.utils.checks.ArgsCheck;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author John
 */
@Stateless
public class BankService implements IBankService {

    @Inject
    private IEmailService emailService;
    @Inject
    private IBankAccountServiceLocal bankAccountService;
    @Inject
    private IUserServiceLocal userService;

    
    
    @Override
    public TransferResult transfer(PaymentOrder payment, boolean requireConfirm) {
        //1 - check the inputs
        //2 - check if the auction winner (sender) has the appropriate amount of money
        //3 - if so, create a transfer transaction object
        //4 - Remove the amount from the sender and put it in BLOCK state
        

        //1
        ArgsCheck.ensureNotNull(payment, "payment order");

        String receiverUsername = payment.getReceiver();
        ArgsCheck.ensureNotWhite(receiverUsername, "receiverUsername");

        String senderUsername = payment.getSender();
        ArgsCheck.ensureNotWhite(senderUsername, "senderUsername");
        
        ArgsCheck.ensureNotNegative(payment.getAmount(), "money");
        
        Account receiverAccount = bankAccountService.findAccountByUsername(receiverUsername);
        if (receiverAccount == null) {
            throw new EntityNotFoundException("The account of the receiver not found");
        }
        ArgsCheck.ensureNotNegative(receiverAccount.getAmount(), "users' money");

        Account senderAccount = bankAccountService.findAccountByUsername(senderUsername);
        if (senderAccount == null) {
            throw new EntityNotFoundException("The account of the sender not found");
        }
        ArgsCheck.ensureNotNegative(senderAccount.getAmount(), "users' money");

        //2
        double amount = payment.getAmount();
        if (senderAccount.getAmount() < amount) {
            return TransferResult.errorResult("the user " + senderUsername + " has not enough money in his account");
        }

        //3
        TransferTransaction transaction = new TransferTransaction();
        transaction.setAmount(amount);
        transaction.setDestinationAccount(receiverAccount);
        transaction.setSourceAccount(senderAccount);
        transaction.setStartTime(new Date());
        transaction.setState(TransferTransaction.State.BLOCKED.toString());
        transaction = bankAccountService.addTransaction(transaction);
        
        //4
        senderAccount.setAmount(senderAccount.getAmount() - amount);
        if (!requireConfirm) {
            confirm(transaction);
        }
        bankAccountService.edit(senderAccount);

        TransferTransactionModel mainModel = ModelEntityConverters.entityToModel(transaction);
        //send back only a portion of the data as a Response
        TransferTransactionModel returnedModel = new TransferTransactionModel();
        returnedModel.setAmount(mainModel.getAmount());
        returnedModel.setStartTime(mainModel.getStartTime());
        returnedModel.setState(mainModel.getState());
        returnedModel.setId(mainModel.getId());
        
        return TransferResult.successResult(returnedModel);
    }

    @Override
    public TransferResult transfer(PaymentOrder payment) {
        return transfer(payment, false);
    }

    
    private void confirm(TransferTransaction transaction) {
        Account senderAccount = transaction.getSourceAccount();
        Account receiverAccount = transaction.getDestinationAccount();
        double amount = transaction.getAmount();
        receiverAccount.setAmount(receiverAccount.getAmount() + amount);
        transaction.setState(TransferTransaction.State.COMPLETED.toString());
        transaction.setEndTime(new Date());
        bankAccountService.editTransaction(transaction);
        bankAccountService.edit(receiverAccount);
        // send emails to the interested parties
        String subject = "Identity Bank Service";
        String body = "Bank transaction is completed! Your new account balance is :" ;
        emailService.sendEmail(senderAccount.getOwner().getEmail(), subject, body + senderAccount.getAmount());
        emailService.sendEmail(receiverAccount.getOwner().getEmail(), subject, body + receiverAccount.getAmount());
    }
    
    @Override
    public void confirmTransfer(int transferId) {
        TransferTransaction transaction = bankAccountService.findTransaction(transferId);
        if (transaction == null) {
            throw new EntityNotFoundException("transaction with id " + transferId + " not found.");
        }
        confirm(transaction);
    }

    
    
    
    @Override
    public Account check(String username) {
        Account account = bankAccountService.findAccountByUsername(username);
        return account;
    }

    
    @Override
    public int createAccount(ModelAccount account) {
        String username = account.getUsername();
        ArgsCheck.ensureNotNull(username, "username");

        double amount = account.getAmount();
        ArgsCheck.ensureNotNegative(amount, "money");

        Account eAccount = findUserAccountOrThrowException(username);
        // if there is already an account
        if (eAccount != null) {
            return eAccount.getId();
        }
        //else create an account object
        eAccount = new Account();
        User user = userService.findByUsername(username);
        eAccount.setOwner(user);
        eAccount.setAmount(amount);
        bankAccountService.create(eAccount);
        bankAccountService.count(); // we do it to get the account id
        return eAccount.getId();
    }

    private Account findUserAccountOrThrowException(String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("user with username :" + username + "not found");
        }
        return bankAccountService.findAccountByUsername(username);

    }

    @Override
    public void deposit(ModelAccount account) {
        String username = account.getUsername();
        ArgsCheck.ensureNotNull(username, "username");

        double amount = account.getAmount();
        ArgsCheck.ensureNotNegative(amount, "amount");

        Account eAccount = findUserAccountOrThrowException(username);
        if (eAccount != null) {
            eAccount.setAmount(eAccount.getAmount() + amount);
            bankAccountService.edit(eAccount);
        }
    }

    @Override
    public void withdraw(ModelAccount account) {
        String username = account.getUsername();
        ArgsCheck.ensureNotNull(username, "username");

        double amount = account.getAmount();
        ArgsCheck.ensureNotNegative(amount, "amount");

        Account eAccount = findUserAccountOrThrowException(username);
        if ((eAccount != null) && (eAccount.getAmount() > amount)) {
            eAccount.setAmount(eAccount.getAmount() - amount);
            bankAccountService.edit(eAccount);
        }
    }

}
