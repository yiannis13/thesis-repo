package gr.ihu.identity.client;

import gr.ihu.identity.model.ModelUser;
import java.util.Collection;

/**
 *
 * @author John
 */

public interface IUserService {
   Collection<ModelUser> getAll(); 
}
