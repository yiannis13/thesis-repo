package gr.ihu.identity.client;

import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author John
 */
public class BankJob implements Runnable {

    private PostalService clientpostalService;
    private BankService clientBankService;

    private final Integer packageId;
    private final Integer bankTransferId;

    public BankJob(Integer packageId, Integer bankTransferId) {
        this.packageId = packageId;
        this.bankTransferId = bankTransferId;
    }

    @Override
    public void run() {
        boolean confirmed = false;

        while (!confirmed) {
            try {
                Thread.sleep(5000);
                // Check identity for package state | Here is being used the REST Client
                boolean packageDelivered = clientpostalService.isPackageDelivered(packageId);
                if (packageDelivered) {
                    confirmed = true;
                    clientBankService.confirmTransfer(bankTransferId);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(BankJob.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

