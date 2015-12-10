package gr.ihu.identity.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author John
 */

@XmlRootElement(name = "account")
public class ModelAccount implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String username;
    private double amount;

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    
}
