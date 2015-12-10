package gr.ihu.identity.security;

import gr.ihu.db.exception.EntityNotFoundException;
import gr.ihu.identity.db.controller.IUserServiceLocal;
import gr.ihu.identity.db.controller.IUserPasswordServiceLocal;
import gr.ihu.identity.db.controller.IUserTokenServiceLocal;
import gr.ihu.identity.db.model.User;
import gr.ihu.identity.db.model.UserPassword;
import gr.ihu.identity.db.model.UserToken;
import gr.ihu.identity.model.ModelEntityConverters;
import gr.ihu.identity.model.ModelUser;
import gr.ihu.utils.checks.ArgsCheck;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author John
 */

@Stateless
public class LoginManager implements ILoginManager {

    @Inject
    private IUserServiceLocal userService;
    @Inject
    private IUserPasswordServiceLocal userPasswordService;
    @Inject
    private IUserTokenServiceLocal userTokenService;
    @Inject
    private IPasswordProtector passProtector;
    @Inject
    private IUserTokenHandler tokenHandler;
    
    @Override
    public LoginResult loginUser(String username, String password) {
        if(ArgsCheck.isWhite(username) || ArgsCheck.isWhite(password)) {
            return LoginResult.newErrorResult("username and/or password not provided");
        }
        User user = userService.findByUsername(username);
        if(user == null) {
            return LoginResult.newErrorResult("user not found");
        }
        // Check the password if it is ok
        UserPassword upass = userPasswordService.findByUsername(username);
        if(upass == null) {
            return LoginResult.newErrorResult("user login not allowed");
        }
        // Check for valid password
        String passHash = passProtector.protect(password);
        if(!upass.getPassword().equals(passHash)) {
            return LoginResult.newErrorResult("login credentials doesn't match");
        }     
        // Check for expired password
        if(!upass.isValid()) {
            return LoginResult.newErrorResult("user password expired on: " + upass.getValidUntil());
        }
        // Check if is already logged in. If so, return the same token
        // but refresh its time
        UserToken token = userTokenService.findByUsername(username);
        if(token != null) {
            tokenHandler.refresh(token);
            return LoginResult.newSuccessResult(token.getValue());
        }      
        // We must create a token at this moment
        token = tokenHandler.create(username);
        return LoginResult.newSuccessResult(token.getValue());
    }
 
    
    @Override
    public LoginResult logoutUser(String username, String token) {
        if(ArgsCheck.isWhite(username) || ArgsCheck.isWhite(token)) {
            return LoginResult.newErrorResult("username and/or token not provided");
        }       
        UserToken utoken = userTokenService.findByUsername(username);
        if(utoken == null) {
            return LoginResult.newErrorResult("operation forbidden");
        }      
        if(!token.equals(utoken.getValue())) {
            return LoginResult.newErrorResult("operation forbidden");
        }
        userTokenService.remove(utoken);
        return LoginResult.newSuccessResult(null);
    }

    @Override
    public boolean isValidToken(String token) {
        if(ArgsCheck.isWhite(token)) {
            return false;
        }       
        UserToken utoken = userTokenService.findByValue(token);
        if(utoken == null) {
            return false;
        }
        return utoken.isValid();
    }

   @Override
   public ModelUser findUserByToken(String token) {
      ArgsCheck.ensureNotWhite(token, "token");
      UserToken userToken = userTokenService.findByValue(token);
      if(userToken == null) {
         throw new EntityNotFoundException("token not found");
      }
      return ModelEntityConverters.entityToModel(userToken.getUser());
   }
}
