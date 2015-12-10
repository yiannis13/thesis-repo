package gr.ihu.identity.security;

import gr.ihu.identity.db.controller.IUserPasswordServiceLocal;
import gr.ihu.identity.db.controller.IUserServiceLocal;
import gr.ihu.identity.db.controller.IUserTokenServiceLocal;
import gr.ihu.identity.db.model.User;
import gr.ihu.identity.db.model.UserPassword;
import gr.ihu.identity.db.model.UserToken;
import gr.ihu.utils.checks.ArgsCheck;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author John
 */

@Stateless
public class UserTokenHandler implements IUserTokenHandler {

    @Inject
    private IUserServiceLocal userService;
    @Inject
    private IUserPasswordServiceLocal userPasswordService;
    @Inject
    private IUserTokenServiceLocal userTokenService;
    @Inject
    private IPasswordProtector passProtector;

    
    @Override
    public UserToken create(String username) {
        if (ArgsCheck.isWhite(username)) {
            return null;
        }
        User user = userService.findByUsername(username);
        if (user == null) {
            return null;
        }
        UserPassword upass = userPasswordService.findByUsername(username);
        if (upass == null) {
            return null;
        }
        String tokenHash = passProtector.protect(username + upass.getPassword());
        
        UserToken token = new UserToken();
        token.setUser(user);
        user.setLoginToken(token);
        token.setType(TokenType.USER_TOKEN.ordinal());
        Date validUntil = new Date();
        validUntil.setTime(validUntil.getTime() + (long)1000 * 60 * 60 * 48);
        token.setValidUntil(validUntil);
        token.setValue(tokenHash);
        userTokenService.create(token);
        return token;
    }

    @Override
    public void refresh(UserToken token) {
        Date validUntil = token.getValidUntil();
        // refresh expire date
        if (validUntil != null) {
            long time = validUntil.getTime() + (long)1000 * 60 * 60 * 48;
            validUntil.setTime(time);
            userTokenService.edit(token);
        }
    }
}
