package gr.ihu.mavenproject1;

import gr.ihu.identity.client.ILoginManager;
import gr.ihu.identity.client.IUserService;
import gr.ihu.identity.client.LoginManager;
import gr.ihu.identity.client.UserService;
import gr.ihu.identity.model.LoginResult;
import gr.ihu.identity.model.ModelUser;
import java.util.Collection;

/**
 *
 * @author John
 */
public class Main {

    public static void main(String[] args) {
        ILoginManager loginManager = new LoginManager();
        LoginResult result = loginManager.loginUser("vligu", "123456");
        if (result.isError()) {
            System.out.println("Error created: " + result.getErrorMsg());
        } else {
            boolean valid = loginManager.isValidToken(result.getToken());
            System.out.println("Token: " + result.getToken() + " is " + (valid ? "valid" : "not valid"));
            if (valid) {
                ModelUser muser = loginManager.findUserByToken(result.getToken());
                System.out.println(muser.getUsername());

                IUserService userService = new UserService(result.getToken());
                Collection<ModelUser> users = userService.getAll();
                System.out.println(users);
            }
        }
    }//end of main() method

}//end of class
