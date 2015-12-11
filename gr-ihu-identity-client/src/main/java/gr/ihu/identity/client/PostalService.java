package gr.ihu.identity.client;

import gr.ihu.identity.model.ModelPackageState;
import gr.ihu.rest.client.RestClient;
import gr.ihu.settings.Settings;
import java.net.URISyntaxException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author John
 */

public class PostalService extends RestClient {

    public PostalService() throws URISyntaxException {
        super(Settings.getInstance().getServiceUri());
    }

    public boolean isPackageDelivered(Integer packageId) {
        WebTarget webTarget = target.path(Settings.getInstance().getPostalPath()).path(packageId + "/check");
        Response response = webTarget.request().get();
        ModelPackageState modelPackageState = response.readEntity(ModelPackageState.class);
        if (modelPackageState.getState().toString().equalsIgnoreCase("COMPLETED")) {
            return true;
        }
        return false;
    }

}
