package gr.ihu.identity;

import gr.ihu.identity.db.model.User;
import javax.ejb.Local;

/**
 *
 * @author John
 */

@Local
public interface IUserManager {
    void create(User user, String password);
}
