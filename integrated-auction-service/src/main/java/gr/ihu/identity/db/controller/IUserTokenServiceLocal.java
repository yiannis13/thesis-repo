package gr.ihu.identity.db.controller;

import gr.ihu.db.controller.IDbService;
import gr.ihu.identity.db.model.UserToken;
import javax.ejb.Local;

/**
 *
 * @author John
 */

@Local
public interface IUserTokenServiceLocal extends IDbService<UserToken>{
    UserToken findByUsername(String uername);
    UserToken findByValue(String value);
}
