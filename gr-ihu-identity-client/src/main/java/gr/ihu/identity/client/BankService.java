package gr.ihu.identity.client;

import gr.ihu.rest.client.RestClient;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author John
 */
public class BankService extends RestClient implements IBankService {

    public BankService() throws URISyntaxException {
        super(new URI("http://localhost:8080/integrated-auction-service/rs/bank/"));
    }

    @Override
    public void confirmTransfer(int transferId) {
        WebTarget webTarget = target.path(transferId + "/confirm");
        webTarget.request().get();
    }

}
