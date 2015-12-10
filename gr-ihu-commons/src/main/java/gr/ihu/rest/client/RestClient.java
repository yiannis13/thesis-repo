package gr.ihu.rest.client;

import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author John
 */
public abstract class RestClient {

    protected final WebTarget target;

    protected RestClient(URI uri) {
        Client client = ClientBuilder.newClient();
        target = client.target(uri);
    }

}
