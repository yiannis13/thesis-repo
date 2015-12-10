package gr.ihu.identity.security;

import gr.ihu.identity.model.ModelUser;
import javax.ejb.Local;

/**
 *
 * @author John
 */

@Local
public interface ILoginManager {
    
    LoginResult loginUser(String username, String password);
    
    LoginResult logoutUser(String username, String token);
    
    boolean isValidToken(String token);
    
    ModelUser findUserByToken(String token);
}

