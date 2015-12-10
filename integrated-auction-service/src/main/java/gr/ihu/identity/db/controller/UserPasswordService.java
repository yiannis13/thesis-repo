package gr.ihu.identity.db.controller;

import gr.ihu.db.controller.AbstractFacade;
import gr.ihu.identity.db.model.UserPassword;
import gr.ihu.utils.checks.ArgsCheck;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author John
 */

@Stateless
public class UserPasswordService extends AbstractFacade<UserPassword> implements IUserPasswordServiceLocal {

    @PersistenceContext(unitName = "auction_pu")
    private EntityManager em;

    public UserPasswordService() {
        super(UserPassword.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public UserPassword findByUsername(String username) {
        ArgsCheck.ensureNotWhite(username, "username");
        return InternalUtilities.getFirstOrDefault(em, UserPassword.class, "UserPassword.findByUsername", "username", username);
    }
}
