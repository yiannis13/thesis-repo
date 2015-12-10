package gr.ihu.identity.db.controller;

import gr.ihu.db.controller.IDbService;
import gr.ihu.identity.db.model.UserInfo;
import javax.ejb.Local;

/**
 *
 * @author John
 */

@Local
public interface IUserInfoServiceLocal extends IDbService<UserInfo> {
}
