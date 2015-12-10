package gr.ihu.identity;

import gr.ihu.identity.model.ModelPackage;
import gr.ihu.identity.model.ModelPackageState;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author John
 */

@Local
public interface IPostalService {
    
    int createPackage(ModelPackage pack);
    
    ModelPackage findPackageByTrackNumber(int trackId);
    
    Collection<ModelPackageState> findStatesByPackageTrackNumber(int trackId);
}


