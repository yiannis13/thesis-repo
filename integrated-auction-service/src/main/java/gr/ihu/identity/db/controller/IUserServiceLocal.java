package gr.ihu.identity.db.controller;

import gr.ihu.db.controller.IDbService;
import gr.ihu.identity.db.model.User;
import javax.ejb.Local;

/**
 *
 * @author John
 */

@Local
public interface IUserServiceLocal extends IDbService<User> {
    
    User findByEmail(String email);
    User findByUsername(String username);
}
