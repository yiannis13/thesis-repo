package gr.ihu.identity.security;

import gr.ihu.identity.db.model.UserToken;
import javax.ejb.Local;

/**
 *
 * @author John
 */

@Local
public interface IUserTokenHandler {
    UserToken create(String username);
    void refresh(UserToken token);
}
