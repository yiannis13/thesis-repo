package gr.ihu.identity.model;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author John
 */
@XmlRootElement(name = "transaction")
public class TransferTransactionModel implements Serializable {
    private static final long serialVersionUID = -8295186623480612697L;

    private Integer id;
    private Date startTime;
    private Date endTime;
    private ModelAccount sourceAccount;
    private ModelAccount destinationAccount;
    private double amount;
    private String state;

    public TransferTransactionModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ModelAccount getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(ModelAccount sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public ModelAccount getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(ModelAccount destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof gr.ihu.identity.model.TransferTransactionModel)) {
            return false;
        }
        gr.ihu.identity.model.TransferTransactionModel other = (gr.ihu.identity.model.TransferTransactionModel) object;
        if ((!this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gr.ihu.auction.db.layer.model.TransferTransaction[ id=" + id + " ]";
    }
}
