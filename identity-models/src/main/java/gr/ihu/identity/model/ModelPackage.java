package gr.ihu.identity.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author John
 */

@XmlRootElement(name = "package")
public class ModelPackage implements Serializable {
    
    private static final long serialVersionUID = 9179435445493907394L;
    
    private String sender;
    private String receiver;
    private String payload;
    private ModelPackageState state;
    

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public ModelPackageState getState() {
        return state;
    }

    public void setState(ModelPackageState state) {
        this.state = state;
    }
}
