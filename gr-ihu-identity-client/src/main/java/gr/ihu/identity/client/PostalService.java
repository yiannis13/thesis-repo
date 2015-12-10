package gr.ihu.identity.client;

import gr.ihu.identity.model.ModelPackageState;
import gr.ihu.rest.client.RestClient;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author John
 */
public class PostalService extends RestClient implements IPostalService {
    
    public PostalService() throws URISyntaxException{
        super(new URI("http://localhost:8080/integrated-auction-service/rs/postal/"));
    }

    @Override
    public  boolean isPackageDelivered (int packageId) {
        WebTarget webTarget = target.path(packageId + "/check");
        Response response = webTarget.request().get();
        ModelPackageState modelPackageState = response.readEntity(ModelPackageState.class);
        if (modelPackageState.getState().toString().equalsIgnoreCase("COMPLETED")) {
            return true;
        }       
        return false;
    }
    
}
