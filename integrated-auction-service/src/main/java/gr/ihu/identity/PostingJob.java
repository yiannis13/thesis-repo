package gr.ihu.identity;

import gr.ihu.identity.db.controller.IPostalPackageServiceLocal;
import gr.ihu.identity.db.model.Package;
import gr.ihu.identity.model.ModelPackageState;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author John
 */
public class PostingJob implements Runnable {
    
    private final IPostalPackageServiceLocal postalPackageService;
    private final gr.ihu.identity.db.model.Package pack;
    

    public PostingJob(IPostalPackageServiceLocal postalPackageService, Package pack) {
        this.postalPackageService = postalPackageService;
        this.pack = pack;
    }
    
    @Override
    public void run() {
        try {
            // 1 - Start receiving package from sender
            // 2 - After receive, set getIntState to PACKAGE_RECEIVED
            // 3 - Start sending package to sender (after delay)
            // 4 - Set State to PENDING
            // 5 - Delay
            // 6 - Set State to COMPLETED
            
            
            // 1
            // Set delay to 20 secs
            Thread.sleep(20000);
            
            Object trackId = pack.getId();
            // 2
            postalPackageService.setCurrentState(trackId, ModelPackageState.PackageStateType.PACKAGE_RECEIVED.getIntState());
            // 3
            Thread.sleep(20000);
            // 4
            postalPackageService.setCurrentState(trackId, ModelPackageState.PackageStateType.PENDING.getIntState());
            // 5 
            Thread.sleep(20000);
            // 6
            postalPackageService.setCurrentState(trackId, ModelPackageState.PackageStateType.COMPLETED.getIntState());
            
        } catch (InterruptedException ex) {
            Logger.getLogger(PostingJob.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }
}
