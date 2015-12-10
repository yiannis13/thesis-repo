package gr.ihu.identity.db.model;

import gr.ihu.utils.checks.ArgsCheck;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author John
 */
@Entity
@Table(name = "postal_package", catalog = "auction_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Package.findAll", query = "SELECT p FROM Package p"),
    @NamedQuery(name = "Package.findById", query = "SELECT p FROM Package p WHERE p.id = :id"),
    @NamedQuery(name = "Package.findByIdAndState", query = "SELECT p FROM Package p, PackageState ps WHERE ps.pack = p AND ps.state = :state AND p.id = :id"), //@NamedQuery(name = "Package.findLastState", query = "SELECT ps FROM Package p, PackageState ps WHERE ps.pack = p AND p.id = :id"),
})
public class Package implements Serializable {

    private static final long serialVersionUID = -1370630630346637416L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private User sender;

    @JoinColumn(name = "receiver_id", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private User receiver;

    @Basic(optional = false)
    @Column(name = "payload", nullable = false, length = 45)
    private String payload; // what is being sent

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pack")
    private List<PackageState> packageStates; // date : STATE

    public Package() {
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public List<PackageState> getPackageStates() {
        return packageStates;
    }

    public void setPackageStates(List<PackageState> packageStates) {
        this.packageStates = packageStates;
    }

//  Glassfish encounters problems with Lamdba expression !!!
//    public PackageState getLastState() {
//        ArgsCheck.ensureNotEmpty(packageStates, "packageStates");
//        PackageState last = this.packageStates.stream().
//                max((o1, o2) -> o1.getWhen().compareTo(o2.getWhen())).get();
//        return last;
//    }
    
    
    public PackageState getLastState() {
        ArgsCheck.ensureNotEmpty(packageStates, "packageStates");
        PackageState lastState = packageStates.get(0);
        for (int i = 1; i < packageStates.size(); i++) {
            PackageState current = packageStates.get(i);
            if (lastState.getWhen().before(current.getWhen())) {
                lastState = current;
            }
        }
        return lastState;
    }

}
