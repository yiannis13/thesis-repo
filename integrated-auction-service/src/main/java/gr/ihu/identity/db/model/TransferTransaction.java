package gr.ihu.identity.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author John
 */
@Entity
@Table(name = "transfer_transaction", catalog = "auction_db", schema = "")
public class TransferTransaction implements Serializable {
    private static final long serialVersionUID = -4793025328288932432L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "start_time")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "end_time")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endTime;

    @JoinColumn(name = "source_acc_id", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private Account sourceAccount;

    @JoinColumn(name = "dest_acc_id", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private Account destinationAccount;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "tr_state", nullable = false, length = 20)
    private String state;

    public TransferTransaction() {
    }

    public static enum State {

        BLOCKED,
        COMPLETED;
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

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
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
        if (!(object instanceof TransferTransaction)) {
            return false;
        }
        TransferTransaction other = (TransferTransaction) object;
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
