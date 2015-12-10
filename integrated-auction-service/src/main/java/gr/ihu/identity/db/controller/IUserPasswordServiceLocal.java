package gr.ihu.identity.db.controller;

import gr.ihu.db.controller.IDbService;
import gr.ihu.identity.db.model.UserPassword;
import javax.ejb.Local;

/**
 *
 * @author John
 */

@Local
public interface IUserPasswordServiceLocal extends IDbService<UserPassword>{
    UserPassword findByUsername(String username);
}
