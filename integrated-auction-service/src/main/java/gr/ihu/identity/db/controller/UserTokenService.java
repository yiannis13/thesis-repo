package gr.ihu.identity.db.controller;

import gr.ihu.db.controller.AbstractFacade;
import gr.ihu.identity.db.model.UserToken;
import gr.ihu.utils.checks.ArgsCheck;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author John
 */

@Stateless
public class UserTokenService extends AbstractFacade<UserToken> implements IUserTokenServiceLocal {

    @PersistenceContext(unitName = "auction_pu")
    private EntityManager em;

    public UserTokenService() {
        super(UserToken.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public UserToken findByUsername(String username) {
        String param = "username";
        ArgsCheck.ensureNotWhite(username, param);
        return InternalUtilities.getFirstOrDefault(em, UserToken.class, "UserToken.findByUsername", param, username);
    }

    @Override
    public UserToken findByValue(String value) {
        String param = "value";
        ArgsCheck.ensureNotWhite(value, param);
        return InternalUtilities.getFirstOrDefault(em, UserToken.class, "UserToken.findByValue", param, value);
    }
}
