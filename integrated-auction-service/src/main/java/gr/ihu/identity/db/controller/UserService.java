package gr.ihu.identity.db.controller;

import gr.ihu.db.controller.AbstractFacade;
import gr.ihu.identity.db.model.User;
import gr.ihu.utils.checks.ArgsCheck;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author John
 */

@Stateless
public class UserService extends AbstractFacade<User> implements IUserServiceLocal {
    
    @PersistenceContext(unitName = "auction_pu")
    private EntityManager em;

    public UserService() {
        super(User.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public User findByEmail(String email) {
        ArgsCheck.ensureNotWhite(email, "email");
        return InternalUtilities.getFirstOrDefault(em, User.class, "User.findByEmail", "email", email);
    }

    @Override
    public User findByUsername(String username) {
        ArgsCheck.ensureNotWhite(username, "username");
        return InternalUtilities.getFirstOrDefault(em, User.class, "User.findByUsername", "username", username);
    }

}//end of Bean class
