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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author John
 */

@Entity
@XmlRootElement
@Table(name = "user_token", catalog = "auction_db", schema = "")
@NamedQueries({
    @NamedQuery(name = "UserToken.findByUser", query = "SELECT u FROM UserToken u JOIN u.user usr WHERE usr = :usr"),
    @NamedQuery(name = "UserToken.findByUserId", query = "SELECT u FROM UserToken u JOIN u.user usr WHERE usr.id = :id"),
    @NamedQuery(name = "UserToken.findByUsername", query = "SELECT u FROM UserToken u JOIN u.user usr WHERE usr.username = :username"),
    @NamedQuery(name = "UserToken.findByValue", query = "SELECT u FROM UserToken u WHERE u.value = :value")})
public class UserToken implements Serializable {
    
    private static final long serialVersionUID = 8212819319560162580L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "token_value", nullable = false, length = 2048)
    private String value;
    
    @Basic(optional = false)
    @Column(name = "type", nullable = false, length = 45)
    private int type;
    
    @Column(name = "expire_at")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date validUntil;

    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private User user;
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    @XmlTransient
    public boolean isValid() {
        return !InternalUtilities.hasExpired(validUntil);
    }
}
