package gr.ihu.identity.client;

import gr.ihu.rest.client.RestClient;
import gr.ihu.settings.Settings;
import java.net.URISyntaxException;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author John
 */
public class BankService extends RestClient {

    public BankService() throws URISyntaxException {
        super(Settings.getInstance().getServiceUri());
    }

    public void confirmTransfer(int transferId) {        
        WebTarget webTarget = target.path(Settings.getInstance().getBankPath()).path(transferId + "/confirm");
        webTarget.request().get();
    }

}

