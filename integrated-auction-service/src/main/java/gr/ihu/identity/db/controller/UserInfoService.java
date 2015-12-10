package gr.ihu.identity.db.controller;

import gr.ihu.db.controller.AbstractFacade;
import gr.ihu.identity.db.model.UserInfo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author John
 */

@Stateless
public class UserInfoService extends AbstractFacade<UserInfo> implements IUserInfoServiceLocal {
    
    @PersistenceContext(unitName = "auction_pu")
    private EntityManager em;

    public UserInfoService() {
        super(UserInfo.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
   
}//end of Bean class
