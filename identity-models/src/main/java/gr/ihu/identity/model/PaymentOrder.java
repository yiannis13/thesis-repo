package gr.ihu.identity.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author John
 */

@XmlRootElement(name = "account")
public class PaymentOrder implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String sender;    // from whose account is going to be withdrawn the money
    private String receiver; // to whose accouct is going to be deposited the money
    private double amount;

    
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    
    
}
