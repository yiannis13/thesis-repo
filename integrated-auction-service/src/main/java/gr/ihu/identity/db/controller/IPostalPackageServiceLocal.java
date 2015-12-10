package gr.ihu.identity.db.controller;

import gr.ihu.db.controller.IDbService;
import gr.ihu.identity.db.model.PackageState;
import javax.ejb.Local;

/**
 *
 * @author John
 */

@Local
public interface IPostalPackageServiceLocal extends IDbService<gr.ihu.identity.db.model.Package> {
    
    PackageState getLastState(Object trackId);
    void setCurrentState(Object trackId, int state);
}
