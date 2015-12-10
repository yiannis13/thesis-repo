package gr.ihu.identity.client;

import gr.ihu.identity.model.LoginResult;
import gr.ihu.identity.model.ModelUser;
import gr.ihu.identity.model.NewUser;
import gr.ihu.rest.client.ErrorMessage;
import gr.ihu.rest.client.RestClient;
import gr.ihu.settings.Settings;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author John
 */
public class LoginManager extends RestClient implements ILoginManager {

    public LoginManager() {
        super(Settings.getInstance().getServiceUri());
    }

    @Override
    public LoginResult loginUser(String username, String password) {
        WebTarget webTarget = target.path(Settings.getInstance().getLoginPath());
        NewUser usr = new NewUser();
        usr.setUsername(username);
        usr.setPassword(password);
        Response response = webTarget.request().post(Entity.json(usr));

        LoginResult result;
        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
            ErrorMessage msg = response.readEntity(ErrorMessage.class);
            result = LoginResult.newErrorResult(msg.getErrorMessage());
        } else {
            String token = response.getHeaderString("Authorization");
            result = LoginResult.newSuccessResult(token);
        }
        return result;
    }

    @Override
    public LoginResult logoutUser(String username, String token) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isValidToken(String token) {
        WebTarget webTarget = target.path(Settings.getInstance().getLoginPath());
        Response response = webTarget.request().header("Authorization", token).get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return true;
        }
        return false;
    }

    @Override
    public ModelUser findUserByToken(String token) {
        WebTarget webTarget = target.path(Settings.getInstance().getLoginPath() + "/identity");
        Response response = webTarget.request().header("Authorization", token).get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            ModelUser usr = new ModelUser();
            String usrname = response.readEntity(String.class);
            usr.setUsername(usrname);
            return usr;
        }
        throw new RuntimeException("method now allowed");
    }

}
