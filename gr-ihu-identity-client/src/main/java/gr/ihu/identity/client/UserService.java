package gr.ihu.identity.client;

import gr.ihu.identity.model.ModelUser;
import gr.ihu.rest.client.ErrorMessage;
import gr.ihu.rest.client.RestClient;
import gr.ihu.settings.Settings;
import java.util.Collection;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author John
 */
public class UserService extends RestClient implements IUserService {

    private final String token;

    public UserService(String token) {
        super(Settings.getInstance().getServiceUri());
        this.token = token;
    }

    @Override
    public Collection<ModelUser> getAll() {
        WebTarget webTarget = target.path(Settings.getInstance().getUsersPath());
        Response response = webTarget.request().header("Authorization", token).get();
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            ErrorMessage msg = response.readEntity(ErrorMessage.class);
            throw new RuntimeException(msg.getErrorMessage());
        }
        Collection<ModelUser> users = response.readEntity(new GenericType<Collection<ModelUser>>() {
        });
        return users;
    }

}
