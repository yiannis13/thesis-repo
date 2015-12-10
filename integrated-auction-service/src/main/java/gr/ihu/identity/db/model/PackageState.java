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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author John
 */

@Entity
@Table(name = "postal_package_state", catalog = "auction_db", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Package.findAll", query = "SELECT p FROM Package p"),
    @NamedQuery(name = "Package.findById", query = "SELECT p FROM Package p WHERE p.id = :id"),
    @NamedQuery(name = "Package.findByIdAndState", query = "SELECT p FROM Package p, PackageState ps WHERE ps.pack = p AND ps.state = :state AND p.id = :id")
})
public class PackageState implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "occurred")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date when;

    @Basic(optional = false)
    @Column(name = "state_")
    private int state;

    @JoinColumn(name = "package_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Package pack;
    
    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Package getPack() {
        return pack;
    }
    
    public void setPack(Package pack) {
        this.pack = pack;
    }
   
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
    public Date getWhen() {
        return when;
    }

    public void setWhen(Date dateTime) {
        this.when = dateTime;
    }
}
