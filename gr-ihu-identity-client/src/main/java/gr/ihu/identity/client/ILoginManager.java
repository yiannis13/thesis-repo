package gr.ihu.identity.client;

import gr.ihu.identity.model.ModelUser;
import gr.ihu.identity.model.LoginResult;

/**
 *
 * @author John
 */
public interface ILoginManager {

    LoginResult loginUser(String username, String password);

    LoginResult logoutUser(String username, String token);

    boolean isValidToken(String token);

    ModelUser findUserByToken(String token);
}
