package gr.ihu.identity;

import gr.ihu.identity.db.controller.IAddressServiceLocal;
import gr.ihu.identity.db.controller.IUserInfoServiceLocal;
import gr.ihu.identity.db.controller.IUserPasswordServiceLocal;
import gr.ihu.identity.db.controller.IUserServiceLocal;
import gr.ihu.identity.db.model.Address;
import gr.ihu.identity.db.model.User;
import gr.ihu.identity.db.model.UserInfo;
import gr.ihu.identity.db.model.UserPassword;
import gr.ihu.identity.security.IPasswordProtector;
import gr.ihu.utils.checks.ArgsCheck;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;


/**
 *
 * @author John
 */

@Stateless
public class UserManager implements IUserManager {

    @Inject
    private IUserServiceLocal userService;
    @Inject
    private IUserInfoServiceLocal userInfoservice;
    @Inject
    private IAddressServiceLocal addressService;
    @Inject
    private IUserPasswordServiceLocal userPasswordService;
    @Inject 
    private IPasswordProtector passProtector;
    
    
    @Override
    public void create(User user, String password) {
        
        // Create user first
        UserInfo info = user.getUserInfo();
        ArgsCheck.ensureNotNull(info, "userInfo");
        Address address = info.getAddress();
        ArgsCheck.ensureNotNull(address, "address");
        
        addressService.create(address);
        info.setAddress(address);
        userInfoservice.create(info);
        address.setUserInfo(info);
        user.setUserInfo(info);
        userService.create(user);
        info.setUser(user);
        
        createPassword(user, password);
        userService.count();//In order for the record to be committed in the DB!!!
        userInfoservice.count();
        addressService.count();
    }
    
    private void createPassword(User user, String password) {
        UserPassword upass = new UserPassword();
        upass.setPassword(passProtector.protect(password));
        Date now = new Date(); 
        long time = now.getTime() + (long)1000*60*60*720; 
        Date validUntil = new Date(time);
        upass.setValidUntil(validUntil);   //it will be valid for 30 days.    
        upass.setUser(user);
        user.setPassword(upass);
        userPasswordService.create(upass);
    }

}
